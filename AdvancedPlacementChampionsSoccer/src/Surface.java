/**
 * Represents the surface of the soccer field, which is a Physics Object
 * @author Brent Delano
 * @version 5/15/18
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
}
