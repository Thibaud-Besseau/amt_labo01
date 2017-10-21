package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Person;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Thibaud Besseau & Michela Zucca
 * Date: 24.09.2017
 *
 * Interface PersonManager
 */

@Local
public interface PersonManagerLocal {

    void randomPeople(int number) throws IOException;

    String addPerson(Person person);

    String deletePerson(int id);

    Person getPerson(int id);

    String editPerson(Person person);

    int getTotalPeople();

    int getNumberPeopleSearch(String searchInput);

    JSONObject getAllPeople(int totalRecords, String columName, String direction, int initial, int recordSize, HttpServletRequest request, String searchInput) throws SQLException, ClassNotFoundException;
}
