package it.unibo.web.strategy;


import java.util.Map;

import it.unibo.web.beans.*;


public interface RecommenderStrategy {
	
	 public Map<String, ProductRecordDTO> recommendProducts(RecommendContext context);
}
