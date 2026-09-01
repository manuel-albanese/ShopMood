	package it.unibo.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.unibo.web.beans.NoteRecordDTO;
import it.unibo.web.dao.DAOFactory;
import it.unibo.web.dao.NoteRecordDAO;

/**
 * Servlet implementation class AuthenticationServlet
 */
public class NoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 Gson g = new Gson();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	@Override
	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);
        g = new Gson();
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
			String text = request.getParameter("note");
			String id_user = request.getParameter("id_user");
			
			DAOFactory persistenceFactory = DAOFactory.getDAOFactory(DAOFactory.FILES);
			NoteRecordDAO noteDAO = persistenceFactory.getNoteRecordDAO();
			NoteRecordDTO note = new NoteRecordDTO(text, id_user, LocalDateTime.now().withNano(0));
			noteDAO.create(note);
	
			response.setStatus(HttpServletResponse.SC_OK);
			out.write("Note Saved Correctly");
		}
		catch(NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("NoteServlet: Exception Encountered");
		}
		catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.write("NoteServlet: Exception Encountered");
		}
		
		

		
		
		
	}

}
