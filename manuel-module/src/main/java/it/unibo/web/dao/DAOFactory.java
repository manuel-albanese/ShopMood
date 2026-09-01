package it.unibo.web.dao;



import it.unibo.web.dao.files.FilesDAOFactory;


public abstract class DAOFactory {


	public static final int FILES = 0;
	
	public static DAOFactory getDAOFactory(int whichFactory) {
		switch ( whichFactory ) {
		case FILES:
			return new FilesDAOFactory();
		default:
			return null;
		}
	}

	public abstract ProductRecordDAO getProductRecordDAO();
	
	public abstract NoteRecordDAO getNoteRecordDAO();
	
	public abstract ReviewRecordDAO getReviewRecordDAO();
	
	public abstract LabelRecordDAO getLabelRecordDAO();
}
