
public class Surface extends PhysicsObject {
	
	public Surface() {
		super(0, 400, 500, 100);
	}
	
	public Surface(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public boolean isPointInSurface(int otherX, int otherY) {
		if (otherX >= getX() && otherY >= getY() && otherX <= getX() + getWidth() && otherY <= getY() + getHeight())
			return true;
		return false;
	}

	public void collision() {
		// implement later
	}
}
