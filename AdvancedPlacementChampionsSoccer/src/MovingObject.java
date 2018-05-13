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
	private boolean canMoveRight, canMoveLeft, canMoveDown;

	public MovingObject(float x, float y, float w, float h) {
		super(x, y, w, h);
		vX = 0.0;
		vY = 0.0;
		canMoveRight = true;
		canMoveLeft = true;
		canMoveDown = true;
	}

	public void fall(Surface s) {
		if (getY() <= s.getY() - getHeight() && canMoveDown) {
			setY((float) (getY() + vY));
			vY += 0.5;
		} else {
			vY = 0;
			if (canMoveDown) {
				onSurface = true;
				setY(s.getY() - getHeight());
			}
		}
	}
	
	public void actHorizontally() {
		if (vX > 0 && canMoveRight || vX < 0 && canMoveLeft)
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

	public boolean canMoveRight() {
		return canMoveRight;
	}
	
	public boolean canMoveLeft() {
		return canMoveLeft;
	}
	
	public boolean canMoveDown() {
		return canMoveDown;
	}
	
	public void setRightMovability(boolean m) {
		canMoveRight = m;
	}
	
	public void setLeftMovability(boolean m) {
		canMoveLeft = m;
	}
	
	public void setDownMovability(boolean m) {
		canMoveDown = m;
	}
	
	public abstract void draw(PApplet drawer);

}
