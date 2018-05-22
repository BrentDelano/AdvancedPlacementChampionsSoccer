
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the screen where the player selects his/her character
 * @author Tony Yu, Mira Khosla
 * @version 5/21/18
 */
public class PlayerSelect extends Screen{
	private PImage[] pictures;
	private final int startX;
	private final int startY;
	private final double imgWidth;
	private final double imgHeight;
	private int currentPlayer;
	private PImage background;

	public PlayerSelect() {
		
		startX = 0;
		startY = 260;
		imgWidth = 128;
		imgHeight = 180;
		currentPlayer = 1;
		
		pictures = new PImage[30];
		
		
	}

	
	public void draw(PApplet drawer) {
		background = drawer.loadImage("SELECTION.png");
		drawer.image(background, 0, 0, drawer.width, drawer.height);

		
	}
}
