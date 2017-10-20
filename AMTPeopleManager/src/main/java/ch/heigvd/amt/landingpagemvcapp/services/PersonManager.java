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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class PersonManager implements PersonManagerLocal {

    final int MAX_USER_API = 5000;
    // JDBC Acces
    @Resource(lookup = "java:/jdbc/person")
    private DataSource dataSource;

    public void randomPeople(int number) throws IOException {
        int numbertemp;
        String formattedDate = "";

        // Connexion JDBC
        Connection con = null;
        PreparedStatement pstmt = null;
        int nbUserBeforeInsertion=0;

        try {
                if (number > MAX_USER_API) {
                    nbUserBeforeInsertion= getTotalPeople();
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
                pstmt = con.prepareStatement(UtilsJDBC.insertToDB);

                //to optimize the time to fill the database. We have decided to load only 5000 unique users.
                // If more users are requested, these will be copies of the first 5000


                    // Préparation de la requête : Ajout des personnes par bloque
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

                    System.out.println("EXECUTE"+number);
                    // Exécution de la requête
                    pstmt.executeBatch();


                    number -= numbertemp;

               pstmt.close();

            System.out.println(number);

            PreparedStatement pstmt2=null;
            PreparedStatement pstmt3=null;

            if(number!=0)
               {
                   long numberAddedThisTime = nbUserBeforeInsertion + MAX_USER_API;
                   System.out.println("before insert"+numberAddedThisTime);
                   System.out.println("numberAddedThisTime before"+numberAddedThisTime);
                   while (numberAddedThisTime*2 < number)
                   {
                       System.out.println("boucle"+numberAddedThisTime);
                       pstmt2=con.prepareStatement("INSERT INTO `personData`( first_name, last_name, gender, birthday, email, phone) SELECT first_name, last_name, gender, birthday, email, phone FROM personData");
                       number -= numberAddedThisTime;
                       numberAddedThisTime += numberAddedThisTime;
                       pstmt2.execute();

                   }
                   System.out.println(number+"ajoute "+numberAddedThisTime);
                   System.out.println("EXECUTE batch 2");
                   pstmt3 = con.prepareStatement("INSERT INTO `personData`( first_name, last_name, gender, birthday, email, phone) SELECT first_name, last_name, gender, birthday, email, phone FROM personData LIMIT " + number);
                   pstmt3.execute();
                   System.out.println("EXECUTE LA QUERY");
               }

            // Fin de connexion
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    public String addPerson(Person person) {
        // Connexion JDBC
        Connection connexion = null;

        try {
            // Connexion
            connexion = dataSource.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(UtilsJDBC.insertToDB);

            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            pstmt.setString(3, person.getGender().toString());
            pstmt.setString(4, person.getBirthday());
            pstmt.setString(5, person.getEmail());
            pstmt.setString(6, person.getPhone());
            pstmt.execute();
            pstmt.close();
            connexion.close();

        } catch (SQLException e) {
            return "Error" + e;
        }
        return "The person has been successfully added";
    }


    public String deletePerson(int id) {
        String status;
        // Connexion JDBC
        Connection connexion = null;

        try {
            // Connexion
            connexion = dataSource.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(UtilsJDBC.removeToDB);
            pstmt.setString(1, Integer.toString(id));
            pstmt.execute();
            pstmt.close();
            status = "The person has been deleted";
            connexion.close();
        } catch (SQLException e) {
            return "Error " + e;
        }
        return status;

    }

    @Override
    public Person getPerson(int id) {
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

            if (rs.next()) {
                // Récupérer les informations
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                gender = rs.getString("gender").equals("Men") ? Gender.Men : Gender.Women;
                dob = rs.getString("birthday");
                email = rs.getString("email");
                phone = rs.getString("phone");

            } else {
                return null;
            }
            pstmt.close();
            rs.close();
            connexion.close();
        } catch (SQLException ex) {
            return null;
        }

        return new Person(id, gender, firstName, lastName, dob, email, phone);
    }

    @Override
    public String editPerson(Person person) {
        // Connexion JDBC
        Connection connexion = null;

        try {
            // Connexion
            connexion = dataSource.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(UtilsJDBC.updateToDB);

            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            System.out.println(person.getGender().toString());
            System.out.println("First " + person.getFirstName());
            pstmt.setString(3, person.getGender().toString());
            pstmt.setString(4, person.getBirthday());
            pstmt.setString(5, person.getEmail());
            pstmt.setString(6, person.getPhone());
            pstmt.setInt(7, person.getId());
            pstmt.executeUpdate();
            pstmt.close();
            connexion.close();

        } catch (SQLException e) {

            return "Error during the update : " + e;
        }

        return "The person has been edited";

    }

    @Override
    public int getTotalPeople() {
        // Connexion JDBC
        Connection connexion = null;
        int total = 0;

        try {
            // Connexion
            connexion = dataSource.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(UtilsJDBC.countToDbAll);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            total = Integer.parseInt(rs.getString("COUNT(*)"));
            rs.close();
            pstmt.close();
            connexion.close();
            System.out.println("connexion : " + connexion.isClosed() + "\nPstmt2 : " +
                    pstmt.isClosed() + "\nrs : " + rs.isClosed());
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
            return total;
        }

        return total;
    }

    @Override
    public int getNumberPeopleSearch(String searchInput) {
        // Connexion JDBC
        Connection connexion = null;
        int total = 0;

        try {
            String SQLFormat = "SELECT COUNT(*) as 'total' FROM personData WHERE first_name LIKE '%" +searchInput+
                    "%' "+"OR first_name LIKE '%" +searchInput+
                    "%' "+"OR last_name LIKE '%" +searchInput+
                    "%' "+"OR gender LIKE '%" +searchInput+
                    "%' "+"OR birthday LIKE '%" +searchInput+
                    "%' "+"OR email LIKE '%" +searchInput+
                    "%' "+"OR phone LIKE '%" +searchInput+
                    "%' ";
            // Connexion
            connexion = dataSource.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(SQLFormat);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            total = Integer.parseInt(rs.getString("total"));
            rs.close();
            pstmt.close();
            connexion.close();
            System.out.println("connexion : " + connexion.isClosed() + "\nPstmt2 : " +
                    pstmt.isClosed() + "\nrs : " + rs.isClosed());
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
            return total;
        }

        return total;
    }

    public JSONObject getAllPeople(int totalRecords, String columName, String direction, int initial, int recordSize, HttpServletRequest request, String searchInput)
            throws SQLException, ClassNotFoundException {

        int totalAfterSearch;
        if(searchInput.isEmpty())
        {
            totalAfterSearch=totalRecords;
        }
        else
        {
            totalAfterSearch=getNumberPeopleSearch(searchInput);
        }
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();

        // Connexion
        Connection connexion = dataSource.getConnection();

        PreparedStatement pstmt = connexion.prepareStatement(UtilsJDBC.selectToDbAllWithLimit(columName, direction, searchInput));
        pstmt.setInt(1, initial);
        pstmt.setInt(2, recordSize);

        //for searching
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
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
        connexion.close();
        try {
            result.put("data", array);
            result.put("iTotalRecords", totalRecords);
            result.put("iTotalDisplayRecords", totalAfterSearch);

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return result;
    }
}
//TODO 		pstmt.close();
