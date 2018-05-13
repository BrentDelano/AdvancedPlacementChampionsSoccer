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
	private Tekkist p1;
	private Tekkist p2;
	private Surface[] boundaries;
	private PImage background;

	public GamePanel() {
		ball = new Ball();
		p1 = new Tekkist();
		p2 = new Tekkist();
		boundaries = new Surface[6];
		background = new PImage();
	}

	public static void main(String[] args) {
		PApplet.main("GamePanel");
	}

	public void createBoundaries() {
		//		boundaries[0] = new Surface(0, 0, (int) (3.0 * width / 4.0), (int) (3.0 * height / 5.0));
		boundaries[0] = new Surface(0, (int) (height / 2.0) + 70,  width, (int) (height / 2.0));

		//		********************************************************
		//		********************************************************
		//		********** TRY TO ADD THE WALLS AND CEILING!! **********
		//		********************************************************
		//		********************************************************
	}

	public void setup() {		
		background = loadImage("field.jpeg");
		createBoundaries();
		ball.setup(this);
		p1.setup(this, "batman.gif");	
		p2.setup(this, "street fighter.gif");
	}

	public void settings() {
		fullScreen(P2D);
	}

	public void draw() {			
		clear();

		image(background, 0, 0, width, height);

		p1.draw(this);
		p1.actHorizontally();
		if (!p1.isOnSurface()) 
			p1.fall(boundaries[0]);
		
		p2.draw(this);
		p2.actHorizontally();
		if (!p2.isOnSurface()) 
			p2.fall(boundaries[0]);

		ball.draw(this);
		ball.actHorizontally();
		if (!ball.isOnSurface()) 
			ball.fall(boundaries[0]);
	}

	public void keyPressed() {
		if (keyPressed) {
			if (key == 'a')
				p1.walkHorizontally(-1);
			if (key == 'd')
				p1.walkHorizontally(1);
			if (key == 'w') 
				p1.jump();
			if (key == 'k')
				p2.walkHorizontally(-1);
			if (key == ';')
				p2.walkHorizontally(1);
			if (key == 'o') 
				p2.jump();
		}	
	}

	public void keyReleased() {
		if (key == 'a') 
			p1.walkHorizontally(0);
		if (key == 'd')
			p1.walkHorizontally(0);
		if (key == 'k') 
			p2.walkHorizontally(0);
		if (key == ';')
			p2.walkHorizontally(0);
	}
}
