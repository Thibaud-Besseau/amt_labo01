package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.Person;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.json.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
@Stateless
public class PersonManager implements PersonManagerLocal
{
    protected List<Person> listPeople = new ArrayList();
    int id;
    final int  MAX_USER_API = 5000;

    // JDBC Acces
	@Resource(lookup = "java:/jdbc/person")
	private DataSource dataSource;


    public void randomPeople(int number) throws IOException
    {
	    int numbertemp;
	    System.out.println("total"+number);

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
								     firstName.substring(1, firstName.length() - 1)
											    + " " + lastName.substring(1, lastName.length() - 1),
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


    public List getListPeople() {
	    System.out.println("list "+listPeople.size());
	    return listPeople;
    }

	@Override
	public List<Person> findAllPerson() {

    	listPeople = new ArrayList<>();

		try {
			Connection connection = dataSource.getConnection();
			System.out.println("Schéma: " + connection.getSchema());
			System.out.println("Catalog: " + connection.getCatalog());
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM personData");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Gender gender = rs.getString("gender").equals("male") ? Gender.Men : Gender.Women;
				String dob = rs.getString("birthday");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				long id = rs.getLong("person_id");

				listPeople.add(new Person(gender,firstName+" " +lastName,dob,email,phone));
			}
			connection.close();
		} catch (SQLException ex) {
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
		}

		return listPeople;

	}
}
