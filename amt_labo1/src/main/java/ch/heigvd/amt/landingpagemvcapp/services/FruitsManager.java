package ch.heigvd.amt.landingpagemvcapp.services;

import ch.heigvd.amt.landingpagemvcapp.model.Fruit;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
public class FruitsManager
{
	public Fruit getRandomFruit()
	{
		return new Fruit("banana","yellow");
	}
}
