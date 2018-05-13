import processing.core.PApplet;

/**
 * Represents the surface of the soccer field, which is a Physics Object
 * @author Brent Delano
 * @version 5/10/18
 *
 */
public class Surface extends PhysicsObject {
	
	public Surface(float x, float y, float w, float h) {
		super(x, y, w, h);
	}
	
	public boolean isPointInSurface(int otherX, int otherY) {
		if (otherX >= getX() && otherY >= getY() && otherX <= getX() + getWidth() && otherY <= getY() + getHeight())
			return true;
		return false;
	}
	
	public void draw(PApplet drawer) {
//		drawer.rotateX(drawer.PI/4);	
//		drawer.rectMode(drawer.CENTER);
		drawer.noFill();
		drawer.stroke(255);

		drawer.rect(getX(), getY(), getWidth(), getHeight());
	}
}
