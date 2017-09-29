package ch.heigvd.amt.landingpagemvcapp.model;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
public class People
{
	public final Gender gender;
	public final String name;
	public final String birthday;
	public final String email;
	public final String phone;

	public People(Gender gender, String name, String birthday, String email, String phone)
	{
		this.gender = gender;
		this.name = name;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
	}

	public Gender getGender()
	{
		return gender;
	}

	public String getName()
	{
		return name;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public String getEmail()
	{
		return email;
	}

	public String getPhone()
	{
		return phone;
	}
}


