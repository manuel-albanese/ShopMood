package it.unibo.web.strategy;


import java.util.*;

import it.unibo.web.beans.RecommendContext;
import it.unibo.web.beans.Recommendation;
import it.unibo.web.beans.ReviewRecordDTO;


class PopularityStrategy implements RecommenderStrategy{

	@Override
	public List<Recommendation> recommendProducts(RecommendContext context)  {
		
		Map<String, Recommendation> recMap = new HashMap<>();
		float val;
		String idP;


		for(ReviewRecordDTO product : context.getReviews()) {			
			idP = product.getParentID();
			
			
			if(recMap.get(idP)==null) recMap.put(idP, new Recommendation(0, idP));
			Recommendation r = recMap.get(idP);
			val=r.getScore();

			
			recMap.get(idP).setScore((float) (val + 1));
					
		}
		

		ArrayList<Recommendation> res = new ArrayList<Recommendation>(recMap.values());	
		res.sort((p1, p2) -> Float.compare(p2.getScore(), p1.getScore()));
		return res;
	}


}
