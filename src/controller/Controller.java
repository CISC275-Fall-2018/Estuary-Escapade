package controller;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import gameobject.Animal;
import model.EndModel;
import model.GameStateModel;
import model.Model;
import model.QuizModel;
import model.TitleModel;
import view.ViewContainer;

public class Controller implements CodeListener, Serializable {

	private static final long serialVersionUID = 1L;
	private Timer t;
	private Model model;
	private ViewContainer view;
	private AbstractAction updateAction;
	private CustomMouseListener mouseListener;
	private CustomKeyListener keyListener;
	private boolean timerRunning;
	private int time;
	private int width;
	private int height;

	private final int cycles = 750; // This controlls how long the game runs for
	private final int timerDelay = 40; // The delay between every game state update

	public Controller() {
		time = 0;
		timerRunning = false; // The time will only start once the player leaves the start screen

		view = new ViewContainer(cycles);

		width = view.getWidth();
		height = view.getHeight();

		model = new TitleModel(width, height, this);
		mouseListener = new CustomMouseListener(model);
		keyListener = new CustomKeyListener(this);
		view.initialize(mouseListener, keyListener, this, model.getGameObjects(), cycles);

		updateAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * This runs for every time step in the game, the model updates then passes
				 * objects to the view and the view draws a new frame. If the timer is running
				 * the time increments and if the time runs out the TIMEUP code is emitted
				 */
				model.update();
				view.repaint(time);
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
			timerRunning = false;
			time = 0;
			break;
		case STARTTIMER:
			timerRunning = true;
		case RIGHT:
			if (model instanceof QuizModel && view.checkQuizView()) {
				model = ((QuizModel) model).questionAnswered(true);
				view.questionAnswered(model.getGameObjects(), ((EndModel) model).getScore());
			}
			break;
		case WRONG:
			if (model instanceof QuizModel && view.checkQuizView()) {
				model = ((QuizModel) model).questionAnswered(false);
				view.questionAnswered(model.getGameObjects(), ((EndModel) model).getScore());
			}
			break;
		case FLASHSCREEN:
			view.flash();
			break;
		case PAUSE:
			t.stop();
			break;
		case RESUME:
			view.resetView();
			t.restart();
			break;
		}
	}

	public void saveState() {
		String fileName = "estuaryGameState.est";
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(model);
			out.close();
		} catch (Exception e) {
			System.out.println("Problem saving");
			e.printStackTrace();
		}
	}

	public void loadState() {
		codeEmitted(Code.PAUSE);
		JFileChooser jf = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Estuary Game Files", "est");
		jf.setFileFilter(filter);
		try {
			int r = jf.showOpenDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) {
				// set the label to the path of the selected directory
				String filename = jf.getSelectedFile().getAbsolutePath();
				FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream in = new ObjectInputStream(fis);
				Controller oldController = (Controller) in.readObject();
				loadValues(oldController);
				in.close();
			}
		} catch (Exception e) {
			System.out.println("Problem loading");
			e.printStackTrace();
		}
		codeEmitted(Code.RESUME);
	}

	private void loadValues(Controller oldController) {
		System.out.println("Got to loadValues");
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

	@Override
	public void estuaryPopup(Animal a) {
		this.codeEmitted(Code.PAUSE);
		view.estuaryPopup(a, this);
	}

	@Override
	public void researchPopup(Animal a) {
		this.codeEmitted(Code.PAUSE);
		view.researchPopup(a, this);
	}

}
