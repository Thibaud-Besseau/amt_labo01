package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.People;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import javax.json.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
public class PeopleManager {
    private List<People> listPeople = new ArrayList();
    int id;

    public void randomPeople(int number) throws Exception {
        // Génération du Json
        URL url = new URL("https://randomuser.me/api/?inc=gender,name,dob,email,phone&results=" + number);
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

        for (int i = 0; i < size; i++) {
            // Récupération de la personne X dans les résultats
            result = results.get(i);
            reader = Json.createReader(new StringReader(result.toString()));
            person = reader.readObject();

            // Récupération des données concernant la personne X
            Gender gender = person.get("gender").toString().substring(1, 3).equals("mr") ? Gender.Men : Gender.Women;
            dob = person.get("dob").toString();
            email = person.get("email").toString();
            phone = person.get("phone").toString();
            firstName = Json.createReader(new StringReader(person.get("name").toString())).readObject().get("first").toString();
            lastName = Json.createReader(new StringReader(person.get("name").toString())).readObject().get("last").toString();

            // Ajout de la nouvelle personne
            listPeople.add(new People(
                            gender,
                            firstName.substring(1, firstName.length() - 1)
                                    + " " + lastName.substring(1, lastName.length() - 1),
                            dob.substring(1, dob.length() - 1),
                            email.substring(1, email.length() - 1),
                            phone.substring(1, phone.length() - 1)
                    )
            );
        }
    }


    public List getListPeople() {
        return listPeople;
    }

}
