package it.unibo.web.test;

import java.util.Map;

import it.unibo.web.beans.ProductRecordDTO;
import it.unibo.web.beans.RecommendContext;
import it.unibo.web.strategy.RecommenderStrategy;
import it.unibo.web.strategy.StrategyFactory;

public class mainTest {
	public static void main(String argv[]) throws Exception {
		
		StrategyFactory strategyFactory = new StrategyFactory();

		

		RecommenderStrategy strat = strategyFactory.getStrategy(StrategyFactory.HISTORY_ONLY);
		
		RecommendContext context =  
				new RecommendContext(StrategyFactory.HISTORY_ONLY,"AFW2PDT3AMT4X3PYQG7FJZH5FXFA",null);
		
	
		
		Map<String, ProductRecordDTO> recs = strat.recommendProducts(context);

		for(ProductRecordDTO p : recs.values()) {
			System.out.println("TESTING    " + p.getParentID() + 
					"||||" + p.getScore()+  "|||||" + p.toString());
		}
		

	}
}
