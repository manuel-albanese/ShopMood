package it.unibo.web.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import it.unibo.web.beans.LabelDTO;
import it.unibo.web.beans.NoteRecordDTO;
import it.unibo.web.beans.ProductRecordDTO;
import it.unibo.web.beans.RecommendContext;
import it.unibo.web.beans.Recommendation;
import it.unibo.web.beans.ReviewRecordDTO;
import it.unibo.web.dao.DAOFactory;
import it.unibo.web.dao.LabelRecordDAO;
import it.unibo.web.dao.NoteRecordDAO;
import it.unibo.web.dao.ProductRecordDAO;
import it.unibo.web.dao.ReviewRecordDAO;
import it.unibo.web.strategy.RecommenderStrategy;
import it.unibo.web.strategy.StrategyFactory;

public class mainTest {
	public static void main(String argv[]) throws Exception {
		
		StrategyFactory strategyFactory = new StrategyFactory();
		DAOFactory persistenceFactory = DAOFactory.getDAOFactory(0);
		
		ProductRecordDAO productDAO = persistenceFactory.getProductRecordDAO();
		NoteRecordDAO noteDAO = persistenceFactory.getNoteRecordDAO();
		RecommenderStrategy strat = strategyFactory.getStrategy(StrategyFactory.TEXT_ONLY);
		
		

		List<ProductRecordDTO> products = productDAO.readAll();
		
		List<NoteRecordDTO> notes  = noteDAO.readByUser("AFW2PDT3AMT4X3PYQG7FJZH5FXFA");
		System.out.println("NOTE" + notes.getFirst().toString());
		
		ReviewRecordDAO reviewDAO = persistenceFactory.getReviewRecordDAO();		
		List<ReviewRecordDTO> reviews = reviewDAO.readByUser("AFW2PDT3AMT4X3PYQG7FJZH5FXFA");
		
		
		LabelRecordDAO labelDAO = persistenceFactory.getLabelRecordDAO();
		LabelDTO label = labelDAO.readByUserLast("AFW2PDT3AMT4X3PYQG7FJZH5FXFA");
		

		NoteRecordDTO note = new NoteRecordDTO("TEST", "980000AA", LocalDateTime.now().withNano(0));
		noteDAO.create(note);
		
		
		RecommendContext context =  new RecommendContext(label.getLabel(),"AFW2PDT3AMT4X3PYQG7FJZH5FXFA",notes,products,reviews);
		
		
		List<Recommendation> recs = strat.recommendProducts(context);
	
		HashMap<ProductRecordDTO, Float> result = new HashMap<ProductRecordDTO, Float>();
		for(Recommendation r : recs) result.put(productDAO.read(r.getID()), r.getScore());

		/*
		for(ProductRecordDTO r : result.keySet()) {
			System.out.println("TESTING    " + r.getName() + "|||||" + result.get(r));
		}
		*/

	}
}
