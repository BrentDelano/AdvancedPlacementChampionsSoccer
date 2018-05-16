import processing.core.PApplet;

/**
 * Represents the surface of the soccer field, which is a Physics Object
 * @author Brent Delano
 * @version 5/10/18
 *
 */
public class Surface extends PhysicsObject {
	
	public Surface(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public boolean isPointInHorizontalSurface(int otherX, int otherY) {
		if (otherX >= getX() && otherY >= getY() && otherX <= getX() + getWidth() && otherY <= getY() + getHeight())
			return true;
		return false;
	}
	
	public void draw(PApplet drawer) {
		drawer.rectMode(drawer.CENTER);
		drawer.fill(51);
		drawer.stroke(255);

		drawer.rect(getX(), getY(), getWidth(), getHeight());
	}
}
