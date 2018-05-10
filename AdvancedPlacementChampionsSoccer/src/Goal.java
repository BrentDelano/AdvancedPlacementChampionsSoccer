import processing.core.*;
public class Goal extends PhysicsObject{
	
	private int x, y, height, width;
	private boolean isLeft;
	private PImage img;
	
	public Goal(int x, int y, boolean isLeft, int height, int width)
	{
		super(x, y, height, width);
		this.isLeft = isLeft;
	}
	
	public void setHeight(int newHeight)
	{
		height = newHeight;
	}
	
	public void setWidth(int newWidth)
	{
		width = newWidth;
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
		marker.image(img, x, y);
	}
	
}
