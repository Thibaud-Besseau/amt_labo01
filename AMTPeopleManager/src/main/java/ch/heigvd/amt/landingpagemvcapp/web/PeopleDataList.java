package ch.heigvd.amt.landingpagemvcapp.web;

import ch.heigvd.amt.landingpagemvcapp.services.PersonManagerLocal;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
/**
 * Created by IntelliJ IDEA.
 * User: Thibaud Besseau & Michela Zucca
 * Date: 24.09.2017
 *
 * Create a JSON for datatables
 */
@WebServlet(name = "PeopleDataList", urlPatterns = {"/people-data"})
public class PeopleDataList extends HttpServlet
{

    @EJB
    PersonManagerLocal personManager;
    final String[] COLUMS_NAME = {"gender", "first_name", "last_name", "birthday", "email", "phone"};


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

        int listDisplayAmount = 10;
        int start = 0;
        int column = 0;
        String dir = "asc";
        JSONObject jsonResult = new JSONObject();
        //get sort and pagination params
        String pageNo = request.getParameter("iDisplayStart");
        String pageSize = request.getParameter("iDisplayLength");
        String colIndex = request.getParameter("iSortCol_0");
        String sortDirection = request.getParameter("sSortDir_0");

        if (pageNo != null)
        {
            start = Integer.parseInt(pageNo);
            if (start < 0)
            {
                start = 0;
            }
        }
        if (pageSize != null)
        {
            listDisplayAmount = Integer.parseInt(pageSize);
            if (listDisplayAmount < 10 || listDisplayAmount > 50)
            {
                listDisplayAmount = 10;
            }
        }
        if (colIndex != null)
        {
            column = Integer.parseInt(colIndex);
            if (column < 0 || column > 5)
            {
                column = 0;
            }
        }
        if (sortDirection != null)
        {
            if (!sortDirection.equals("asc"))
            {
                dir = "desc";
            }
        }

        String colName = COLUMS_NAME[column];
        String searchInput = request.getParameter("sSearch");


        //get how many row are in the database
        int totalRecords = personManager.getTotalPeople();

        try
        {
            jsonResult = personManager.getAllPeople(totalRecords, colName, dir, start, listDisplayAmount, request, searchInput);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException ex)
        {
            request.setAttribute("status", "SQL ERROR: Please try Again");
        }

        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
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

