package it.unibo.web.beans;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRecordDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("title")
	private String name;
	@Override
	public String toString() {
		return "ProductRecordDTO [name=" + name + ", category=" + category + ", parentID=" + parentID
				+ ", average_rating=" + average_rating + ", price=" + price + ", description=" + description
				+ ", rating_number=" + rating_number + "]";
	}
	@JsonProperty("main_category")
	private String category;	
	@JsonProperty("parent_asin")
	private String parentID;
	private Float average_rating;
	private String price;
	private List<String> description;
	private Integer rating_number;
	private Float score;
	
	
	
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public ProductRecordDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public Float getAverage_rating() {
		return average_rating;
	}

	public void setAverage_rating(Float average_rating) {
		this.average_rating = average_rating;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public Integer getRating_number() {
		return rating_number;
	}

	public void setRating_number(Integer rating_number) {
		this.rating_number = rating_number;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ProductRecordDTO(ProductRecordDTO other) {
		super();
		this.name = other.name;
		this.category = other.category;
		this.parentID = other.parentID;
		this.average_rating = other.average_rating;
		this.price = other.price;
		this.description = other.description;
		this.rating_number = other.rating_number;
		this.score = (float) 0;
	}
	
	
	
	
	
	
	
	
	

}
