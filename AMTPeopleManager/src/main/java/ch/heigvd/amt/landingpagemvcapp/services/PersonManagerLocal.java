package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Person;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PersonManagerLocal {

    public List<Person> findAllPerson();
    public void randomPeople(int number) throws IOException;
    public List getListPeople();
}
