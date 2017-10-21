package ch.heigvd.amt.landingpagemvcapp.services;

public class UtilsJDBC
{

    // Insertion en base de données d'une personne
    public static String insertToDB = "INSERT INTO personData(first_name,last_name,gender,birthday,email,phone) " +
            "VALUES(?,?,?,?,?,?)";

    public static String updateToDB = "UPDATE personData SET first_name = ?, last_name = ?," +
            " gender = ?, birthday = ? , email = ? ,phone = ? WHERE ? = personData.person_id";

    public static String removeToDB = "DELETE FROM personData WHERE person_id = ?";

    public static String selectToDbWithID = "SELECT * FROM personData WHERE person_id = ?";

    public static String selectToDbAll = "SELECT * FROM personData";

    public static String countToDbAll = "SELECT COUNT(*) FROM personData";

    public static String selectToDbAllWithLimit(String columName, String direction, String searchInput)
    {

        if (searchInput.isEmpty())
        {
            String SQLFormat = "SELECT * FROM personData ORDER BY %s" + " %s " + " LIMIT ?, ?";
            return String.format(SQLFormat, columName, direction);
        }
        else
        {
            String SQLFormat = "SELECT * FROM personData WHERE first_name LIKE '%" + searchInput +
                    "%' " + "OR first_name LIKE '%" + searchInput +
                    "%' " + "OR last_name LIKE '%" + searchInput +
                    "%' " + "OR gender LIKE '%" + searchInput +
                    "%' " + "OR birthday LIKE '%" + searchInput +
                    "%' " + "OR email LIKE '%" + searchInput +
                    "%' " + "OR phone LIKE '%" + searchInput +
                    "%' " + "ORDER BY " + columName + " " + direction + " LIMIT ?, ?";
            return SQLFormat;
        }


    }
}
