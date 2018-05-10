import processing.core.PApplet;

public abstract class MovingObject extends PhysicsObject {
	
	private double vY;
	private boolean onSurface;
	
	public MovingObject(int x, int y, int w, int h) {
		super(x, y, w, h);
		vY = 0.0;
	}
	
	public void fall(Surface s) {
		vY += 30.0;
		setY((int) vY);
		if (s.isPointInSurface(getX() + getWidth() / 2, getY() + getHeight())) {
			setY(getY() - (getY() + getHeight() - s.getY()));
			onSurface = true;
			vY = 0;
		}
	}
	
	public void setVY(int v) {
		vY = v;
	}
	
	public boolean isOnSurface() {
		return onSurface;
	}
	
	public void setState(boolean s) {
		onSurface = s;
	}	
	
	public abstract void draw(PApplet drawer);
}
