package it.unibo.web.dao.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.web.beans.ReviewRecordDTO;
import it.unibo.web.dao.ReviewRecordDAO;
import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

public class FilesReviewRecordDAO implements ReviewRecordDAO {

	private static final String HISTORY = "Video_Games_Reduced.jsonl";
	private static final String PATH = 
			FilesReviewRecordDAO.class.getClassLoader().getResource(HISTORY).getPath();


	@Override
	public List<ReviewRecordDTO> readByUser(String id_user) throws IOException,SQLException {
		FileReader historyReader = new FileReader(PATH);
		BufferedReader buffReader = new BufferedReader(historyReader);
		ObjectMapper mapper = new ObjectMapper();

		String line = null;
		ArrayList<ReviewRecordDTO> result = new ArrayList<ReviewRecordDTO>();
		ReviewRecordDTO review = null;
		
		while((line=buffReader.readLine())!=null) {
			review = mapper.readValue(line, ReviewRecordDTO.class);
			if(review.getUserID().compareTo(id_user)==0)result.add(review);
		}
		

		historyReader.close();
		return result;

	}

	@Override
	public List<ReviewRecordDTO> readAll() throws IOException {
		
		FileReader historyReader = new FileReader(PATH);
		BufferedReader buffReader = new BufferedReader(historyReader);
		ObjectMapper mapper = new ObjectMapper();
		String line = null;
		ArrayList<ReviewRecordDTO> result = new ArrayList<ReviewRecordDTO>();
		ReviewRecordDTO review = null;

		
		
		while((line=buffReader.readLine())!=null) {

			
			review = mapper.readValue(line, ReviewRecordDTO.class);
			result.add(review);
		}
		

		historyReader.close();
		return result;
	}

	@Override
	public List<ReviewRecordDTO> readByProd(String id_product) throws IOException,SQLException  {
		FileReader historyReader = new FileReader(PATH);
		BufferedReader buffReader = new BufferedReader(historyReader);
		ObjectMapper mapper = new ObjectMapper();
		String line = null;
		ArrayList<ReviewRecordDTO> result = new ArrayList<ReviewRecordDTO>();
		ReviewRecordDTO review = null;
		
		while((line=buffReader.readLine())!=null) {
			review = mapper.readValue(line, ReviewRecordDTO.class);
			if(review.getParentID().compareTo(id_product)==0) result.add(review);
		}
		

		historyReader.close();
		return result;
	}
}