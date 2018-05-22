import processing.core.PImage;
import processing.core.PApplet;

/**
 * Represents the screen where there is instructions for the game
 * @author Tony Yu
 * @version 5/10/18
 *
 */
public class InsPanel extends Screen {

	private PImage background;

	public InsPanel() {
		background = new PImage();
	}
	
	public void draw(PApplet drawer) {	
		background = drawer.loadImage("INSTRUCTIONS.png");
		drawer.image(background, 0, 0, drawer.width, drawer.height);
	}
}
