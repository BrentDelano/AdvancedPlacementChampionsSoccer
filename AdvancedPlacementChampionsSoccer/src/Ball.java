import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a soccer ball, which is a MovingObject
 * @author Brent Delano
 * @version 5/10/18
 *
 */
public class Ball extends MovingObject {

	PImage ball;

	public Ball(float x, float y, float d) { 
		super(x, y, d, d);
		ball = new PImage();
	}

	public void setup(PApplet drawer) {
		//		drawer.translate(getX(), getY(), 0);
		//		drawer.fill(255);
		//		drawer.noStroke();
		ball = drawer.loadImage("ball.png");
	}

	public void draw(PApplet drawer) {
		//		drawer.lights();
		//		drawer.sphere(getWidth());
		drawer.image(ball, getX(), getY(), getWidth(), getHeight());
	}

	public void applyFriction() {
		if (isOnSurface()) {
			if (getVX() > 0) {
				setVX(getVX() - 0.05);
			}
			if (getVX() < 0) {
				setVX(getVX() + 0.05);
			} 
		}
	}

	public void fall(Surface s) {
		if (getY() <= s.getY() - getHeight()) {
			setY((float) (getY() + getVY()));
			setVY(getVY() + 0.5);
		} else {
			setState(true);
			if (Math.abs(getVY()) > 1.75) {
				bounce(); 
			} else {
				setVY(0);
				setY(s.getY() - getHeight());
			}
		}
	}

	public void bounce() {
		setVY(-Math.abs(getVY()) + Math.abs(getVY() * 0.2));
		setState(false);
	}

	public void kicked(int side) {

	}
}