import processing.core.PApplet;
import processing.core.PImage;

public class Tekkist extends MovingObject {
	
	private PImage tekkist;
	
	public Tekkist() {
		super(100, 10, 100, 125);
		tekkist = new PImage();
	}
	
	public Tekkist(int x, int y, int w, int h) {
		super(x, y, w, h);
		tekkist = new PImage();
	}
	
	public void walk(int direction) {
		setX(getX() + direction*2); 
	}
	
	public void jump() {
		if (isOnSurface()) {
			setVY(-10);
			setState(false);
		}
	}
	
	public void setup(PApplet drawer)
	{
		tekkist = drawer.loadImage("boi.png");
	}
	
	public void draw(PApplet drawer) {
		
		drawer.image(tekkist, getX(), getY(), getWidth(), getHeight());
	}

	public void collision() {
		// implement later
	}
}
