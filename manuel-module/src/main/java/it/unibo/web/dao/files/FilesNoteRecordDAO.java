package it.unibo.web.dao.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import it.unibo.web.beans.NoteRecordDTO;
import it.unibo.web.dao.NoteRecordDAO;

public class FilesNoteRecordDAO implements NoteRecordDAO {

	private static final String NOTES = "notes.csv";
	private static final String PATH = 
			FilesNoteRecordDAO.class.getClassLoader().getResource(NOTES).getPath();
	

	@Override
	public NoteRecordDTO read(String id_user) throws IOException {
		FileReader noteReader = new FileReader(PATH);
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setHeader()
				.setSkipHeaderRecord(true).setIgnoreSurroundingSpaces(true).build().parse(noteReader);
		
		
		String text = null;
		String id_user_record = null;
		LocalDateTime timestamp = null;
		NoteRecordDTO note = null;
		
		for(CSVRecord record : records) {
			id_user_record = record.get("id_user");
			text = record.get("note");
			timestamp = LocalDateTime.parse(record.get("timestamp"));
			
			if(id_user==id_user_record) {
				note = new NoteRecordDTO(text,id_user_record, timestamp);
				break;
			}
		}

		noteReader.close();
		return note;
	}

	@Override
	public List<NoteRecordDTO> readAll() throws IOException {
		
		FileReader noteReader = new FileReader(PATH);
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setHeader()
				.setSkipHeaderRecord(true).setIgnoreSurroundingSpaces(true).build().parse(noteReader);
		
		List<NoteRecordDTO> notes = new ArrayList<NoteRecordDTO>();
		String text = null;
		String id_user_record = null;
		LocalDateTime timestamp = null;
		NoteRecordDTO note = null;
		
		
		for(CSVRecord record : records) {
			id_user_record = record.get("id_user");
			text = record.get("note");
			timestamp = LocalDateTime.parse(record.get("timestamp"));
			
			 note = new NoteRecordDTO(text,id_user_record, timestamp);	
			 notes.add(note);
		}

		noteReader.close();
		return notes;
	}

	@Override
	public void create(NoteRecordDTO note) throws IOException, SQLException {
		System.out.print(PATH);
		FileWriter writer = new FileWriter(new File("C:\\Users\\albam\\eclipse-workspace-2023-PAW\\Modello-Esame.zip_expanded\\Modello-Esame\\src\\main\\resources\\notes.csv"),true);
		BufferedWriter noteWriter = new BufferedWriter(writer);
		
		noteWriter.write(note.getId_user() + "," + note.getTimestamp() + "," + note.getNote() + "\n");
		noteWriter.close();
		
	}

	@Override
	public List<NoteRecordDTO> readByUser(String id_user) throws IOException, SQLException {
		FileReader noteReader = new FileReader(PATH);
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setHeader()
				.setSkipHeaderRecord(true).setIgnoreSurroundingSpaces(true).build().parse(noteReader);
		
		List<NoteRecordDTO> notes = new ArrayList<NoteRecordDTO>();
		String text = null;
		String id_user_record = null;
		LocalDateTime timestamp = null;
		NoteRecordDTO note = null;
		
		
		for(CSVRecord record : records) {
			id_user_record = record.get("id_user");
			text = record.get("note");
			timestamp = LocalDateTime.parse(record.get("timestamp"));
			
			 if(id_user_record.compareTo(id_user)==0) {
				 note = new NoteRecordDTO(text,id_user_record, timestamp);	
				 notes.add(note); 
			 }

		}

		noteReader.close();
		return notes;
	}

}