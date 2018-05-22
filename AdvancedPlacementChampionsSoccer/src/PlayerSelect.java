
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the screen where the player selects his/her character
 * @author Tony Yu
 * @version 5/15/18
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
		for (int i = 0; i < 30; i++) {
			pictures[i] = new PImage();
		}
		
		background = new PImage();
	}

//	public PImage getTekkistPicture(double mouseX, double mouseY)
//	{
//		int a = (int)(mouseX/imgWidth);
//		int b = (int)((mouseY-460)/imgHeight);
//		return pictures.get(b*10 + a);	
//	}
	
	public void draw(PApplet drawer) {
		background = drawer.loadImage("SELECTION.png");
		drawer.image(background, 0, 0, drawer.width, drawer.height);

	}
}
