package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import controller.CodeListener;
import gameobject.GameObject;
import gameobject.Question;

public abstract class ObjectView extends View {

	HashMap<GameObject, BufferedImage> map = new HashMap<>();

	public ObjectView(int width, int height, ArrayList<GameObject> objects, CodeListener listener) {
		super(width, height, objects, listener);
		for (GameObject currentObj : getObjects()) {
			// Read the image from the current game object
			BufferedImage img = createImage(currentObj.getImagePath());
			// Place the game object and the animation frames in the hash map as a key-value
			// pair
			map.put(currentObj, img);
		}
	}

	// void paint
	// Paints the updated game objects to the screen
	// params: Graphics g: the graphics object used for the drawImage method

	@Override
	public void paint(Graphics g) {
		for (GameObject object : this.map.keySet()) {
			if(object.isVisible()) {
				g.drawImage(map.get(object), object.getxPos(), object.getyPos(), object.getxSize(), object.getySize(),
						(ImageObserver) this);
			}
		}
	}

	// void update
	// Updates the game objects array
	// params: ArrayList<GameObject> objects: an arraylist of the game objects with
	// updated attributes

	public void update(ArrayList<GameObject> objects) {
		setObjects(objects); // Update the objects attribute
	}

	// BufferedImage createImage
	// Reads an image from the file system and returns it as a BufferedImage, or an
	// IOException if not found
	// params: String imagePath: the path to the image taken from the game object

	private BufferedImage createImage(String imagePath) {
		BufferedImage bufferedImage;
		try {
			// Try to read the file
			bufferedImage = ImageIO.read(new File(imagePath));
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public QuizView timeUp(Question q) {
		return new QuizView(super.getWidth(), super.getHeight(), q, new ArrayList<GameObject>(), super.getListener());
	}

}
