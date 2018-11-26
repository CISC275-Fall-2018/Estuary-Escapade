package gameobject;

public abstract class GameObject {

	private int xPos;
	private int yPos;
	private int depth;
	private int xSize;
	private int ySize;
	private boolean visible;
	private String imagePath;

	public GameObject(int xPos, int yPos, int depth, int xSize, int ySize, String imagePath) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.depth = depth;
		this.xSize = xSize;
		this.ySize = ySize;
		this.imagePath = imagePath;
		setVisible(true);
	}

	public boolean clickedOn(int clickX, int clickY) {
		// Given a click x and y this returns a boolean for whether or not this object
		// was clicked on
		if(!visible) {
			return false;
		}
		return ((clickX >= xPos && clickX <= xPos + xSize) && (clickY >= yPos && clickY <= yPos + ySize));
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getxSize() {
		return xSize;
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public int getySize() {
		return ySize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	// Every object should have an update function, if the object does not move it
	// should be empty
	public abstract void update();

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
