package it.unibo.web.strategy;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.web.beans.NoteRecordDTO;
import it.unibo.web.beans.ProductRecordDTO;
import it.unibo.web.beans.RecommendContext;

class TextOnlyStrategy implements RecommenderStrategy{

	@Override
	public Map<String, ProductRecordDTO> recommendProducts(RecommendContext context)  {
		
		Map<String, ProductRecordDTO> productMap = new HashMap<>();
		for (ProductRecordDTO p : context.getProducts()) productMap.put(p.getParentID(), p);
		float val;
		String idP;

		String name = null;
		
		
		StringBuilder sb = new StringBuilder();
        for (NoteRecordDTO note : context.getNotes()) sb.append(note.getNote().toLowerCase().trim()).append(" ");  
        String fullNotesText = sb.toString().trim().toLowerCase();
        
        if (fullNotesText.isEmpty()) {
            return productMap;
        }
			
			for(ProductRecordDTO product : productMap.values()) {
				idP = product.getParentID();
				name = product.getName().toLowerCase().trim();

					try {
						String title = this.extractMainTitle(name);
						
						if(fullNotesText.contains(title) && title.length() > 3) {
							
							val=product.getScore();
							productMap.get(idP).setScore((float) (val + 1));
						}
							
					}
					catch (IllegalArgumentException e1) {
						System.out.println("The CSV file doesn't contain enough information");
					}
						
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
	
	private String extractMainTitle(String rawTitle) {
		

	    if (rawTitle == null || rawTitle.isEmpty()) return "";


	    String mainTitle = rawTitle.split(":")[0];


	    mainTitle = mainTitle.split("-")[0];
	    
	    if(mainTitle.startsWith("The")) mainTitle = mainTitle.split(" ")[1];
	    else mainTitle = mainTitle.split(" ")[0];

		
	    return mainTitle.trim().toLowerCase();
	}

}
