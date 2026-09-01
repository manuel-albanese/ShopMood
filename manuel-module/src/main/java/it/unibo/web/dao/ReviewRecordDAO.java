package it.unibo.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unibo.web.beans.ReviewRecordDTO;

public interface ReviewRecordDAO {
	
	
	public List<ReviewRecordDTO> readAll() throws IOException,SQLException ;
	
	public List<ReviewRecordDTO> readByUser(String id_user) throws IOException,SQLException ;
	
	public List<ReviewRecordDTO> readByProd(String id_product) throws IOException,SQLException ;
	
}
