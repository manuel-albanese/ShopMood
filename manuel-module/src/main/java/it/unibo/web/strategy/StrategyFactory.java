package it.unibo.web.strategy;

public class StrategyFactory {
	public static final int HISTORY_ONLY = 0;
	public static final int LABEL_NO_TS = 1;
	public static final int LABEL_TS = 2;
	public static final int TEXT_ONLY = 3;
	public static final int POPULARITY = 4;

	public StrategyFactory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RecommenderStrategy getStrategy(int type) {
		switch(type) {
		  case HISTORY_ONLY:
		    return new HistoryOnlyStrategy();
		  case LABEL_NO_TS:
			    return new LabelNoTimestamp();		
		  case LABEL_TS:
			    return new LabelTimestamp();	
		  case TEXT_ONLY:
			    return new TextOnlyStrategy();	
		  case POPULARITY:
			    return new PopularityStrategy();	
		default:
		    return null;
		}
	}

}
