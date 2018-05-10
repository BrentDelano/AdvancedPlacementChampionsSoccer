import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a soccer ball, which is a MovingObject
 * @author Brent Delano
 * @version
 *
 */
public class Ball extends MovingObject {
	
	private PImage ball;
	
	public Ball() {
		super(10, 10, 15, 15);
		ball = new PImage();
	}
	
	public Ball(int x, int y, int d) {
		super(x, y, d, d);
		ball = new PImage();
	}
	

	public void setup(PApplet drawer)
	{
		ball = drawer.loadImage("ball.png");
	}
	
	public void draw(PApplet drawer) {
		drawer.image(ball, getX(), getY(), getWidth(), getHeight());
		if (!isOnSurface())
			fall(new Surface());
	}

	public void collision() {
		// implement later
	}
}