package it.unibo.web.strategy;


import java.util.*;

import it.unibo.web.beans.RecommendContext;
import it.unibo.web.beans.Recommendation;
import it.unibo.web.beans.ReviewRecordDTO;


class HistoryOnlyStrategy implements RecommenderStrategy{
	private final static int event = 1;
	private final static int constant = 1;

	@Override
	public List<Recommendation> recommendProducts(RecommendContext context) {
		
		Map<String, Recommendation> recMap = new HashMap<>();
		float val;
		float factor;
		String idP;

		for(ReviewRecordDTO review : context.getReviews()) {			
			idP = review.getParentID();
			
			
			if(recMap.get(idP)==null) recMap.put(idP, new Recommendation(0, idP));
			Recommendation r = recMap.get(idP);
			val=r.getScore();
			
			factor = (float) (event*review.getRating()/constant);
			
			recMap.get(idP).setScore((float) (val + factor));
		}
		
		
		ArrayList<Recommendation> res = new ArrayList<Recommendation>(recMap.values());	
		res.sort((p1, p2) -> Float.compare(p2.getScore(), p1.getScore()));
		return res;
	}


}
