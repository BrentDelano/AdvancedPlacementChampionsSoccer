import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the screen where the player selects his/her character
 * @author Tony Yu
 * @version 5/15/18
 */
public class PlayerSelect extends DrawingSurface {

	private PImage[] pictures;
	private final int startX;
	private final int startY;
	private final double imgWidth;
	private final double imgHeight;
	private int currentPlayer;
	private PImage background;

	public PlayerSelect() {
		startX = 0;
		startY = 460;
		imgWidth = 235.7;
		imgHeight = 313.3333;
		currentPlayer = 1;
		
		pictures = new PImage[30];
		for (int i = 0; i < 30; i++) {
			pictures[i] = new PImage();
		}
		
		background = new PImage();
	}

	public void setup() {
//		for (int i = 1; i <= 30; i++) {
//			pictures[i] = loadImage("people" + System.getProperty("file.separator") + "tek" + i + ".jpeg");
//		}
		background = loadImage("SELECTION.png");
	}

	public void settings() {
		size(1280, 800, P2D);
	}

	public void draw() {			
		clear();
		image(background, 0, 0, width, height);
		fill(225);
		rect(0f, 260f, 128f, 180f);
	}
	
//	public int get(int x, int y) {
//
//	}

	public void mouseClicked() {
		if (mouseButton == LEFT) {
			
			PApplet.main("GamePanel");
		}
	}
}
