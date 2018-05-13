import processing.core.PApplet;

/**
 * Represents a soccer ball, which is a MovingObject
 * @author Brent Delano
 * @version 5/10/18
 *
 */
public class Ball extends MovingObject {

	public Ball() {
		super(10, 10, 15, 15);
	}

	public Ball(int x, int y, int d) { 
		super(x, y, d, d);
	}

	public void setup(PApplet drawer) {
		drawer.translate(getX(), getY(), 0);
		drawer.fill(255);
		drawer.noStroke();
	}

	public void draw(PApplet drawer) {
		drawer.lights();
		drawer.sphere(getWidth());
	}
	
	public void kicked(int side) {
		
	}

	public void collision() {
		
	}
}