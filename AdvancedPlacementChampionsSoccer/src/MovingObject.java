import processing.core.PApplet;

public abstract class MovingObject extends PhysicsObject {
	
	private double vY, gravity;
	private boolean onSurface;
	
	public MovingObject(int x, int y, int w, int h) {
		super(x, y, w, h);
		vY = 0.0;
	}
	
	public void fall(Surface s) {
		if (!onSurface) {
			if(super.getY() <  s.getY() - super.getHeight()) {
				setY((int)(super.getY() + gravity));
				gravity += 0.5;
			} else {
				gravity = 0;
			}
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
	public abstract void collision();

}
