package ch.heigvd.amt.landingpagemvcapp.web;

import ch.heigvd.amt.landingpagemvcapp.services.PersonManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Thibaud Besseau & Michela Zucca
 * Date: 24.09.2017
 *
 * Used to delete a person on Database
 */
@WebServlet(name = "PersonDeleteServlet", urlPatterns = {"/delete"})
public class PersonDeleteServlet extends HttpServlet
{
	@EJB
	PersonManagerLocal personManager;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}


	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		//invalid id return to the array page
		if(id <= 0)
		{
				request.getRequestDispatcher("/WEB-INF/pages/PeopleList.jsp").forward(request, response);
		}
		else
		{
			String infosStatus = personManager.deletePerson(id);
			request.setAttribute("status", infosStatus);
			request.getRequestDispatcher("/WEB-INF/pages/PeopleList.jsp").forward(request, response);
		}

		request.getRequestDispatcher("/WEB-INF/pages/settings.jsp").forward(request, response);
	}
}