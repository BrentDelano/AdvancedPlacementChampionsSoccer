import processing.core.PApplet;
import processing.core.PImage;


public class Tekkist extends MovingObject {
	
	private PImage tekkist;
	private boolean isJumping;
	
	public Tekkist() {
		super(100, 10, 60, 75);
		tekkist = new PImage();
		isJumping = false;
	}
	
	public Tekkist(int x, int y, int w, int h) {
		super(x, y, w, h);
		tekkist = new PImage();
		isJumping = false;
	}
	
	public void walk(int direction) {
		setX(getX() + direction*2); 
	}
	
	public void jump() {
		if (isOnSurface()) {
			super.setVY(-40);
			setY((int) (super.getY() -10));
			setIsJumping(true);
			setState(false);
		}
	}
	
	public void setup(PApplet drawer)
	{
		tekkist = drawer.loadImage("boi.png");
	}
	
	public void setIsJumping(boolean isJumping)
	{
		this.isJumping = isJumping;
	}
	
	public void draw(PApplet drawer) {
		drawer.image(tekkist, getX(), getY(), getWidth(), getHeight());
		if (!isOnSurface())
		{
			
			fall(new Surface(), isJumping);
			if(isJumping)
			{
				if(super.getVy()==0)
				{
					isJumping = false;
				}
			}
		}
	}

	public void collision() {
		// implement later
	}
}
