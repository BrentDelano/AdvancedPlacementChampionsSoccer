import processing.core.PImage;
import processing.core.PApplet;

/**
 * Represents the screen where the player can begin the game
 * @author Tony Yu
 * @version 5/10/18
 *
 */
public class MenuPanel extends Screen{

	private PImage background;

	public MenuPanel() {
		background = new PImage();
	}

	public void draw(PApplet drawer) {	
		background = drawer.loadImage("MENU.png");
		drawer.image(background, 0, 0, drawer.width, drawer.height);
	}
}
