package ch.heigvd.amt.landingpagemvcapp.services;

public class UtilsJDBC {

    // Insertion en base de données d'une personne
    public static String insertToDB = "INSERT INTO personData(first_name,last_name,gender,birthday,email,phone) " +
            "VALUES(?,?,?,?,?,?)";

    public static String updateToDB = "";

    public static String removeToDB = "";

    public static String selectToDB = "";

}
