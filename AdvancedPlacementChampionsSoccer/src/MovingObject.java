import processing.core.PApplet;

/**
 * Represents all object that moves, which are also PhysicsObjects
 * @author Brent Delano
 * @version 5/15/18
 *
 */
public abstract class MovingObject extends PhysicsObject {

	private double vX, vY;
	private boolean onSurface;
	private boolean canMoveRight, canMoveLeft, canMoveUp;

	public MovingObject(float x, float y, float w, float h) {
		super(x, y, w, h);
		vX = 0.0;
		vY = 0.0;
		canMoveRight = true;
		canMoveLeft = true;
		canMoveUp = true;
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
	
	public void applyFriction() {
		if (isOnSurface()) {
			double friction;
			if (Math.abs(getVX()) < 0.05) 
				friction = Math.abs(getVX());
			else
				friction = 0.05;

			if (getVX() > 0) {
				setVX(getVX() - friction);
			}
			if (getVX() < 0) {
				setVX(getVX() + friction);
			} 
		}
	}

	public void act() {
		setX((float) (getX() + vX));
		setY((float) (getY() + vY));
	}

	public double getVX() {
		return vX;
	}

	public double getVY() {
		return vY;
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

	public boolean canMoveRight() {
		return canMoveRight;
	}

	public boolean canMoveLeft() {
		return canMoveLeft;
	}
	
	public boolean canMoveUp() {
		return canMoveUp;
	}

	public void setRightMobility(boolean m) {
		canMoveRight = m;
	}

	public void setLeftMobility(boolean m) {
		canMoveLeft = m;
	}
	
	public void setUpwardsMobility(boolean m) {
		canMoveUp = m;
	}

	public abstract void draw(PApplet drawer);
	
}
