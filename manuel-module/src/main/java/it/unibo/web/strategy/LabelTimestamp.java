package it.unibo.web.strategy;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.web.beans.ProductRecordDTO;
import it.unibo.web.beans.RecommendContext;
import it.unibo.web.beans.Recommendation;
import it.unibo.web.beans.ReviewRecordDTO;


class LabelTimestamp implements RecommenderStrategy{
		private final static int event = 1;
		private final static double decay = -1/200;
		private final static int constant = 1;
		private static final Map<String, List<String>> EMOTION_MAP = new HashMap<>();
		
		static {
	        EMOTION_MAP.put("stressed", Arrays.asList(
	            "Beauty and Personal Care", "Health and Household", 
	            "Health and Personal Care", "Grocery and Gourmet_Food"
	        ));

	        EMOTION_MAP.put("relaxed", Arrays.asList(
	            "Books", "Kindle Store", "Digital Music", "Video Games",
	            "Movies and TV", "Patio Lawn_and_Garden", "Arts Crafts_and_Sewing"
	        ));

	        EMOTION_MAP.put("neutral", Collections.emptyList());
	    }
		
		
		@Override
		public List<Recommendation> recommendProducts(RecommendContext context)  {
		
				Map<String, Recommendation> recMap = new HashMap<>();
				float val;
				float factor;
				String idP;

				try {
				for(ReviewRecordDTO review : context.getReviews()) {			
					idP = review.getParentID();
					
					
					if(recMap.get(idP)==null) recMap.put(idP, new Recommendation(0, idP));
					Recommendation r = recMap.get(idP);
					val=r.getScore();
					
					factor = (float) (event*review.getRating()/constant);
					factor = factor * this.returnFactor(context.getProducts(),idP,context.getLabel())
							*this.returnFactorTimestamp(review,context);
					
					recMap.get(idP).setScore((float) (val + factor));
							
				}
				}
				 catch (IllegalArgumentException e1) {
					 System.out.println("The CSV file doesn't contain enough information");
				 }

				ArrayList<Recommendation> res = new ArrayList<Recommendation>(recMap.values());	
				res.sort((p1, p2) -> Float.compare(p2.getScore(), p1.getScore()));
				return res;
			}
		
		private float returnFactor(List<ProductRecordDTO> products, String ID, String label) {
			
			List<String> categories = null;
			float factor = 1;
			for(ProductRecordDTO product : products) {
				if(product.getParentID().compareTo(ID)==0) {
						 if((categories=EMOTION_MAP.get(label))!=null && !categories.isEmpty()) {
							 if(categories.contains(product.getCategory())) factor = 1.5f;
							 else factor = 0.5f;
						 }
				}
			}
			 
			 
			return factor; 
		}
		
		private float returnFactorTimestamp(ReviewRecordDTO review,RecommendContext context) {
			
			int dayNow = LocalDateTime.now().toLocalDate().getDayOfYear();
			long timeUnixNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);					
			long timeUnix = review.getTimestamp()/1000;
			LocalDateTime timestamp = LocalDateTime.ofEpochSecond(timeUnix, 0, ZoneOffset.UTC);
			
			
			@SuppressWarnings("unchecked")
			HashMap<String, Integer> days = (HashMap<String, Integer>) context.getContext().getAttribute("days");
			int day=-1;
			
			
			double diffInDays = Math.max(0, (timeUnixNow - timeUnix) / (24*60*60)); //Difference in days
			float factor = (float) Math.exp(decay*(diffInDays));
			
			
			if(days.get(context.getUserID())!=null) {
				day = days.get(context.getUserID());
				if(day==dayNow &&  day==timestamp.toLocalDate().getDayOfYear())
					factor = (float) (factor * 1.5);
			} 
			
		    int monthNow = LocalDate.now().getMonthValue();   
		    int monthPast = timestamp.toLocalDate().getMonthValue();
		    int rawDiff = Math.abs(monthNow - monthPast);
		    int circularDiff = Math.min(rawDiff, 12 - rawDiff); 
			factor = (float) (factor *  1.5f - (circularDiff / 6.0f));
		
			
			return factor;
		}


}
