package it.unibo.web.dao.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.web.beans.ProductRecordDTO;
import it.unibo.web.dao.ProductRecordDAO;
import tools.jackson.databind.ObjectMapper;

public class FilesProductRecordDAO implements ProductRecordDAO {

	private static final String HISTORY = "meta_Video_Games.jsonl";
	private static final String PATH = 
			FilesProductRecordDAO.class.getClassLoader().getResource(HISTORY).getPath();
	private static List<ProductRecordDTO> cachedProducts = null;

	@Override
	public ProductRecordDTO read(String id_product) throws IOException, SQLException {
	ProductRecordDTO product = null;
		
        for (ProductRecordDTO p : loadAll()) {
			if(p.getParentID().compareTo(id_product)==0) {
				product = p;
				break;
			} 
        }
	
		return product;

	}

	@Override
	public List<ProductRecordDTO> readAll() throws IOException {
		
		return this.loadAll();
	}

	@Override
	public List<ProductRecordDTO> readByCat(String category) throws IOException,SQLException  {
        List<ProductRecordDTO> result = new ArrayList<>();
        for (ProductRecordDTO p : loadAll()) {
            if (p.getCategory().compareTo(category) == 0) result.add(p);
        }
        return result;
	}
	
    private synchronized List<ProductRecordDTO> loadAll() throws IOException {
        if (cachedProducts == null) {
            FileReader historyReader = new FileReader(PATH);
            BufferedReader buffReader = new BufferedReader(historyReader);
            ObjectMapper mapper = new ObjectMapper();
            String line;
            ArrayList<ProductRecordDTO> result = new ArrayList<>();
            ProductRecordDTO p = null;

            while ((line = buffReader.readLine()) != null) {
            	p = mapper.readValue(line, ProductRecordDTO.class);
                result.add(p);
            }
            historyReader.close();
            cachedProducts = result;
        }
        for(ProductRecordDTO p : cachedProducts) p.setScore( (float) 0 );
        return cachedProducts;
    }


}