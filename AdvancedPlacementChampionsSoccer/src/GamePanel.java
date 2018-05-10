import processing.core.PApplet;
import processing.core.PImage;

public class GamePanel extends PApplet {
	
	private Ball ball;
	private Tekkist tekkist;
	private Surface surface;
	private PImage background;
	
	public GamePanel() {
		ball = new Ball();
		tekkist = new Tekkist();
		surface = new Surface();
		background = new PImage();
	}
	
	public GamePanel(Ball b, Tekkist t, Surface s) {
		ball = b;
		tekkist = t;
		surface = s;
		background = new PImage();
	}
	
	public void setup() {
		background = loadImage("field.png");
		
		tekkist.setup(this);
		ball.setup(this);
		
	}
	
	public void draw() {		
		clear();
		background(255);
		float ratioX = (float) width / 500; 		
		float ratioY = (float) height / 500; 
		scale(ratioX, ratioY);
		
	
		image(background, 0, 0, 500, 500);
		
		ball.draw(this);
		if (!ball.isOnSurface()) {
			ball.fall(surface);
		}
		
		tekkist.draw(this);
		if (!tekkist.isOnSurface()) {
			tekkist.fall(surface);
		}
	}
	
	public void keyPressed() {
		if (keyPressed) {
			if (key == 'a') {
				tekkist.walk(-5);
			}
			if (key == 'd') {
				tekkist.walk(5);
			}
			if (key == 'w') {
				tekkist.jump();
			}
		}	
	}
}
