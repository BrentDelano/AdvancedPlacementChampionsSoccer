import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the in-game screen
 * @author Brent Delano
 * @version 5/10/18
 *
 */
public class GamePanel extends PApplet {
	
	private Ball ball;
	private Tekkist tekkist;
	private Surface[] boundaries;
	private PImage background;
	
	public GamePanel() {
		ball = new Ball();
		tekkist = new Tekkist();
		boundaries = new Surface[6];
		background = new PImage();
	}
	
	public void createBoundaries() {
		boundaries[0] = new Surface(width/2, height/2, width, height/2);
		
	}
	
	public void setup() {		
		background = loadImage("field.jpeg");
		createBoundaries();
		ball.setup(this);
		tekkist.setup(this);	
	}
	
	public void settings() {
		fullScreen(P2D);
	}
	
	public void draw() {			
		clear();
	//	rotateX(PI/4);	
	//	translate(width / 2, height / 2, 0);
		
//		imageMode(CENTER);
//		image(background, 0, 0, width, height);
		
		
		
		pushMatrix();
		ball.draw(this);
		//boundaries[0].draw(this);
		tekkist.draw(this);
		popMatrix();
	}
	
	public void keyPressed() {
		if (keyPressed) {
			if (key == 'a') {
				tekkist.walkHorizontally(-1);
			}
			if (key == 'd') {
				tekkist.walkHorizontally(1);
			}
			if (key == 'w') {
				tekkist.walkVertically(-1);
			}
			if (key == 's' ) {
				tekkist.walkVertically(+1);
			}
			if (key == ' ') {
				tekkist.jump();
			}
		}	
	}
}
