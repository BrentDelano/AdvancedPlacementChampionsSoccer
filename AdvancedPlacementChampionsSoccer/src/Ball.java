import processing.core.PApplet;
import processing.core.PImage;

public class Ball extends MovingObject {
	
	private PImage ball;
	
	public Ball() {
		super(10, 10, 30, 30);
		ball = new PImage();
	}
	
	public Ball(int x, int y, int d) {
		super(x, y, d, d);
		ball = new PImage();
	}
	
	public void draw(PApplet drawer) {
		ball = drawer.loadImage("ball.png");
		drawer.image(ball, getX(), getY(), getWidth(), getHeight());
	}

	public void collision() {
		// implement later
	}
}