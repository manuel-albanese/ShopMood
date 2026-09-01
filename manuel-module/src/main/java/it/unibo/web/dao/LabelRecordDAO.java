package it.unibo.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unibo.web.beans.LabelDTO;

public interface LabelRecordDAO {
	
	
	public List<LabelDTO> readByUser(String id_user) throws IOException,SQLException ;
	
	public LabelDTO readByUserLast(String id_user) throws IOException,SQLException ;
}
