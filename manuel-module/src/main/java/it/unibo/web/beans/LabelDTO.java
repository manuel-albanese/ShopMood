package it.unibo.web.beans;

import java.time.LocalDateTime;

public class LabelDTO {

	private String label;
	private LocalDateTime timestamp;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public LabelDTO(String label, LocalDateTime timestamp) {
		super();
		this.label = label;
		this.timestamp = timestamp;
	}
	
	
	
	
}
