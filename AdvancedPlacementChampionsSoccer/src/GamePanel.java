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
		p1 = new Tekkist(225, (float) (height / 2.0), 100, 135);
		p2 = new Tekkist(1075, (float) (height / 2.0), 100, 135);
		boundaries = new Surface[6];
		background = new PImage();
	}

	public static void main(String[] args) {
		PApplet.main("GamePanel");
	}

	public void createBoundaries() {
		//		boundaries[0] = new Surface(0, 0, (int) (3.0 * width / 4.0), (int) (3.0 * height / 5.0));
		boundaries[0] = new Surface(0, (int) (height / 2.0) + 70,  width, (int) (height / 2.0));
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

		if (Math.abs(p1.getX() - p2.getX()) < 100 && Math.abs(p1.getY() - p2.getY()) < 135)
			playerCollisionDetection();
	}

	public void keyPressed() {
		if (keyPressed) {
			if (key == 'a')
				if (p1.canMoveLeft())
					p1.walkHorizontally(-1);
			if (key == 'd')
				if (p1.canMoveRight())
					p1.walkHorizontally(1);
			if (key == 'w') 
				p1.jump();

			if (keyCode == LEFT)
				if (p2.canMoveLeft())
					p2.walkHorizontally(-1);
			if (keyCode == RIGHT)
				if (p2.canMoveRight())
					p2.walkHorizontally(1);
			if (keyCode == UP) 
				p2.jump();
		}	
	}

	public void keyReleased() {
		if (key == 'a') 
			if (p1.canMoveLeft())
				p1.walkHorizontally(0);
		if (key == 'd')
			if (p1.canMoveRight())
				p1.walkHorizontally(0);

		if (keyCode == LEFT) 
			if (p2.canMoveLeft())
				p2.walkHorizontally(0);
		if (keyCode == RIGHT)
			if (p2.canMoveRight())
				p2.walkHorizontally(0);
	}

	public void playerCollisionDetection() {
		if (p1.getX() + p1.getWidth() >= p2.getX()) {
			p1.setVX(0);
			p1.setRightMovability(false);
			p2.setVX(0);
			p2.setLeftMovability(false);
		}
		if (p2.getX() + p2.getWidth() >= p1.getX()) {
			p2.setVX(0);
			p2.setRightMovability(false);
			p1.setVX(0);
			p1.setLeftMovability(false);
		}
		if (p1.getY() + p1.getHeight() >= p2.getY()) {
			p1.setVY(0);
			p1.setDownMovability(false);
		}
		if (p2.getY() + p2.getHeight() >= p1.getY()) {
			p2.setVY(0);
			p2.setDownMovability(false);
		}
	}
}
