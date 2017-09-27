package ch.heigvd.amt.landingpagemvcapp.model;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
public class Fruit
{
	public final String name;
	public final String color;

	public Fruit(String name, String color)
	{
		this.name = name;
		this.color = color;
	}

	public String getName()
	{
		return name;
	}



	public String getColor()
	{
		return color;
	}

}


