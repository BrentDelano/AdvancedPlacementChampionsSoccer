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
	
	public Ball() {
		super(50, 10, 30, 30);
		ball = new PImage();
	}

	public Ball(int x, int y, int d) { 
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
	
	public void kicked(int side) {
		
	}

	public void collision() {
		
	}
}