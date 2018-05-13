/**
 * Represents all object that has properties of physics
 * @author Brent Delano
 * @version 5/10/18
 *
 */
public abstract class PhysicsObject {

	private float x, y;
	private float width, height;
	
	public PhysicsObject(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setX(float d) {
		this.x = d;
	}
	
	public void setY(float y) {
		this.y = y;
	}		
}
