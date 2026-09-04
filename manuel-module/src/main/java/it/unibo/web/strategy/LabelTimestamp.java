package it.unibo.web.strategy;

import java.time.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.web.beans.ProductRecordDTO;
import it.unibo.web.beans.RecommendContext;
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
		public Map<String, ProductRecordDTO> recommendProducts(RecommendContext context)  {
		
			Map<String, ProductRecordDTO> productMap = new HashMap<>();
			for (ProductRecordDTO p : context.getProducts()) productMap.put(p.getParentID(), p);
				float val;
				float factor;
				String idP;

				try {
				for(ReviewRecordDTO review : context.getReviews()) {			
					idP = review.getParentID();
					
					
					ProductRecordDTO product = productMap.get(idP);
					val=product.getScore();
					
					factor = (float) (event*review.getRating()/constant);
					factor = factor * this.returnFactor(context.getProducts(),idP,context.getLabel())
							*this.returnFactorTimestamp(review,context);
					
					productMap.get(idP).setScore((float) (val + factor));
							
				}
				}
				 catch (IllegalArgumentException e1) {
					 System.out.println("The CSV file doesn't contain enough information");
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
