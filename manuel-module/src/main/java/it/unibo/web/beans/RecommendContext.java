package it.unibo.web.beans;

import java.util.List;

import javax.servlet.ServletContext;

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
	
	
	
	
}
