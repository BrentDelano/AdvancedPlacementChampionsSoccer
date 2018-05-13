import processing.core.PApplet;

/**
 * Represents all object that moves, which are also PhysicsObjects
 * @author Brent Delano
 * @version 5/10/18
 *
 */
public abstract class MovingObject extends PhysicsObject {

	private double vX, vY;
	private boolean onSurface;

	public MovingObject(int x, int y, int w, int h) {
		super(x, y, w, h);
		vX = 0.0;
		vY = 0.0;
	}

	public void fall(Surface s) {
		if (getY() <= s.getY() - getHeight()) {
			setY((float) (getY() + vY));
			vY += 0.5;
		} else {
			vY = 0;
			onSurface = true;
			setY(s.getY() - getHeight());
		}
	}
	
	public void actHorizontally() {
		setX((float) (getX() + vX));
	}

	public double getVX() {
		return vX;
	}
	
	public void setVX(double v) {
		vX = v;
	}
	
	public void setVY(double v) {
		vY = v;
	}

	public boolean isOnSurface() {
		return onSurface;
	}

	public void setState(boolean s) {
		onSurface = s;
	}

	public abstract void draw(PApplet drawer);

	public abstract void collision();

}
