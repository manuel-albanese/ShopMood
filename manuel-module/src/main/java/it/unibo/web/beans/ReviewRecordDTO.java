package it.unibo.web.beans;


import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewRecordDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Float rating;
	private String title;
	private String text;	
	@JsonProperty("asin")
	private String productID;
	@JsonProperty("parent_asin")
	private String parentID;
	@JsonProperty("user_id")
	private String userID;
	private Long timestamp;
	
	@Override
	public String toString() {
		return "ReviewRecordDTO [rating=" + rating + ", title=" + title + ", text=" + text + ", productID=" + productID
				+ ", parentID=" + parentID + ", userID=" + userID + ", timestamp=" + timestamp + "]";
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReviewRecordDTO(Float rating, String title, String text, String productID, String parentID, String userID,
			Long timestamp) {
		super();
		this.rating = rating;
		this.title = title;
		this.text = text;
		this.productID = productID;
		this.parentID = parentID;
		this.userID = userID;
		this.timestamp = timestamp;
	}

	public ReviewRecordDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	

}
