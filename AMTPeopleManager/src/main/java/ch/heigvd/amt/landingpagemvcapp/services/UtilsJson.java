package ch.heigvd.amt.landingpagemvcapp.services;

import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
/**
 * Created by IntelliJ IDEA.
 * User: Thibaud Besseau & Michela Zucca
 * Date: 24.09.2017
 *
 * Used for managing randomly generated Json on https://randomuser.me
 */

public class UtilsJson
{
    private static int size;
    private static JsonReader reader;
    private static JsonArray results;
    private static JsonObject person;

    public static JsonArray getJson(int numbertemp) throws IOException
    {
        URL url = new URL("https://randomuser.me/api/?inc=gender,name,dob,email,phone&results=" + numbertemp);
        InputStream is = url.openStream();

        reader = Json.createReader(is);
        JsonObject obj = reader.readObject();
        // get size
        JsonObject info = obj.getJsonObject("info");
        JsonValue sizeValue = info.get("results");
        int size = Integer.parseInt(sizeValue.toString());
        setSize(size);

        // get data
        results = obj.getJsonArray("results");

        return results;
    }

    public static int getSize()
    {
        return size;
    }

    private static void setSize(int i)
    {
        size = i;
    }

    public static JsonReader getReader()
    {
        return reader;
    }

    public static JsonObject initNext(int i)
    {
        JsonValue result = results.get(i);
        reader = Json.createReader(new StringReader(result.toString()));
        person = reader.readObject();

        return person;
    }

    public static String getGender()
    {
        return person.get("gender").toString().substring(1, 5);
    }

    public static String getDoB()
    {
        String dob = person.get("dob").toString();
        return dob.substring(1, dob.length() - 1);
    }

    public static String getEmail()
    {
        String email = person.get("email").toString();
        return email.substring(1, email.length() - 1);
    }

    public static String getPhone()
    {
        String phone = person.get("phone").toString();
        return phone.substring(1, phone.length() - 1);
    }

    public static String getFirstName()
    {
        String firstName = Json.createReader(new StringReader(person.get("name").toString())).readObject().get("first").toString();
        return firstName.substring(1, firstName.length() - 1);
    }

    public static String getLastName()
    {
        String lastName = Json.createReader(new StringReader(person.get("name").toString())).readObject().get("last").toString();
        return lastName.substring(1, lastName.length() - 1);
    }
}
