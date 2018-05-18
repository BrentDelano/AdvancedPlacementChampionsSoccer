
import processing.core.*;

/**
 * Represents the power up that a Tekkist has
 * @author Mira Khosla
 * @version 5/15/18
 *
 */
public class PowerUp {
	public static final String[] powerUps= new String[]{"shrink goal","grow", "slow motion"};
	public int whichPower;
	public float x, y;
	public PImage powerImage;
	
	public PowerUp(int whichPower, float x, float y)
	{
		this.whichPower = whichPower;
		powerImage = new PImage();
		this.x =x;
		this.y = y;
	}
	
	public void setup(PApplet drawer) {
		powerImage = drawer.loadImage("powerUpLogo.jpg");
	}
	
	public void setPower(int newPower)
	{
		whichPower = newPower;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	public void setY(float y)
	{
		this.y =y;
	}
	
	public void draw(PApplet drawer)
	{
		drawer.image(powerImage, x, y, 100, 50);
	}
}
