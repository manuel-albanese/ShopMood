package it.unibo.web.dao.files;


import it.unibo.web.dao.DAOFactory;
import it.unibo.web.dao.LabelRecordDAO;
import it.unibo.web.dao.NoteRecordDAO;
import it.unibo.web.dao.ProductRecordDAO;
import it.unibo.web.dao.ReviewRecordDAO;




public class FilesDAOFactory extends DAOFactory {
	
	
	@Override
	public ProductRecordDAO getProductRecordDAO(){
		return new FilesProductRecordDAO();
	}

	
	@Override
	public NoteRecordDAO getNoteRecordDAO(){
		return new FilesNoteRecordDAO();
	}


	@Override
	public ReviewRecordDAO getReviewRecordDAO() {
		return new FilesReviewRecordDAO();
	}


	@Override
	public LabelRecordDAO getLabelRecordDAO() {
		return new  FilesLabelRecordDAO();
	}
	
}
