package ch.heigvd.amt.landingpagemvcapp.model;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
public class Person
{
	public int id;
	public  Gender gender;
	public  String firstName;
	public  String lastName;
	public  String birthday;
	public  String email;
	public  String phone;

	public Person(int id, Gender gender, String firstName, String lastName, String birthday, String email, String phone)
	{
		this.id = id;
		this.gender = gender;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
	}

	public Person(Gender gender, String firstName, String lastName, String birthday, String email, String phone)
	{
		this.gender = gender;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
	}

	public Person()
	{
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Gender getGender()
	{
		return gender;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}


}


