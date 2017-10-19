package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.Person;

import java.io.IOException;
import java.util.List;
import javax.ejb.Local;

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

}
