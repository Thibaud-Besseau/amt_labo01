package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.Person;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.json.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
@Singleton
public class PersonManager
{
	protected List<Person> listPeople = new ArrayList();
	int id;
	final int MAX_USER_API = 5000;

	public void randomPeople(int number) throws Exception
	{
		int numbertemp;
		System.out.println("total" + number);

		//clear the old list
		listPeople.clear();
		System.out.println(listPeople.size());

		while (number != 0)
		{
			if (number > MAX_USER_API)
			{
				numbertemp = 5000;
			}
			else
			{
				numbertemp = number;
			}

			// Génération du Json
			URL url = new URL("https://randomuser.me/api/?inc=gender,name,dob,email,phone&results=" + numbertemp);
			InputStream is = url.openStream();
			JsonReader reader = Json.createReader(is);
			JsonObject obj = reader.readObject();
			// Récupération du niveau résultats contenant les générations aléatoires
			JsonArray results = obj.getJsonArray("results");

			// Récupération du niveau infos pour récupérer la taille du jeu de données
			JsonObject info = obj.getJsonObject("info");
			JsonValue sizeValue = info.get("results");
			int size = Integer.parseInt(sizeValue.toString());

			// Eléments pour la récupération des données brutes
			JsonValue result;
			JsonObject person;
			String firstName, lastName, dob, email, phone;


			for (int i = 0; i < size; i++)
			{
				// Récupération de la personne X dans les résultats
				result = results.get(i);
				reader = Json.createReader(new StringReader(result.toString()));
				person = reader.readObject();

				// Récupération des données concernant la personne X
				Gender gender = person.get("gender").toString().substring(1, 5).equals("male") ? Gender.Men : Gender.Women;
				dob = person.get("dob").toString();
				email = person.get("email").toString();
				phone = person.get("phone").toString();
				firstName = Json.createReader(new StringReader(person.get("name").toString())).readObject().get("first").toString();
				lastName = Json.createReader(new StringReader(person.get("name").toString())).readObject().get("last").toString();

				// Ajout de la nouvelle personne
				listPeople.add(new Person(
									 gender,
									 firstName.substring(1, firstName.length() - 1),
									 lastName.substring(1, lastName.length() - 1),
									 dob.substring(1, dob.length() - 1),
									 email.substring(1, email.length() - 1),
									 phone.substring(1, phone.length() - 1)
						  )
				);
			}
			number -= numbertemp;
			System.out.println("final " + listPeople.size());

		}
	}


	public List getListPeople()
	{
		System.out.println("list " + listPeople.size());
		return listPeople;
	}

	public void addPerson(Gender gender, String firstName, String lastName, String dob, String email, String phone)
	{
		listPeople.add(new Person(gender, firstName, lastName, dob, email, phone));
	}

	public void addPerson(Person person)
	{
		listPeople.add(person);
	}

	public String deletePerson(int id)
	{
		//
		String status;
		//TODO DELETE IN DATABASE
		status="The person has been deleted";
		return status;
	}

}
