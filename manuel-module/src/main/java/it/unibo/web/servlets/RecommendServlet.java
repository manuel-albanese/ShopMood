package it.unibo.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.unibo.web.beans.LabelDTO;
import it.unibo.web.beans.NoteRecordDTO;
import it.unibo.web.beans.ProductRecordDTO;
import it.unibo.web.beans.RecommendContext;
import it.unibo.web.beans.Recommendation;
import it.unibo.web.beans.ReviewRecordDTO;
import it.unibo.web.dao.DAOFactory;
import it.unibo.web.dao.LabelRecordDAO;
import it.unibo.web.dao.NoteRecordDAO;
import it.unibo.web.dao.ProductRecordDAO;
import it.unibo.web.dao.ReviewRecordDAO;
import it.unibo.web.strategy.RecommenderStrategy;
import it.unibo.web.strategy.StrategyFactory;


/**
 * Servlet implementation class AuthenticationServlet
 */
public class RecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 Gson g = new Gson();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws ServletException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
        synchronized (this.getServletContext()) {
            if (this.getServletContext().getAttribute("days") == null) {
                this.getServletContext().setAttribute("days", new HashMap<Long, Integer>());
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
		
		try {
			String id_user = request.getParameter("id_user");
			int mod = Integer.parseInt(request.getParameter("modality"));
			
			StrategyFactory strategyFactory = new StrategyFactory();
			DAOFactory persistenceFactory = DAOFactory.getDAOFactory(0);

				
			ReviewRecordDAO reviewDAO = persistenceFactory.getReviewRecordDAO();
			List<ReviewRecordDTO> reviews = null;
			if(mod==StrategyFactory.POPULARITY)  reviews = reviewDAO.readAll();
			else reviews = reviewDAO.readByUser(id_user);	
			
			ProductRecordDAO productDAO = persistenceFactory.getProductRecordDAO();		
			List<ProductRecordDTO> products = productDAO.readAll();
			
			NoteRecordDAO noteDAO = persistenceFactory.getNoteRecordDAO();
			List<NoteRecordDTO> notes  = noteDAO.readByUser(id_user);
			
			LabelRecordDAO labelDAO = persistenceFactory.getLabelRecordDAO();
			LabelDTO label = labelDAO.readByUserLast(id_user);
						
			
			RecommendContext context =  new RecommendContext(this.getServletContext()
					,label.getLabel(),id_user,notes,products,reviews);
			
			
			RecommenderStrategy strat = strategyFactory.getStrategy(mod);
			if(strat==null) throw new Exception("Illegal Argument");
			
			List<Recommendation> recs = strat.recommendProducts(context);

			Map<String, ProductRecordDTO> productMap = new HashMap<>();
			for (ProductRecordDTO p : products) productMap.put(p.getParentID(), p);

			HashMap<ProductRecordDTO, Float> result = new LinkedHashMap<>();
			for (Recommendation r : recs) {
			    ProductRecordDTO p = productMap.get(r.getID());
			    if (p != null) result.put(p, r.getScore());
			    
			}
	
			request.setAttribute("result", result);
			request.getRequestDispatcher("/pages/products.jsp").forward(request, response);
		}
		catch(NumberFormatException e) {
			request.setAttribute("javax.servlet.error.exception", e);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			request.getRequestDispatcher("/errors/failure.jsp").forward(request, response);
			
		}
		catch(Exception e) {
			request.setAttribute("javax.servlet.error.exception", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			request.getRequestDispatcher("/errors/failure.jsp").forward(request, response);
			
		}
		
		
		
		
		
	}

}
