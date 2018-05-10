import java.util.ArrayList;
import processing.core.PImage;

/**
 * Represents the screen where the player selects his/her character
 * @author Tony Yu
 * @version 5/10/18
 */
public class PlayerSelect {

	private ArrayList<PImage> pictures;
	private int imgWidth;
	private int imgHeight;

	public PlayerSelect(ArrayList<PImage> pictures, int imgWidth, int imgHeight)
	{
		this.pictures = pictures;
		this.imgHeight = imgHeight;
		this.imgWidth= imgWidth;
	}
	//dont necessarily multiply by 5 in return statement, multiply by # of pics per row
	public PImage getTekkistPicture(double mouseX, double mouseY)
	{
		int a = (int) (mouseY/imgHeight);
		int b = (int) (mouseX/imgWidth);
		return pictures.get(b*5 + a);
	}
}
