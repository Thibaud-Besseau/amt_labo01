package ch.heigvd.amt.landingpagemvcapp.web;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.Forms.PersonForm;
import ch.heigvd.amt.landingpagemvcapp.model.Person;
import ch.heigvd.amt.landingpagemvcapp.services.PersonManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thibaud Besseau on 24.09.2017.
 * Used to create a new person or edit
 */
@WebServlet(name = "PersonActionServlet", urlPatterns = {"/person-action"})
public class PersonActionServlet extends HttpServlet
{
	@EJB
	PersonManager personManager;

	public static final String ERRORS = "errors";
	public static final String RESULT = "result";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		System.out.println("POST");
		Map<String, String> errors = new HashMap<String, String>();


		PersonForm form = new PersonForm();
		Person person = form.addPerson( request );




		if (form.getErrors().size() == 0)
		{
			personManager.addPerson(person);
			List<Person> list = personManager.getListPeople();
			System.out.println(list.size());
			request.setAttribute("dataPeople", list);
			this.getServletContext().getRequestDispatcher("/WEB-INF/pages/PeopleList.jsp").forward(request, response);


		}
		else
		{
			request.setAttribute( "form", form );
			System.out.println(form.getErrors().get("phoneUser"));
			request.getRequestDispatcher("/WEB-INF/pages/person-form.jsp").forward(request, response);
		}



        /* Transmission de la paire d'objets request/response à notre JSP */

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

		String[] genderList = new String[2];
		genderList[0] = Gender.Men.toString();
		genderList[1] = Gender.Women.toString();

		request.setAttribute("genderList", genderList);

		String action = request.getParameter("action");
		//test if user creation is needed
		System.out.println(action);
		if (action.equals("add"))
		{
			request.getRequestDispatcher("/WEB-INF/pages/person-form.jsp").forward(request, response);

		}
		else if (action.equals("edit"))
		{
			//TODO EDIT
			List<Person> list = personManager.getListPeople();
			request.setAttribute("dataPeople", list);
			request.getRequestDispatcher("/WEB-INF/pages/person-form.jsp").forward(request, response);

		}
		else
		{
			//get list
			List<Person> list = personManager.getListPeople();
			request.setAttribute("dataPeople", list);
			request.getRequestDispatcher("/WEB-INF/pages/PeopleList.jsp").forward(request, response);
		}
	}



	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo()
	{
		return "Short description";
	}// </editor-fold>

}
