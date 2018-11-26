package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Timer;

import model.GameStateModel;
import model.Model;
import model.QuizModel;
import model.TitleModel;
import view.ViewContainer;

public class Controller implements CodeListener {

	private Timer t;
	private Model model;
	private ViewContainer view;
	private AbstractAction updateAction;
	private CustomMouseListener mouseListener;
	private boolean timerRunning;
	private int time;
	private int width;
	private int height;

	private final int cycles = 500; // This controlls how long the game runs for
	private final int timerDelay = 30; // The delay between every game state update

	public Controller() {
		time = 0;
		timerRunning = false; // The time will only start once the player leaves the start screen

		view = new ViewContainer();

		width = view.getWidth();
		height = view.getHeight();

		model = new TitleModel(width, height, this);
		mouseListener = new CustomMouseListener(model);
		view.initialize(mouseListener, this, model.getGameObjects());

		updateAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * This runs for every time step in the game, the model updates then passes
				 * objects to the view and the view draws a new frame. If the timer is running
				 * the time increments and if the time runs out the TIMEUP code is emitted
				 */
				model.update();
				view.repaint();
				if (timerRunning) {
					time++;
				}
				if (time >= cycles) {
					codeEmitted(Code.TIMEUP);
				}
			}
		};
		t = new Timer(timerDelay, updateAction);
	}

	/*
	 * void codeEmited
	 * 
	 * updates game on click based on command
	 * 
	 * params Code code the command uses
	 */
	@Override
	public void codeEmitted(Code c) {
		switch (c) {
		case NEXT:
			model = model.nextModel();// calls nextmodel and move to next game state
			view.next(model.getGameObjects());
			mouseListener.setModel(model);
			// System.out.println("In: " + model); Used for debugging
			break;
		case EXIT:
			t.stop();
			// Since the program is set to exit on closing this fully terminates the game
			view.close();
			break;
		case TIMEUP:
			// This makes sure the game is in an applicable model then calls the timeUp
			// methods to transition to the Quiz
			if (model instanceof GameStateModel && view.checkObjectView()) {
				model = ((GameStateModel) model).timeUp();
				mouseListener.setModel(model);
				view.timeUp((QuizModel) model);
			}
			// System.out.println("In: " + model); for debugging
			timerRunning = false;
			time = 0;
			break;
		case STARTTIMER:
			timerRunning = true;
		}
	}

	public void start() {
		t.start();
		view.start();
	}

	public Timer getT() {
		return t;
	}

	public void setT(Timer t) {
		this.t = t;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public ViewContainer getViewContainer() {
		return view;
	}

	public void setViewContainer(ViewContainer view) {
		this.view = view;
	}

	public AbstractAction getUpdateAction() {
		return updateAction;
	}

	public void setUpdateAction(AbstractAction updateAction) {
		this.updateAction = updateAction;
	}

}
