package ch.heigvd.amt.landingpagemvcapp.services;

public class UtilsJDBC {

    // Insertion en base de données d'une personne
    public static String insertToDB = "INSERT INTO personData(first_name,last_name,gender,birthday,email,phone) " +
            "VALUES(?,?,?,?,?,?)";

    public static String updateToDB = "UPDATE personData SET first_name = ?, last_name = ?," +
            " gender = ?, birthday = ? , email = ? ,phone = ? WHERE ? = personData.person_id";

    public static String removeToDB = "DELETE FROM personData WHERE person_id = ?";

    public static String selectToDbWithID = "SELECT * FROM personData WHERE person_id = ?";

    public static String selectToDbAll = "SELECT * FROM personData";

    public static String countToDbAll = "SELECT COUNT(*) FROM personData";

    public static String selectToDbAllWithLimit(String columName, String direction) {
        String SQLFormat = "SELECT * FROM personData ORDER BY %s" + " %s " + " LIMIT ?, ?";
        return String.format(SQLFormat, columName, direction);
    }
}
