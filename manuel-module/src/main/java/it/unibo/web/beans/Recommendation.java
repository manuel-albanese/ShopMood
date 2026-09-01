package it.unibo.web.beans;

public class Recommendation {
	private float score;
	private String ID;
	
	
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public Recommendation(float score, String iD) {
		super();
		this.score = score;
		ID = iD;
	}
	@Override
	public String toString() {
		return "Recommendation [score=" + score + ", ID=" + ID + "]";
	}
	
	
	
	
	
}
