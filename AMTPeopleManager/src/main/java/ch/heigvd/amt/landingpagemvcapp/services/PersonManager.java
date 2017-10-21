package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.Person;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class PersonManager implements PersonManagerLocal
{

    final int MAX_USER_API = 5000;
    // JDBC Acces
    @Resource(lookup = "java:/jdbc/person")
    private DataSource dataSource;

    public void randomPeople(int number) throws IOException
    {
        int numbertemp;
        String formattedDate = "";

        // JDBC Connection
        Connection connection = null;
        PreparedStatement pstmt = null;
        int nbUserBeforeInsertion = 0;

        try
        {

            //to optimize the time to fill the database. We have decided to load only 5000 unique users (maximum authorized by the API).
            // If more users are requested, these will be copies of the first 5000
            if (number > MAX_USER_API)
            {
                nbUserBeforeInsertion = getTotalPeople();
                numbertemp = MAX_USER_API;
            }
            else
            {
                numbertemp = number;
            }

            // Get a JSON from a URL
            JsonArray results = UtilsJson.getJson(numbertemp);
            int size = UtilsJson.getSize();

            // Open a connection with the database
            connection = dataSource.getConnection();
            // prepare the query
            pstmt = connection.prepareStatement(UtilsJDBC.insertToDB);

            // add the person informations in the query
            for (int i = 0; i < size; i++)
            {
                UtilsJson.initNext(i); // Actualise l'objet sélectionné dans le tableau Json

                pstmt.setString(1, UtilsJson.getFirstName());
                pstmt.setString(2, UtilsJson.getLastName());

                if (UtilsJson.getGender().equals("male"))
                {
                    pstmt.setString(3, Gender.Men.toString());

                }
                else
                {
                    pstmt.setString(3, Gender.Women.toString());
                }


                try
                {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(UtilsJson.getDoB());
                    formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                }
                catch (Exception e)
                {
                    Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, e);
                }
                pstmt.setString(4, formattedDate);
                pstmt.setString(5, UtilsJson.getEmail());
                pstmt.setString(6, UtilsJson.getPhone());
                pstmt.addBatch();
            }

            System.out.println("EXECUTE" + number);
            // Execute the query
            pstmt.executeBatch();

            //remaining people to add
            number -= numbertemp;

            pstmt.close();

            PreparedStatement pstmt2 = null;
            PreparedStatement pstmt3 = null;

            //Duplicate database row
            if (number != 0)
            {
                long numberAddedThisTime = nbUserBeforeInsertion + MAX_USER_API;
                while (numberAddedThisTime * 2 < number)
                {
                    pstmt2 = connection.prepareStatement("INSERT INTO `personData`( first_name, last_name, gender, birthday, email, phone) SELECT first_name, last_name, gender, birthday, email, phone FROM personData");
                    number -= numberAddedThisTime;
                    numberAddedThisTime += numberAddedThisTime;
                    pstmt2.execute();

                }
                pstmt3 = connection.prepareStatement("INSERT INTO `personData`( first_name, last_name, gender, birthday, email, phone) SELECT first_name, last_name, gender, birthday, email, phone FROM personData LIMIT " + number);
                pstmt3.execute();
            }

            // End connection
            connection.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public String addPerson(Person person)
    {
        // JDBC Connection
        Connection connection = null;

        try
        {
            // Connection
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(UtilsJDBC.insertToDB);

            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            pstmt.setString(3, person.getGender().toString());
            pstmt.setString(4, person.getBirthday());
            pstmt.setString(5, person.getEmail());
            pstmt.setString(6, person.getPhone());
            pstmt.execute();
            pstmt.close();
            connection.close();

        }
        catch (SQLException e)
        {
            return "Error" + e;
        }
        return "The person has been successfully added";
    }


    public String deletePerson(int id)
    {
        String status;
        // JDBC Connection
        Connection connection = null;

        try
        {
            // Connexion
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(UtilsJDBC.removeToDB);
            pstmt.setString(1, Integer.toString(id));
            pstmt.execute();
            pstmt.close();
            status = "The person has been deleted";
            connection.close();
        }
        catch (SQLException e)
        {
            return "Error " + e;
        }
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

        // JDBC connection
        Connection connection = null;

        try
        {
            // connection
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(UtilsJDBC.selectToDbWithID);
            pstmt.setString(1, Integer.toString(id));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
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
            pstmt.close();
            rs.close();
            connection.close();
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
        // JDBC connection
        Connection connection = null;

        try
        {
            // Connexion
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(UtilsJDBC.updateToDB);

            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            pstmt.setString(3, person.getGender().toString());
            pstmt.setString(4, person.getBirthday());
            pstmt.setString(5, person.getEmail());
            pstmt.setString(6, person.getPhone());
            pstmt.setInt(7, person.getId());
            pstmt.executeUpdate();
            pstmt.close();
            connection.close();

        }
        catch (SQLException e)
        {

            return "Error during the update : " + e;
        }

        return "The person has been edited";

    }

    @Override
    public int getTotalPeople()
    {
        // JDBC connection
        Connection connection = null;
        int total = 0;

        try
        {

            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(UtilsJDBC.countToDbAll);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            total = Integer.parseInt(rs.getString("COUNT(*)"));
            rs.close();
            pstmt.close();
            connection.close();
        }
        catch (SQLException e)
        {
            System.out.println("Error : " + e.getMessage());
            return total;
        }

        return total;
    }

    @Override
    public int getNumberPeopleSearch(String searchInput)
    {
        // JDBC connection
        Connection connection = null;
        int total = 0;

        try
        {
            String SQLFormat = "SELECT COUNT(*) as 'total' FROM personData WHERE first_name LIKE '%" + searchInput +
                    "%' " + "OR first_name LIKE '%" + searchInput +
                    "%' " + "OR last_name LIKE '%" + searchInput +
                    "%' " + "OR gender LIKE '%" + searchInput +
                    "%' " + "OR birthday LIKE '%" + searchInput +
                    "%' " + "OR email LIKE '%" + searchInput +
                    "%' " + "OR phone LIKE '%" + searchInput +
                    "%' ";
            // Connexion
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQLFormat);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            total = Integer.parseInt(rs.getString("total"));
            rs.close();
            pstmt.close();
            connection.close();
        }
        catch (SQLException e)
        {
            System.out.println("Error : " + e.getMessage());
            return total;
        }

        return total;
    }

    public JSONObject getAllPeople(int totalRecords, String columName, String direction, int initial, int recordSize, HttpServletRequest request, String searchInput)
            throws SQLException, ClassNotFoundException
    {

        int totalAfterSearch;
        if (searchInput.isEmpty())
        {
            totalAfterSearch = totalRecords;
        }
        else
        {
            totalAfterSearch = getNumberPeopleSearch(searchInput);
        }
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();

        // JDBC connection
        Connection connection = dataSource.getConnection();

        PreparedStatement pstmt = connection.prepareStatement(UtilsJDBC.selectToDbAllWithLimit(columName, direction, searchInput));
        pstmt.setInt(1, initial);
        pstmt.setInt(2, recordSize);

        //for searching
        ResultSet rs = pstmt.executeQuery();

        while (rs.next())
        {
            JSONArray jsonArray = new JSONArray();
            // Add the informations into the JSON array
            jsonArray.put(rs.getString("gender").equals("Men") ? Gender.Men : Gender.Women);
            jsonArray.put(rs.getString("first_name"));
            jsonArray.put(rs.getString("last_name"));
            jsonArray.put(rs.getString("birthday"));
            jsonArray.put(rs.getString("email"));
            jsonArray.put(rs.getString("phone"));

            //add the button to edit or delete in the json
            String actions = "<a  href='person-action?action=edit&id=";
            actions += rs.getString("person_id") + "' class='btn-flat circle greenButton'> <span style='margin:auto' style='display:table'	class='fa fa-fw fa-edit'></span></a>";
            actions += "<a  href='person-action?action=delete&id=";
            actions += rs.getString("person_id") + "' class='btn-flat circle greenButton'> <span style='margin:auto' style='display:table' class='fa fa-fw fa-remove'></span></a>";

            jsonArray.put(actions);
            array.put(jsonArray);
        }
        rs.close();
        pstmt.close();
        connection.close();
        try
        {
            result.put("data", array);
            result.put("iTotalRecords", totalRecords);
            result.put("iTotalDisplayRecords", totalAfterSearch);

        }
        catch (Exception e)
        {
            System.out.println("Error : " + e.getMessage());
        }

        return result;
    }
}
