package model;

public class EndModel extends Model {

	private int score;
	private boolean quizCorrect;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isQuizCorrect() {
		return quizCorrect;
	}

	public void setQuizCorrect(boolean quizCorrect) {
		this.quizCorrect = quizCorrect;
	}

	public EndModel(int frameWidth, int frameHeight) {
		super(frameWidth, frameHeight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Model nextModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
