import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the player in the game, which is a MovingObject
 * @author Brent Delano
 * @version 5/15/18
 */
public class Tekkist extends MovingObject {

	private PImage tekkist;
	private boolean turned;

	public Tekkist() {
		super(100, 10, 100, 135);
		tekkist = new PImage();
		turned = false;
	}

	public Tekkist(int x, int y, int w, int h) {
		super(x, y, w, h);
		tekkist = new PImage();
		turned = false;
	}

	public void walkHorizontally(int direction) {
		if (direction > 0) {
			while (getVX() < 4.0)
				setVX(getVX() + 0.1);
			
		} else if (direction < 0) {
			while (getVX() > -4.0)
				setVX(getVX() - 0.1);
			
		} else {
			if (getVX() > 0) {
				while(getVX() > 0) 
					setVX(getVX() - 0.1);
			}
			if (getVX() < 0) {
				while(getVX() < 0) 
					setVX(getVX() + 0.1);
			}
			setVX(0);
		}
	}

	public void dash(int direction)  {
		if (direction > 0) {
			while (getVX() < 25.0)
				setVX(getVX() + 0.1);
			
		} else if (direction < 0) {
			while (getVX() > -25.0)
				setVX(getVX() - 0.1);
			
		} else {
			if (getVX() > 0) {
				while(getVX() > 0) 
					setVX(getVX() - 0.1);
			}
			if (getVX() < 0) {
				while(getVX() < 0) 
					setVX(getVX() + 0.1);
			}
			setVX(0);
		}
	}
	
//	public void walkVertically(int direction) {
//		setVY(5 * direction);
//	}

	public void jump() {
		if (isOnSurface()) {
			setVY(-7.5);
			setState(false);
		}
	}

	public void setup(PApplet drawer, String imageFile) {
		tekkist = drawer.loadImage(imageFile);
	}

	public void draw(PApplet drawer) {
		
//		drawer.pushMatrix();
//		if(turned)
//		{
//			
//			drawer.rotate((float)(Math.PI/2));
//			
//		}
		
		drawer.image(tekkist, getX(), getY(), getWidth(), getHeight());
	//	drawer.popMatrix();
		
	}

	public void kick(Ball b, Surface s, boolean isLeft) {
		// implement later
		
		if(isLeft)
			{
			b.setVX(10);
			}
		else
		{
			b.setVX(-10);
		}
		b.setVY(-10);
		b.setState(false);
		turned = true;
	//	b.fall(s);
		
	}
}
