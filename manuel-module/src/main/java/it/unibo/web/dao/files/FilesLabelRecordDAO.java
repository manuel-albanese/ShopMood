package it.unibo.web.dao.files;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import it.unibo.web.beans.LabelDTO;
import it.unibo.web.dao.LabelRecordDAO;

public class FilesLabelRecordDAO implements LabelRecordDAO {

	private static final String NOTES = "label.csv";
	private static final String PATH = 
			FilesLabelRecordDAO.class.getClassLoader().getResource(NOTES).getPath();

	@Override
	public List<LabelDTO> readByUser(String id_user) throws IOException, SQLException {
		FileReader noteReader = new FileReader(PATH);
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setHeader()
				.setSkipHeaderRecord(true).setIgnoreSurroundingSpaces(true).build().parse(noteReader);
		
		List<LabelDTO> notes = new ArrayList<LabelDTO>();

		String id_user_record = null;
		LocalDateTime timestamp = null;
		LabelDTO note = null;
		
		
		for(CSVRecord record : records) {
			id_user_record = record.get("id_user");
			timestamp = LocalDateTime.parse(record.get("timestamp"));
			
			 if(id_user_record.compareTo(id_user)==0) {
				 note = new LabelDTO(record.get("label"), timestamp);	
				 notes.add(note); 
			 }

		}

		noteReader.close();
		return notes;
	}

	@Override
	public LabelDTO readByUserLast(String id_user) throws IOException, SQLException {
		FileReader noteReader = new FileReader(PATH);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setHeader()
				.setSkipHeaderRecord(true).setIgnoreSurroundingSpaces(true).build().parse(noteReader);
		
		List<LabelDTO> notes = new ArrayList<LabelDTO>();

		String id_user_record = null;
		LocalDateTime timestamp = null;
		LabelDTO note = null;
		
		
		for(CSVRecord record : records) {
			id_user_record = record.get("id_user");
			timestamp = LocalDateTime.parse(record.get("timestamp"));
			
			 if(id_user_record.compareTo(id_user)==0) {
				 note = new LabelDTO(record.get("label"), timestamp);	
				 notes.add(note); 
			 }

		}

		noteReader.close();
		if(notes.isEmpty()) return new LabelDTO("", timestamp);
		else return notes.getLast();
	}

}