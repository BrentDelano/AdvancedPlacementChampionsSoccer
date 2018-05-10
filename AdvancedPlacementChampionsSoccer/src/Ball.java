import processing.core.PApplet;
import processing.core.PImage;

public class Ball extends MovingObject {
	
	private PImage ball;
	
	public Ball() {
		super(10, 10, 40, 50);
		ball = new PImage();
	}
	
	public Ball(int x, int y, int d) {
		super(x, y, d, d);
		ball = new PImage();
	}
	
	public void setup(PApplet drawer)
	{
		ball = drawer.loadImage("ball.png");
	}
	
	public void draw(PApplet drawer) {
		
		drawer.image(ball, getX(), getY(), getWidth(), getHeight());
	}

	public void collision() {
		// implement later
	}
}