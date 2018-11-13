package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import gameobject.GameObject;

public abstract class View extends JPanel {
	private int frameWidth;
	private int frameHeight;
	private ArrayList<GameObject> objects;
	
	final static Color SEA_BLUE = Color.decode("#006994");

	public View(int width, int height, ArrayList<GameObject> objects) {
		frameWidth = width;
		frameHeight = height;
		this.objects = objects;

		setSize(frameWidth, frameHeight);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}
	
	public abstract void update(ArrayList<GameObject> objects);

	public abstract View nextView(ArrayList<GameObject> objects);

	public int getFrameWidth() {
		return frameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}

	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
	}

}
