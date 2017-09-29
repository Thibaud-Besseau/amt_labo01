package ch.heigvd.amt.landingpagemvcapp.web;

import ch.heigvd.amt.landingpagemvcapp.model.People;
import ch.heigvd.amt.landingpagemvcapp.services.PeopleManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 */
@WebServlet(name = "SettingsServlet", urlPatterns = {"/settings"})
public class SettingsServlet extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}


	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException {

//        People people = peopleManager.randomPeople();
//        request.setAttribute("thePeople", people);
//	request.getRequestDispatcher("/WEB-INF/pages/people.jsp").forward(request,response);

		//request.getRequestDispatcher("/WEB-INF/pages/people.jsp").forward(request,response);
		request.getRequestDispatcher("/WEB-INF/pages/settings.jsp").forward(request,response);
	}


	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Settings";
	}// </editor-fold>

}