package ch.heigvd.amt.landingpagemvcapp.web;

import ch.heigvd.amt.landingpagemvcapp.services.PersonManager;
import ch.heigvd.amt.landingpagemvcapp.services.PersonManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
@WebServlet(name = "SettingsServlet", urlPatterns = {"/settings"})
public class SettingsServlet extends HttpServlet
{
	@EJB
	PersonManagerLocal personManager;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
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

//        Person people = peopleManager.randomPeople();
//        request.setAttribute("thePeople", people);
//	request.getRequestDispatcher("/WEB-INF/pages/people.jsp").forward(request,response);

		//request.getRequestDispatcher("/WEB-INF/pages/people.jsp").forward(request,response);
		request.getRequestDispatcher("/WEB-INF/pages/settings.jsp").forward(request, response);
	}


	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo()
	{
		return "Settings";
	}// </editor-fold>


	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException
	{

		String targetUrl = request.getContextPath() + "/settings";
		int numberUser;

    /*
     Get the parameter values, which have been transmitted either in the query string
     (for GET requests) or in the body (for POST requests).
     */
		String action = request.getParameter("action");
		String data = request.getParameter("numberUser");

		if (data != null)
		{
			numberUser = Integer.parseInt(data);
		}
		else
		{
			numberUser = 0;
			request.getSession().setAttribute("error", "Enter a valid value. Please try again.");
		}


		if (numberUser > 0)
		{
			try
			{
				personManager.randomPeople(numberUser);
				targetUrl = request.getContextPath() + "/index.html";

			}
			catch (Exception e)
			{
				request.getSession().setAttribute("error", "An error occurred while loading the people list. Please try again." + e.toString());
			}
		}

		response.sendRedirect(targetUrl);

	}

}
