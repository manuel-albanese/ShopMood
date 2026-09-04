package it.unibo.web.strategy;


import java.util.*;
import java.util.stream.Collectors;

import it.unibo.web.beans.ProductRecordDTO;
import it.unibo.web.beans.RecommendContext;
import it.unibo.web.beans.ReviewRecordDTO;


class HistoryOnlyStrategy implements RecommenderStrategy{
	private final static int event = 1;
	private final static int constant = 1;

	@Override
	public Map<String, ProductRecordDTO> recommendProducts(RecommendContext context) {
		
		Map<String, ProductRecordDTO> productMap = new HashMap<>();
		for (ProductRecordDTO p : context.getProducts()) productMap.put(p.getParentID(), p);

		float val;
		float factor;
		String idP;

		for(ReviewRecordDTO review : context.getReviews()) {			
			idP = review.getParentID();
			
			
			ProductRecordDTO product = productMap.get(idP);
			val=product.getScore();
			
			factor = (float) (event*review.getRating()/constant);
			
			productMap.get(idP).setScore((float) (val + factor));
		}
		
		
		
		Map<String, ProductRecordDTO> sortedProductMap = productMap.entrySet().stream()
				.filter(entry -> entry.getValue().getScore() > 0)
			    .sorted(Comparator.comparing((Map.Entry<String, ProductRecordDTO> e) -> e.getValue().getScore()).reversed())
			    .collect(Collectors.toMap(
			        Map.Entry::getKey,
			        Map.Entry::getValue,
			        (e1, e2) -> e1,
			        LinkedHashMap::new
			    ));
		
		return sortedProductMap;
	}


}
