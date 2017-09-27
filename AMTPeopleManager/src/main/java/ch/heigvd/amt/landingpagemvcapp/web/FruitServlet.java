package ch.heigvd.amt.landingpagemvcapp.web;

import ch.heigvd.amt.landingpagemvcapp.model.Fruit;
import ch.heigvd.amt.landingpagemvcapp.services.FruitsManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
@WebServlet(name = "FruitServlet", urlPatterns = {"/fruit"})
public class FruitServlet extends HttpServlet
{
	FruitsManager fruitsManager = new FruitsManager();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
			Fruit fruit = fruitsManager.getRandomFruit();
			request.setAttribute("theFruit", fruit);
			request.getRequestDispatcher("/WEB-INF/pages/fruit.jsp").forward(request,response);

	}

}
