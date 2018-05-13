import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the player in the game, which is a MovingObject
 * @author Brent Delano
 * @version 5/10/18
 */
public class Tekkist extends MovingObject {
	
	private PImage tekkist;
	private boolean isJumping;
	
	public Tekkist() {
		super(100, 10, 100, 135);
		tekkist = new PImage();
		isJumping = false;
	}
	
	public Tekkist(int x, int y, int w, int h) {
		super(x, y, w, h);
		tekkist = new PImage();
		isJumping = false;
	}
	
	public void walkHorizontally(int direction) {
		setX(getX() + direction * 2); 
	}
	
	public void walkVertically(int direction) {
		setY(getY() + -direction * 2);
	}
	
	public void jump() {
		if (isOnSurface()) {
			super.setVY(-40);
			setY((int) (super.getY() -10));
			setIsJumping(true);
			setState(false);
		}
	}
	
	public void setup(PApplet drawer) {
		tekkist = drawer.loadImage("boi.png");
	}
	
	public void setIsJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}
	
	public void draw(PApplet drawer) {
		drawer.image(tekkist, getX(), getY(), getWidth(), getHeight());
		if (!isOnSurface())
		{
			
			fall(new Surface(drawer.width/2, drawer.height/2, drawer.width, drawer.height/2), isJumping);
			if(isJumping)
			{
				if(super.getVy()==0)
				{
					isJumping = false;
				}
			}
		}
	}
	
	public void kick(Tekkist t, Ball b) {
		
	}

	public void collision() {
		// implement later
	}
}
