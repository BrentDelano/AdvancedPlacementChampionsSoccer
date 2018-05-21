import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a mystery box that can give a player an advantage
 * @author Brent Delano
 * @version 5/15/18
 */
public class MysteryBox extends MovingObject {
	
	private PImage mysteryBox;
	
	public MysteryBox(float x, float y) {
		super(x, y, 75, 75);
		mysteryBox = new PImage();
	}
	
	public void setup(PApplet drawer) {
		mysteryBox = drawer.loadImage("mysteryBox.png");
	}
	
	public void draw(PApplet drawer) {
			drawer.image(mysteryBox, getX(), getY(), getWidth(), getHeight());
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
		setVY(-Math.abs(getVY()) + Math.abs(getVY() * 0.4));
		setState(false);
	}
}
