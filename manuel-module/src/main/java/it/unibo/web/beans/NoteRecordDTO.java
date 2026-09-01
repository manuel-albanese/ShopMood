package it.unibo.web.beans;

import java.time.LocalDateTime;

public class NoteRecordDTO {
	@Override
	public String toString() {
		return "NoteRecordDTO [note=" + note + ", id_user=" + id_user + ", timestamp=" + timestamp + "]";
	}
	private String note;
	private String id_user;
	private LocalDateTime timestamp;
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getId_user() {
		return id_user;
	}
	public void setId_user(String id_user) {
		this.id_user = id_user;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public NoteRecordDTO(String note, String id_user, LocalDateTime timestamp) {
		super();
		this.note = note;
		this.id_user = id_user;
		this.timestamp = timestamp;
	}
	
	
}
