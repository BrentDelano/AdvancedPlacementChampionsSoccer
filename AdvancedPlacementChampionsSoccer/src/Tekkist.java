import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the player in the game, which is a MovingObject
 * @author Brent Delano
 * @version 5/10/18
 */
public class Tekkist extends MovingObject {

	private PImage tekkist;

	public Tekkist() {
		super(100, 10, 100, 135);
		tekkist = new PImage();
	}

	public Tekkist(int x, int y, int w, int h) {
		super(x, y, w, h);
		tekkist = new PImage();
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

	public void walkVertically(int direction) {
		setVY(5 * direction);
	}

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
		drawer.image(tekkist, getX(), getY(), getWidth(), getHeight());
	}

	public void kick(Ball b) {
		// implement later
	}
}
