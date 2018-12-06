package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import controller.CodeListener;
import controller.Controller;
import controller.CustomKeyListener;
import controller.CustomMouseListener;
import gameobject.Animal;
import gameobject.GameObject;
import gameobject.Question;
import model.QuizModel;

public class ViewContainer implements Serializable {
	private JFrame frame;
	private JLayeredPane pane;
	private View view;
	private TimerImage timerImage;
	private int width;
	private int height;

	private final static String title = "Estuary Escapade";

	public ViewContainer(int cycles) {
		frame = new JFrame(title);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // This fullscreens the game
		frame.setUndecorated(true); // This removes the window border
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = frame.getLayeredPane();
		// These get the size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.width;
		height = screenSize.height;
	}

	public void initialize(CustomMouseListener m, CustomKeyListener k, CodeListener c, ArrayList<GameObject> o,
			int cycles) {
		/*
		 * This adds the MouseListener to the frame and initializes the view, this has
		 * to happen after the constructor because the model needs to be set up first
		 * and that requires the size of the screen
		 */
		pane.addMouseListener(m);
		pane.addMouseMotionListener(m);
		frame.addKeyListener(k);
		view = new TitleView(title, width, height, c, o);
		timerImage = new TimerImage(cycles); // Adds timer image
	}

	public void next(ArrayList<GameObject> o) {
		view = view.nextView(o);
		resetView();
	}

	public void timeUp(QuizModel model) {
		// This is only called if the view is a ObjectView but view needs to be cast to
		// access timeUp
		view = ((ObjectView) view).timeUp(model.getQuestion());
		resetView();
	}

	public void questionAnswered(ArrayList<GameObject> o, int score, boolean quizCorrect) {
		view = ((QuizView) view).questionAnswered(o, score, quizCorrect);
		resetView();
	}

	public void resetView() {
		// If this is not done the JFrame will not display properly and things will look
		// wrong
		if (view instanceof ObjectView) {
			timerImage.setFrameSize(view.getWidth(), view.getHeight());
			((ObjectView) view).passTimer(timerImage);
		}
		pane.removeAll();
		pane.add(view, JLayeredPane.DEFAULT_LAYER);
		frame.revalidate();
		frame.repaint();
	}

	public void resetView(JComponent component) {
		// If this is not done the JFrame will not display properly and things will look
		// wrong
		pane.remove(component);
		frame.revalidate();
		frame.repaint();
	}

	public void start() {
		pane.add(view, JLayeredPane.DEFAULT_LAYER);
		frame.pack();
		frame.setAutoRequestFocus(true); // frame must be in focus for key input to work
		frame.setVisible(true);
	}

	public void repaint(int time) {
		timerImage.update(time);
		frame.repaint();
	}

	public void close() {
		// Program is set to end on window Close so this will terminate everything
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public void estuaryPopup(Animal a, CodeListener cl) {
		EstuaryPopup pop = new EstuaryPopup(a, cl, width);
		pop.setBounds(width / 4, height / 4, width / 2, height / 2);
		pane.add(pop, JLayeredPane.POPUP_LAYER);
		frame.revalidate();
		frame.repaint();
	}

	public void researchPopup(Animal a, CodeListener cl) {
		ResearchPopup pop = new ResearchPopup(a, cl, width, height);
		pop.setBounds(width / 4, 0, width / 2, height);
		pane.add(pop, JLayeredPane.POPUP_LAYER);
		frame.revalidate();
		frame.repaint();
	}

	public void tutorialPopup1(Controller controller) {
		TutorialPopup pop = new TutorialPopup(1, controller, width);
		pop.setBounds(width / 4, height / 6, width / 2, (height * 2) / 3);
		pane.add(pop, JLayeredPane.POPUP_LAYER);
		frame.revalidate();
		frame.repaint();
	}

	public void tutorialPopup2(Controller controller) {
		TutorialPopup pop = new TutorialPopup(2, controller, width);
		pop.setBounds(width / 4, height / 6, width / 2, (height * 2) / 3);
		pane.add(pop, JLayeredPane.POPUP_LAYER);
		frame.revalidate();
		frame.repaint();
	}

	public boolean checkObjectView() {
		return (view instanceof ObjectView);
	}

	public boolean checkQuizView() {
		return (view instanceof QuizView);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public static String getTitle() {
		return title;
	}

	public void flash() {
		if (view instanceof ResearchView) {
			view.flash();
		}
	}

	public void loadView(ViewContainer oldViewContainer) {
		pane = oldViewContainer.pane;
		view = oldViewContainer.view;
		timerImage = oldViewContainer.timerImage;
		width = oldViewContainer.width;
		height = oldViewContainer.height;
		frame.setContentPane(pane);
		resetView();
	}

	public void loadTitle(CodeListener cl, ArrayList<GameObject> o) {
		view = new TitleView(title, width, height, cl, o);
		resetView();
	}

	public void loadEstuary(CodeListener cl, ArrayList<GameObject> o) {
		view = new EstuaryView(width, height, o, cl);
		resetView();
	}

	public void loadResearch(CodeListener cl, ArrayList<GameObject> o) {
		view = new ResearchView(width, height, o, cl);
		resetView();
	}

	public void loadQuiz(Question q, CodeListener cl, ArrayList<GameObject> o) {
		view = new QuizView(width, height, q, o, cl);
		resetView();
	}
	
	public void loadEnd(CodeListener cl, ArrayList<GameObject> o, boolean quizCorrect) {
		view = new EndView(width, height, o, cl, height, quizCorrect);
		resetView();
	}

}
