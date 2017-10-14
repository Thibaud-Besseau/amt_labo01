package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.Person;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

@Stateless
public class PersonManager implements PersonManagerLocal
{

	protected List<Person> listPeople = new ArrayList();
	int id;
	final int MAX_USER_API = 5000;

	// JDBC Acces
	@Resource(lookup = "java:/jdbc/person")
	private DataSource dataSource;

	public void randomPeople(int number) throws IOException {
		int numbertemp;
		System.out.println("total" + number);

		//clear the old list
		listPeople.clear();
		System.out.println(listPeople.size());

		// Connexion JDBC
		Connection con = null;

		try {
			while (number != 0) {
				if (number > MAX_USER_API) {
					numbertemp = 5000;
				} else {
					numbertemp = number;
				}

				// Récupération du Json via l'url (voir classe UtilsJson)
				JsonArray results = UtilsJson.getJson(numbertemp);
				int size = UtilsJson.getSize();

				// Etablissement de la connexion
				con = dataSource.getConnection();
				// Prépration de la requête
				PreparedStatement pstmt = con.prepareStatement(UtilsJDBC.insertToDB);

				// Préparation de la requête : Ajout des personnes par bloque
				for (int i = 0; i < size; i++) {
					UtilsJson.initNext(i);

					pstmt.setString(1,UtilsJson.getFirstName());
					pstmt.setString(2,UtilsJson.getLastName());
					pstmt.setString(3,UtilsJson.getGender());
					pstmt.setString(4,UtilsJson.getDoB());
					pstmt.setString(5,UtilsJson.getEmail());
					pstmt.setString(6,UtilsJson.getPhone());
					pstmt.addBatch();
				}
				// Exécution de la requête
				pstmt.executeBatch();

				number -= numbertemp;
			}
			con.close();
		} catch (SQLException ex) {
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public List getListPeople()
	{
		System.out.println("list " + listPeople.size());
		return listPeople;
	}

	@Override
	public List<Person> findAllPerson()
	{

		listPeople = new ArrayList<>();

		try
		{
			Connection connection = dataSource.getConnection();
			System.out.println("Schéma: " + connection.getSchema());
			System.out.println("Catalog: " + connection.getCatalog());
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM personData");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Gender gender = rs.getString("gender").equals("male") ? Gender.Men : Gender.Women;
				String dob = rs.getString("birthday");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				long id = rs.getLong("person_id");

				listPeople.add(new Person(gender, firstName, lastName, dob, email, phone));
			}
			connection.close();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
		}

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
		status = "The person has been deleted";
		return status;
	}
}
