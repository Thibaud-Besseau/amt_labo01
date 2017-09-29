package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
public class PeopleManager
{
	private List<People> listPeople = new ArrayList();
	int id;

	public void randomPeople(){

		for(int i = 0; i < 10 ; i++) {
			listPeople.add(new People(Gender.HOMME,"Donald Trump","14 juin 1946","donald.trump@white-house.gov","1600 Pennsylvania Ave NW, Washington, DC 20500, États-Unis", "+1 202-456-1111"));
		}
	}

	public List getListPeople(){
		return listPeople;
	}
}
