import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the screen where the player selects his/her character
 * @author Tony Yu
 * @version 5/15/18
 */
public class PlayerSelect extends Screen{

	private ArrayList<PImage> pictures;
	private double imgWidth = 235.7;
	private double imgHeight = 313.3333;
	private PImage background;

	public PlayerSelect()
	{
		//		for (int i = 1; i<=30; i++) {
		//			pictures.add(loadImage(i + ".png"));
		//		}
		background = new PImage();
	}

	public PImage getTekkistPicture(double mouseX, double mouseY)
	{
		int a = (int)(mouseX/imgWidth);
		int b = (int)((mouseY-460)/imgHeight);
		return pictures.get(b*10 + a);
	}

	public void draw(PApplet drawer) {
		background = drawer.loadImage("SELECTION.png");
		drawer.image(background, 0, 0, drawer.width, drawer.height);

	}
	
}
