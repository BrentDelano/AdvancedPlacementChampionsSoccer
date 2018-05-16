import processing.core.PApplet;

/**
 * Represents all object that moves, which are also PhysicsObjects
 * @author Brent Delano
 * @version 5/10/18
 *
 */
public abstract class MovingObject extends PhysicsObject {

	private double vY, gravity;
	private boolean onSurface;

	public MovingObject(int x, int y, int w, int h) {
		super(x, y, w, h);
		vY = 0.0;
	}

	public void fall(Surface s, boolean isJumping) {
//		if (!onSurface) {
//			if (super.getY() < s.getY() - super.getHeight()) {
//				setY((int) (super.getY() + gravity));
//				gravity += 0.1;
//			} else {
//				gravity = 0;
//			}
//			vY += 0.0;
//			setY((int) vY);
//			if (s.isPointInSurface(getX() + getWidth() / 2, getY() + getHeight())) {
//				setY(getY() - (getY() + getHeight() - s.getY()));
//				onSurface = true;
//				vY = 0;
//			}
//		}
		if(!isJumping)
		{
			if(!onSurface)
			{
				if (s.isPointInHorizontalSurface(getX() + getWidth() / 2, getY() + getHeight())) {
					setY(getY() - (getY() + getHeight() - s.getY()));
					onSurface = true;
					vY = 0;
				}
				if (super.getY() < s.getY() - super.getHeight()) {
					setY((int) (super.getY() + gravity));
					gravity += 0.1;
				} else {
					gravity = 0;
				}
			}
		}
		else {
			vY+=5;
			if (super.getY() < s.getY() - super.getHeight()) {
				setY((int) (super.getY() +vY -gravity));
				gravity +=1;
			} else {
				gravity = 0;
			}
			
		}
	}
	
	public double getVy()
	{
		return vY;
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

	public abstract void collision();

}
