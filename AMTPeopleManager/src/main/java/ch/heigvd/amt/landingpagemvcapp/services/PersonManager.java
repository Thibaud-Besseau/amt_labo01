package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.Person;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.json.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	final int MAX_USER_API = 1000;
    boolean modified =true;

	// JDBC Acces
	@Resource(lookup = "java:/jdbc/person")
	private DataSource dataSource;

	public void randomPeople(int number) throws IOException {
		int numbertemp;
		String formattedDate="";

		//clear the old list
		listPeople.clear();
		System.out.println(listPeople.size());

		// Connexion JDBC
		Connection con = null;

		try {
			while (number != 0) {
				if (number > MAX_USER_API) {
					numbertemp = MAX_USER_API;
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
					UtilsJson.initNext(i); // Actualise l'objet sélectionné dans le tableau Json

					pstmt.setString(1,UtilsJson.getFirstName());
					pstmt.setString(2,UtilsJson.getLastName());

                    System.out.println(UtilsJson.getGender());
                    if(UtilsJson.getGender().equals("male"))
                    {
                        pstmt.setString(3,Gender.Men.toString());

                    }
                    else
                    {
                        pstmt.setString(3,Gender.Women.toString());
                    }


					try
					{
						Date date = new SimpleDateFormat("yyyy-MM-dd").parse(UtilsJson.getDoB());
						formattedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
					}
					catch (Exception e)
					{
						Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, e);
					}
					pstmt.setString(4, formattedDate);
					pstmt.setString(5,UtilsJson.getEmail());
					pstmt.setString(6,UtilsJson.getPhone());
					pstmt.addBatch();
				}
				// Exécution de la requête
				pstmt.executeBatch();
				number -= numbertemp;
			}
            modified=true;
			// Fin de connexion
			con.close();
		} catch (SQLException ex) {
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public List getListPeople()
	{
	    if(modified)
        {
            listPeople.clear();
            listPeople=findAllPerson();
            modified = false;
        }

            return listPeople;

	}

	@Override
	public List<Person> findAllPerson()
	{
		// Liste retournée contenant les personnes en BD
		listPeople = new ArrayList<>();
		try
		{
			// Connexion
			Connection connection = dataSource.getConnection();

			PreparedStatement pstmt = connection.prepareStatement(UtilsJDBC.seletToDbAll);
			ResultSet rs = pstmt.executeQuery();

			// Pour chaque personne en DB
			while (rs.next())
			{
				// Récupérer les informations
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Gender gender = rs.getString("gender").equals("Men") ? Gender.Men : Gender.Women;
				String dob = rs.getString("birthday");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				int id = rs.getInt("person_id");



				listPeople.add(new Person(id, gender, firstName, lastName, dob, email, phone));
			}
			connection.close();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
		}

		return listPeople;

	}

	public String addPerson(Gender gender, String firstName, String lastName, String dob, String email, String phone)
	{
		return addPerson(new Person(gender, firstName, lastName, dob, email, phone));
	}

	public String addPerson(Person person)
	{
// Connexion JDBC
        Connection connexion = null;

        try {
            // Connexion
            connexion = dataSource.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(UtilsJDBC.insertToDB);

            pstmt.setString(1,person.getFirstName());
            pstmt.setString(2,person.getLastName());
            pstmt.setString(3,person.getGender().toString());
            pstmt.setString(4,person.getBirthday());
            pstmt.setString(5,person.getEmail());
            pstmt.setString(6,person.getPhone());
            pstmt.execute();
            connexion.close();

        }
        catch (SQLException e)
        {
            return "Error"+e;
        }

        modified=true;
        return "The person has been successfully added";
	}



	public String deletePerson(int id)
	{
		String status;
// Connexion JDBC
		Connection connexion = null;

		try {
			// Connexion
			connexion = dataSource.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement(UtilsJDBC.removeToDB);
			pstmt.setString(1, Integer.toString(id));
			pstmt.execute();
			status = "The person has been deleted";
            connexion.close();
		}
		catch (SQLException e)
		{
			return "Error "+e;
		}
		modified=true;
		return status;

	}

    @Override
    public Person getPerson(int id)
	{

		String firstName;
		String lastName;
		Gender gender;
		String dob;
		String email;
		String phone;

		// Connexion JDBC
		Connection connexion = null;

		try {
			// Connexion
			connexion = dataSource.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement(UtilsJDBC.selectToDbWithID);
			pstmt.setString(1, Integer.toString(id));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
			{

                // Récupérer les informations
				firstName = rs.getString("first_name");
				lastName = rs.getString("last_name");
				gender = rs.getString("gender").equals("Men") ? Gender.Men : Gender.Women;
				dob = rs.getString("birthday");
				email = rs.getString("email");
				phone = rs.getString("phone");

			}
			else
			{
				return null;
			}
            connexion.close();



		}
		catch (SQLException ex)
		{
			return null;
		}

		return new Person(id, gender, firstName, lastName, dob, email, phone);
	}

    @Override
    public String editPerson(Person person)
    {
        // Connexion JDBC
        Connection connexion = null;

        try
        {
            // Connexion
            connexion = dataSource.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(UtilsJDBC.updateToDB);

            pstmt.setString(1,person.getFirstName());
            pstmt.setString(2,person.getLastName());
            System.out.println(person.getGender().toString());
            System.out.println("First "+person.getFirstName());
            pstmt.setString(3,person.getGender().toString());
            pstmt.setString(4,person.getBirthday());
            pstmt.setString(5,person.getEmail());
            pstmt.setString(6,person.getPhone());
            pstmt.setInt(7,person.getId());
            pstmt.executeUpdate();
            connexion.close();

        }
        catch (SQLException e)
        {

            return "Error during the update : "+e;
        }

        modified=true;
        return "The person has been edited";

    }
}
