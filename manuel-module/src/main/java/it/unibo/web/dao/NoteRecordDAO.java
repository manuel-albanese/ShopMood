package it.unibo.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unibo.web.beans.NoteRecordDTO;

public interface NoteRecordDAO {
	
	public NoteRecordDTO read(String id_product) throws IOException, SQLException ;
	
	public List<NoteRecordDTO> readAll() throws IOException,SQLException ;
	
	public List<NoteRecordDTO> readByUser(String id_user) throws IOException,SQLException ;
	
	public void create(NoteRecordDTO note) throws IOException,SQLException ;
	
}
