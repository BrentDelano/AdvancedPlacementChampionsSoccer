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

//	public void actHorizontally() {
//		final double friction = 0.05;
//		setX((float) (getX() + getVX()));
//		if (getVX() > 0) {
//			while(getVX() > 0) 
//				setVX(getVX() - friction);
//		}
//		if (getVX() < 0) {
//			while(getVX() < 0) 
//				setVX(getVX() + friction);
//		}
//		setVX(0);
//	}

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
		System.out.println(getVY());
		setVY(-Math.abs(getVY()) + Math.abs(getVY() * 0.2));
		System.out.println(getVY());
		setState(false);
	}

	public void kicked(int side) {

	}
}