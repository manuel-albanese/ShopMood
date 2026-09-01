package it.unibo.web.strategy;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.web.beans.NoteRecordDTO;
import it.unibo.web.beans.ProductRecordDTO;
import it.unibo.web.beans.RecommendContext;
import it.unibo.web.beans.Recommendation;

class TextOnlyStrategy implements RecommenderStrategy{

	@Override
	public List<Recommendation> recommendProducts(RecommendContext context)  {
		
		Map<String, Recommendation> recMap = new HashMap<>();
		float val;
		String idP;

		String name = null;
		
		
		StringBuilder sb = new StringBuilder();
        for (NoteRecordDTO note : context.getNotes()) sb.append(note.getNote().toLowerCase().trim()).append(" ");  
        String fullNotesText = sb.toString().trim().toLowerCase();
        
        if (fullNotesText.isEmpty()) {
            return new ArrayList<>();
        }
			
			for(ProductRecordDTO product : context.getProducts()) {
				idP = product.getParentID();
				name = product.getName().toLowerCase().trim();

					try {
						String title = this.extractMainTitle(name);
						
						if(fullNotesText.contains(title) && title.length() > 3) {
							
							if(recMap.get(idP)==null) recMap.put(idP, new Recommendation(0, idP));
							 val = recMap.get(idP).getScore();
							recMap.get(idP).setScore((float) (val + 1));
						}
							
					}
					catch (IllegalArgumentException e1) {
						System.out.println("The CSV file doesn't contain enough information");
					}
						
			}


		
		

		ArrayList<Recommendation> res = new ArrayList<Recommendation>(recMap.values());	
		res.sort((p1, p2) -> Float.compare(p2.getScore(), p1.getScore()));
		return res;
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
