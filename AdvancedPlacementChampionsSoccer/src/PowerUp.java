import processing.core.*;
/**
 * Represents the power up  that a Tekkist has
 * @author Mira Khosla
 * @version 5/20/18
 */
public class PowerUp {

	public static final String fileSeparator = System.getProperty("file.separator");
	
	public static final String[] powerUps = {"growGoal", "shrinkGoal"};
	//add grow and shrink tekkist
	private String powerUp;
	private PImage powerPic;
	
	public PowerUp() {
		powerUp= powerUps[(int)(Math.random()*powerUps.length)];
		powerPic = new PImage();
	}
	
	public void setup(PApplet drawer) {
		powerPic = drawer.loadImage(powerUp + ".png");
	}
	
	public void draw(PApplet drawer, float x, float y) {
		drawer.fill(0);
		drawer.stroke(0);
		drawer.rect(x+10, y+10, 80, 80);
		drawer.image(powerPic, x, y, 100, 100);

		
	}
	
	
}
