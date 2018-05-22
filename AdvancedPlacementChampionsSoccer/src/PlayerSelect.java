
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the screen where the player selects his/her character
 * @author Mira Khosla
 * @version 5/15/18
 */
public class PlayerSelect extends Screen{
	private String[] pictures;
	private final int startX;
	private final int startY;
	private final double imgWidth;
	private final double imgHeight;
	private int currentPlayer;
	private PImage background;
	private boolean p1selected;
	private boolean p2selected;
	private double p1x, p1y, p2x, p2y;

	public PlayerSelect() {
		startX = 0;
		startY = 260;
		imgWidth = 128;
		imgHeight = 180;
		currentPlayer = 1;
		p1selected = false;
		p2selected = false;
		p1x = 0;
		p2x = 0;
		p1y = 0;
		p2y = 0;
		
		pictures = new String[30];
		for (int i = 0; i < 30; i++) {
			pictures[i] = "tek" + i;
		}
		
		background = new PImage();
	}

	public String getTekkistPicture(double mouseX, double mouseY, boolean p1or2)
	{
		int a = (int)(mouseX/imgWidth);
		int b = (int)((mouseY-260)/imgHeight);
		if(p1or2)
		{
			p1x = a*imgWidth;
			p1y = b*imgHeight + 260;
			p1selected = true;
		}
		else
		{
			p2x = a*imgWidth;
			p2y = b*imgHeight +260;
			p2selected = true;
		}
		
		return pictures[b*10 + a+1];	
	}
	
	public void draw(PApplet drawer) {
		background = drawer.loadImage("SELECTION.png");
		drawer.image(background, 0, 0, drawer.width, drawer.height);
		if(p1selected) {
			drawer.stroke(0);
			drawer.strokeWeight(20);
			drawer.noFill();
			drawer.rect((float)(p1x), (float)(p1y), (float)imgWidth,(float) imgHeight);
			//System.out.println(p1selected);
		}
		if(p2selected) {
			drawer.stroke(0);
			drawer.strokeWeight(20);
			drawer.noFill();
			drawer.rect((float)(p2x), (float)(p2y), (float)imgWidth,(float) imgHeight);
			//System.out.println(p2selected);
		}
		drawer.strokeWeight(1);
	}
}