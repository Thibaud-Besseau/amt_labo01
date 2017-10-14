package ch.heigvd.amt.landingpagemvcapp.services;

import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

public class UtilsJson {
    private static int size;
    private static JsonReader reader;
    private static JsonArray results;
    private static JsonObject person;

    public static JsonArray getJson(int numbertemp) throws IOException {
        URL url = new URL("https://randomuser.me/api/?inc=gender,name,dob,email,phone&results=" + numbertemp);
        InputStream is = url.openStream();

        reader = Json.createReader(is);
        JsonObject obj = reader.readObject();
        // Récupérer la size
        JsonObject info = obj.getJsonObject("info");
        JsonValue sizeValue = info.get("results");
        int size = Integer.parseInt(sizeValue.toString());
        setSize(size);

        // Récupération du niveau résultats contenant les générations aléatoires
        results = obj.getJsonArray("results");

        return results;
    }

    public static int getSize(){
        return size;
    }

    private static void setSize(int i){
        size = i;
    }

    public static JsonReader getReader(){
        return reader;
    }

    public static JsonObject initNext(int i){
        JsonValue result = results.get(i);
        reader = Json.createReader(new StringReader(result.toString()));
        person = reader.readObject();

        return person;
    }

    public static String getGender(){
        return person.get("gender").toString().substring(1, 5);
    }
    public static String getDoB(){
        return person.get("dob").toString();
    }
    public static String getEmail(){
        return person.get("email").toString();
    }
    public static String getPhone(){
        return person.get("phone").toString();
    }
    public static String getFirstName(){
        return Json.createReader(new StringReader(person.get("name").toString())).readObject().get("first").toString();
    }
    public static String getLastName(){
        return Json.createReader(new StringReader(person.get("name").toString())).readObject().get("last").toString();
    }
}
