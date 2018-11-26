package model;

import java.awt.event.MouseEvent;

import controller.Code;
import controller.CodeListener;
import gameobject.Animal;
import gameobject.Camera;
import gameobject.GameObject;

public class ResearchModel extends Model implements GameStateModel {

	// Used to determine if the animal is fully researched
	private boolean isWeighed = false;
	private boolean isMeasured = false;
	private boolean isPhotographed = false;
	// Used to determine if the user is holding an animal or nots
	private boolean isHolding = false;

	// Different research tools
	// TODO decide what functionality we should have from camera and if it should be
	// move
	private Camera camera = new Camera(50, 50, 1, 100, 100, "images/camera.png");
	// TODO decide if we want to make a ruler class or not
	private Camera ruler = new Camera(50, 300, 1, 100, 100, "images/ruler.png");
	private Animal caught;
	// the EstuaryModel that gave us this ResearchModel
	private EstuaryModel goBack;

	// TODO figure out how to set this relationally based on screen size instead of
	// hard coding
	private int right = 500;
	private int top = -100;

	public boolean isWeighed() {
		return isWeighed;
	}

	public void setWeighed(boolean isWeighed) {
		this.isWeighed = isWeighed;
	}

	public boolean isMeasured() {
		return isMeasured;
	}

	public void setMeasured(boolean isMeasured) {
		this.isMeasured = isMeasured;
	}

	public boolean isPhotographed() {
		return isPhotographed;
	}

	public void setPhotographed(boolean isPhotographed) {
		this.isPhotographed = isPhotographed;
	}

	// Constructor
	public ResearchModel(int frameWidth, int frameHeight, Animal animalCaught, EstuaryModel goBack,
			CodeListener listener) {
		super(frameWidth, frameHeight, listener);
		this.caught = animalCaught;
		this.goBack = goBack;
		instantiateObjects();
	}

	public void instantiateObjects() {
		// TODO: make this so its not hard coded
		this.caught.setxPos(right);
		this.caught.setyPos(top);

		addGameObject(this.caught);
		addGameObject(this.camera);
		addGameObject(this.ruler);
	}

	@Override
	public Model nextModel() {
		return this.goBack;
	}

	@Override
	public void registerClick(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();

		if (isHolding) {
			if (camera.clickedOn(mouseX, mouseY)) {
				this.caught.setxPos(right);
				this.caught.setyPos(top);

				this.camera.setVisible(false);
				this.setPhotographed(true);
				this.setHolding(false);

				if (this.isMeasured() && this.isPhotographed()) {
					doneResearching();
				}

			} else if (ruler.clickedOn(mouseX, mouseY)) {
				this.caught.setxPos(right);
				this.caught.setyPos(top);

				this.ruler.setVisible(false);
				this.setMeasured(true);
				this.setHolding(false);

				if (this.isMeasured() && this.isPhotographed()) {
					doneResearching();
				}
			}
		} else {
			if (caught.clickedOn(mouseX, mouseY)) {
				setHolding(true);
			}
		}
	}

	public void mouseMoved(int mouseX, int mouseY) {
		if (isHolding) {
			this.caught.setxPos(mouseX - caught.getxSize() / 2);
			this.caught.setyPos(mouseY - caught.getySize() / 2);
		}
	}

	private void doneResearching() {
		goBack.researched.add(caught);
		getListener().codeEmitted(Code.NEXT);
	}

	private void measuring() {
		// TODO Re-evaluate does this need to exist?
	}

	private void photographing() {
		// TODO Re-evaluate does this need to exist?
	}

	private void weighing() {
		// TODO Re-evaluate does this need to exist?
	}

	private void setHolding(boolean value) {
		this.isHolding = value;
	}

	private boolean getHolding() {
		return this.isHolding;
	}

	@Override
	public QuizModel timeUp() {
		// Transition to the QuizModel
		return new QuizModel(getFrameWidth(), getFrameHeight(), goBack.researched, getListener());
	}

	@Override
	public void update() {
		// TODO We may need to move some of the positional logic here to move the animal
		// across the screen once the user clicks on it
	}

}
