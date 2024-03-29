package ch.heigvd.amt.landingpagemvcapp.web;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.Forms.PersonForm;
import ch.heigvd.amt.landingpagemvcapp.model.Person;
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

 * Used to create a new person or edit
 */
@WebServlet(name = "PersonActionServlet", urlPatterns = {"/person-action"})
public class PersonActionServlet extends HttpServlet
{
    @EJB
    PersonManagerLocal personManager;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        String status;


        PersonForm form = new PersonForm();
        Person person = form.addPerson(request);

        if (form.getErrors().size() == 0)
        {
            System.out.println("ACTION :" + action);
            if (action != null && action.equals("edit"))
            {
                person.setId(Integer.parseInt(id));
                status = personManager.editPerson(person);

            }
            else
            {
                status = personManager.addPerson(person);
            }
            request.setAttribute("status", status);
            this.getServletContext().getRequestDispatcher("/WEB-INF/pages/PeopleList.jsp").forward(request, response);
        }
        else
        {
            String[] genderList = new String[2];
            genderList[0] = Gender.Men.toString();
            genderList[1] = Gender.Women.toString();
            request.setAttribute("genderList", genderList);
            request.setAttribute("form", form);
            this.getServletContext().getRequestDispatcher("/WEB-INF/pages/person-form.jsp").forward(request, response);
        }
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
        if (action.equals("add"))
        {
            this.getServletContext().getRequestDispatcher("/WEB-INF/pages/person-form.jsp").forward(request, response);
        }
        else if (action.equals("edit"))
        {

            String id = request.getParameter("id");
            Person person = personManager.getPerson(Integer.parseInt(id));

            if (person == null)
            {
                request.setAttribute("status", "Error: Incorrect id");
                this.getServletContext().getRequestDispatcher("/WEB-INF/pages/PeopleList.jsp").forward(request, response);
            }

            request.setAttribute("person", person);
            request.setAttribute("action", action);
            request.setAttribute("id", id);
            this.getServletContext().getRequestDispatcher("/WEB-INF/pages/person-form.jsp?action=edit&id=" + id).forward(request, response);

        }
        else if (action.equals("delete"))
        {
            String id = request.getParameter("id");
            String status = personManager.deletePerson(Integer.parseInt(id));
            request.setAttribute("status", status);
            this.getServletContext().getRequestDispatcher("/WEB-INF/pages/PeopleList.jsp").forward(request, response);
        }
        else
        {
            this.getServletContext().getRequestDispatcher("/WEB-INF/pages/PeopleList.jsp").forward(request, response);
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
