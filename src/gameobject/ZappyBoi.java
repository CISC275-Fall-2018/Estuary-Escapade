package gameobject;

public class ZappyBoi extends Animal{

	public ZappyBoi(int xPos, int yPos, int depth, int xSize, int ySize) {
		super(xPos, yPos, depth, xSize, ySize);
		this.setImagePath("images/ZappyBoiRight.png");
		this.setName("Bill");
		this.setWeight(7);
		this.setSpeed(4);
		this.setQuestion(new Question(" Eels have to come to the surfact for air every ten minutes.",
				"How often do eels have to come to the surface for air? ", "every 10 minutes",
				"every 5 minutes", "an hour","once a day" ));
		this.setPathLength(200);
	}

	@Override
	public void update() {
		int pathState = getPathState();
		int x = getxPos();
		int y = getyPos();
		int speed = getSpeed();
		int length = getPathLength();

		// our waypoints are located at each third of the way through our path.
		int wayPoint1 = length / 2 - 10;
		int wayPoint2 = length / 2;
		int wayPoint3 = length;

		if (!isMovingForward()) {
			// we multiply by -1 so that we will head the other way, this is
			// executed after we've reached the end of our path and must return.
			speed *= -1;
			wayPoint1 *= -1;
			wayPoint2 *= -1;
			wayPoint3 *= -1;
			this.setImagePath("images/ZappyBoiLeft.png");
			// we switch to a greater than sign because we are headed the
			// opposite direction
			if (pathState > wayPoint1) {
				setxPos(x + speed);
			} else if (pathState > wayPoint2) {
				setyPos(y + speed);
			} else if (pathState > wayPoint3) {
				setxPos(x + (speed));
			}
		} else {
			setImagePath("images/ZappyBoiRight.png");
			if (pathState < wayPoint1) {
				// swim right until waypoint1
				setxPos(x + speed);
			} else if (pathState < wayPoint2) {
				// swim down until waypoint2
				setyPos(y + speed);
			} else if (pathState < wayPoint3) {
				// swim to waypoint3
				setxPos(x + (speed));
			}
		}
		updatePosition();
		
	}

}
