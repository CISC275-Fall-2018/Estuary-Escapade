package view;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import controller.CodeListener;
import controller.CustomMouseListener;
import gameobject.GameObject;
import model.QuizModel;

public class ViewContainer {
	private JFrame frame;
	private View view;
	private int width;
	private int height;

	private final static String title = "Estuary Escapade";

	public ViewContainer() {
		frame = new JFrame(title);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // This fullscreens the game
		frame.setUndecorated(true); // This removes the window border
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// These get the size of the screen
		width = frame.getContentPane().getWidth();
		height = frame.getContentPane().getHeight();
	}

	public void initialize(CustomMouseListener m, CodeListener c, ArrayList<GameObject> o) {
		/*
		 * This adds the MouseListener to the frame and initializes the view, this has
		 * to happen after the constructor because the model needs to be set up first
		 * and that requires the size of the screen
		 */
		frame.addMouseListener(m);
		frame.addMouseMotionListener(m);
		view = new TitleView(title, width, height, c, o);
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

	private void resetView() {
		// If this is not done the JFrame will not display properly and things will look
		// wrong
		frame.getContentPane().removeAll();
		frame.add(view);
		frame.validate();
		frame.repaint();
	}

	public void start() {
		frame.add(view);
		frame.setVisible(true);
	}

	public void repaint() {
		frame.repaint();
	}

	public void close() {
		// Program is set to end on window Close so this will terminate everything
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public boolean checkObjectView() {
		return (view instanceof ObjectView);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
