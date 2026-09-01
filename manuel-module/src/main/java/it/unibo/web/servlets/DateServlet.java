package it.unibo.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class AuthenticationServlet
 */
public class DateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 Gson g = new Gson();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);
        synchronized (this.getServletContext()) {
            if (this.getServletContext().getAttribute("days") == null) {
                this.getServletContext().setAttribute("days", new HashMap<String, Integer>());
            }
        }
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		
		try {
			LocalDate date = LocalDate.parse(request.getParameter("date"));
			String id_user = request.getParameter("id_user");
			
			
			synchronized (this.getServletContext()) {
				@SuppressWarnings("unchecked")
				HashMap<String, Integer> days = (HashMap<String, Integer>) this.getServletContext().getAttribute("days");
				days.put(id_user, date.getDayOfYear());				
			}
	
			response.setStatus(HttpServletResponse.SC_OK);
			out.write("Date Saved Correctly");
		}
		catch(NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("DateServlet: Exception Encountered With ID");
		}
		catch(DateTimeParseException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("DateServlet: Exception Encountered with Date");
		}
		catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.write("DateServlet: Exception Encountered");
		}
		
		
		
		
		
	}

}
