package it.unibo.web.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unibo.web.beans.ProductRecordDTO;
import it.unibo.web.beans.RecommendContext;
import it.unibo.web.strategy.RecommenderStrategy;
import it.unibo.web.strategy.StrategyFactory;


/**
 * Servlet implementation class AuthenticationServlet
 */
public class RecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
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
			

			RecommendContext context =  
					new RecommendContext(mod,id_user,this.getServletContext());
			
			
			StrategyFactory strategyFactory = StrategyFactory.getStrategyFactory();  
			RecommenderStrategy strat = strategyFactory.getStrategy(mod);
			if(strat==null) throw new Exception("Illegal Argument");
			
			Map<String, ProductRecordDTO> result = strat.recommendProducts(context);

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
