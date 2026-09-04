package it.unibo.web.beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletContext;

import it.unibo.web.dao.DAOFactory;
import it.unibo.web.dao.LabelRecordDAO;
import it.unibo.web.dao.NoteRecordDAO;
import it.unibo.web.dao.ProductRecordDAO;
import it.unibo.web.dao.ReviewRecordDAO;
import it.unibo.web.strategy.StrategyFactory;

public class RecommendContext {
	private ServletContext context;
	private String label;
	private String userID;
	private List<NoteRecordDTO> notes;
	private List<ProductRecordDTO> products;
	private List<ReviewRecordDTO> reviews;
	
	
	
	public ServletContext getContext() {
		return context;
	}
	public void setContext(ServletContext context) {
		this.context = context;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public List<NoteRecordDTO> getNotes() {
		return notes;
	}
	public void setNotes(List<NoteRecordDTO> notes) {
		this.notes = notes;
	}
	public List<ProductRecordDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductRecordDTO> products) {
		this.products = products;
	}
	public List<ReviewRecordDTO> getReviews() {
		return reviews;
	}
	public void setReviews(List<ReviewRecordDTO> reviews) {
		this.reviews = reviews;
	}
	public RecommendContext(ServletContext context, String label, String userID, List<NoteRecordDTO> notes,
			List<ProductRecordDTO> products, List<ReviewRecordDTO> reviews) {
		super();
		this.context = context;
		this.label = label;
		this.userID = userID;
		this.notes = notes;
		this.products = products;
		this.reviews = reviews;
	}
	
	public RecommendContext(String label, String userID, List<NoteRecordDTO> notes,
			List<ProductRecordDTO> products, List<ReviewRecordDTO> reviews) {
		super();
		this.context = null;
		this.label = label;
		this.userID = userID;
		this.notes = notes;
		this.products = products;
		this.reviews = reviews;
	}
	
	public RecommendContext(int modality, String userID, ServletContext context) throws IOException, SQLException {
		super();
		
		DAOFactory persistenceFactory = DAOFactory.getDAOFactory(0);
		ProductRecordDAO productDAO = persistenceFactory.getProductRecordDAO();		
		this.products = productDAO.readAll();
		
		NoteRecordDAO noteDAO = persistenceFactory.getNoteRecordDAO();
		this.notes  = noteDAO.readByUser(userID);
		
		
		LabelRecordDAO labelDAO = persistenceFactory.getLabelRecordDAO();
		LabelDTO label = labelDAO.readByUserLast(userID);
		
		ReviewRecordDAO reviewDAO = persistenceFactory.getReviewRecordDAO();
		if(modality==StrategyFactory.POPULARITY)  this.reviews = reviewDAO.readAll();
		else this.reviews = reviewDAO.readByUser(userID);	
		
		this.context = context;
		this.label = label.getLabel();
		this.userID = userID;

	}

	
}
