import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a soccer ball, which is a MovingObject
 * @author Brent Delano
 * @version 5/15/18
 *
 */
public class Ball extends MovingObject {

	private PImage ball;
	private boolean frozen;

	public Ball(float x, float y, float d) { 
		super(x, y, d, d);
		ball = new PImage();
		frozen = false;
	}

	public void setup(PApplet drawer) {
		ball = drawer.loadImage("ball.png");
	}

	public void draw(PApplet drawer) {
		drawer.pushMatrix();
		drawer.translate((float) (getX() + getWidth()/2.0), (float) (getY() + getHeight()/2.0));
		drawer.rotate((float) (calcSpin()));
		drawer.image(ball, (float) (-getWidth()/2.0), (float) (-getHeight()/2.0), getWidth(), getHeight());
		drawer.translate((float) -(getX() + getWidth()/2.0), (float) -(getY() + getHeight()/2.0));
		drawer.popMatrix();
	}
	
	public void superSpeed()
	{
		setVX(50);
		setVY(5*getVY());
	}

	public void fall(Surface s) {
		if (getY() <= s.getY() - getHeight()) {
			setY((float) (getY() + getVY()));
			setVY(getVY() + 0.5);
		} else {
			setState(true);
			if (Math.abs(getVY()) > 1.75) {
				bounce(); 
			} else {
				setVY(0);
				setY(s.getY() - getHeight());
			}
		}
	}

	public void bounce() {
		setVY(-Math.abs(getVY()) + Math.abs(getVY() * 0.2));
		setState(false);
	}
	
	public double calcSpin() {
		if (getVX() > 0)
			return -Math.pow(2, getVX()) / 4.0;
		else 
			return Math.pow(2, Math.abs(getVX())) / 4.0;
	}
	
	public void freeze() {
		frozen = true;
		
		setRightMobility(false);
		setLeftMobility(false);
		setUpwardsMobility(false);
		
		setVX(0);
		setVY(0);
	}
	
	public void unfreeze() {		
		frozen = false;
		
		setRightMobility(true);
		setLeftMobility(true);
		setUpwardsMobility(true);
	}
	
	public boolean isFrozen() {
		return frozen;
	}
}