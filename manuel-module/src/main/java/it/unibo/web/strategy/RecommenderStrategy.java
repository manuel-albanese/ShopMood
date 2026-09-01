package it.unibo.web.strategy;

import java.util.List;

import it.unibo.web.beans.*;


public interface RecommenderStrategy {
	
	 public List<Recommendation> recommendProducts(RecommendContext context);
}
