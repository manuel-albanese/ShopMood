package it.unibo.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unibo.web.beans.ProductRecordDTO;

public interface ProductRecordDAO {
	
	public ProductRecordDTO read(String id_product) throws IOException, SQLException ;
	
	public List<ProductRecordDTO> readAll() throws IOException,SQLException ;
	
	public List<ProductRecordDTO> readByCat(String category) throws IOException,SQLException ;
}
