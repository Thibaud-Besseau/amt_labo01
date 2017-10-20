package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.Person;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

@Local
public interface PersonManagerLocal {

    List<Person> findAllPerson();

    void randomPeople(int number) throws IOException;

    List getListPeople();

    String addPerson(Gender gender, String firstName, String lastName, String dob, String email, String phone);

    String addPerson(Person person);

    String deletePerson(int id);

    Person getPerson(int id);

    String editPerson(Person person);

    int getTotalPeople();

    int getNumberPeopleSearch(String searchInput);

    JSONObject getAllPeople(int totalRecords, String columName, String direction, int initial, int recordSize, HttpServletRequest request, String searchInput) throws SQLException, ClassNotFoundException;
}
