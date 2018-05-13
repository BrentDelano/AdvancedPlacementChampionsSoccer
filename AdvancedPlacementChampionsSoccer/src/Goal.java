import processing.core.*;

/**
 * Represents the soccer goal, which is a PhysicsObject
 * @author Mira Khosla
 * @version 5/10/18
 *
 */
public class Goal extends PhysicsObject{
	
	private boolean isLeft;
	private PImage img;
	
	public Goal(int x, int y, boolean isLeft, int height, int width)
	{
		super(x, y, height, width);
		this.isLeft = isLeft;
	}
	
	public void setup(PApplet marker)
	{
		if(isLeft)
			img = marker.loadImage("soccerGoalLeft.png");
		else
		{
			img = marker.loadImage("soccerGoal.png");
		}
	}
	
	public void draw(PApplet marker)
	{
		marker.image(img, getX(), getY());
	}
	
}
