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
	private Goal leftGoal;
	private Goal rightGoal;
	private int p1Score;
	private int p2Score;
	private PImage pauseButton;

	public GamePanel() {
		ball = new Ball(700, 0, 30);
		p1 = new Tekkist(225, 520, 100, 135);
		p2 = new Tekkist(1000, 520, 100, 135);
		boundaries = new Surface[6];
		background = new PImage();
		leftGoal = new Goal((float)(width/25.6), (float)((height*3.0)/16.0), true,100, 400); 
		//System.out.println(width);
		//System.out.println(height);
		//System.out.println(leftGoal.getX());
		//System.out.println(leftGoal.getY());
		rightGoal = new Goal(1120, 150, false,100, 400);
		pauseButton = new PImage();
		p1Score = 0;
		p2Score =0;
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
		leftGoal.setup(this);
		rightGoal.setup(this);
		pauseButton = loadImage("pauseButton.png");
		//leftGoal = new Goal((float)(width/25.6), (float)((height*3.0)/16.0), true,100, 400);
	}

	public void settings() {
		fullScreen(P2D);
	}

	public void draw() {			
		clear();

		// draws everything onto the screen
		leftGoal.setX((float)(width/25.6));
		leftGoal.setY((float)((height*3.0)/16.0));
		rightGoal.setX((float)((width*7.0)/8.0));
		rightGoal.setY((float)((height*3.0)/16.0));
		image(background, 0, 0, width, height);
		image(pauseButton, width-60, 10, 50, 50);
		p1.draw(this);
		p2.draw(this);	
		ball.draw(this);
		rightGoal.draw(this);
		leftGoal.draw(this);

		textSize(40);
		text("SCORE: " + p1Score + " - " + p2Score, (float)(width/2.56),(float)((height*15.0)/16.0));

		// creates physics between physics objects

		p1.act();
		if (!p1.isOnSurface()) 
			p1.fall(boundaries[0]);

		p2.act();
		if (!p2.isOnSurface()) 
			p2.fall(boundaries[0]);

		ball.act();
		if (!ball.isOnSurface()) 
			ball.fall(boundaries[0]);
		if (ball.getVX() != 0)
			ball.applyFriction();

		//  collision detecting

		
		
		if (Math.abs(p1.getX() - p2.getX()) < 100 && Math.abs(p1.getY() - p2.getY()) < 135)
			playerCollisionDetection();
		else {
			p1.setRightMovability(true);
			p2.setLeftMovability(true);
		}
		
		if (Math.abs(p1.getX() - ball.getX()) < 150)
			ballCollisionDetection(p1);
		if (Math.abs(p2.getX() - ball.getX()) < 150)
			ballCollisionDetection(p2);
		
		goalInteraction();
	//	System.out.println(width + "width");
	//	System.out.println(height + "height");
	}
	
	

	public void keyPressed() {		
		if (keyPressed) {

			// player 1

			if (key == 'a')
				if (p1.canMoveLeft())
					p1.walkHorizontally(-1);
			if (key == 'd')
				if (p1.canMoveRight())
					p1.walkHorizontally(1);
			if (key == 'w') 
				p1.jump();

			// player 2

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
	
	public void mousePressed()
	{
		if(mouseX>=width-60 && mouseY<=60 && mouseY>10 && mouseX<width-10)
		{
			//TONY ADD PAUSE SCREEN GRAPHIC
			
		}
	}

	public void keyReleased() {
		// player 1	

		if (key == 'a') 
			p1.walkHorizontally(0);
		if (key == 'd')
			p1.walkHorizontally(0);

		// player 2	

		if (keyCode == LEFT)
			p2.walkHorizontally(0);
		if (keyCode == RIGHT)
			p2.walkHorizontally(0);
	}

	/**
	 * @pre only detects collision if p1 is to the left of p2; also it does not detect player collision on the y axis
	 */
	public void playerCollisionDetection() {
		if (p1.getX() + p1.getWidth() >= p2.getX()) {
			p1.setVX(0);
			p1.setRightMovability(false);
			p2.setVX(0);
			p2.setLeftMovability(false);
		}
	}
	
	public void ballCollisionDetection(Tekkist p) {
		if (p.getY() <= ball.getY() - ball.getHeight() && p.getY() + p.getHeight() >= ball.getY()) {
//<<<<<<< HEAD
		//	System.out.println(p.getX() + p.getWidth() - ball.getX());
//=======
			System.out.println(p1.getX());
//>>>>>>> branch 'master' of https://github.com/bdelano255/AdvancedPlacementChampionsSoccer.git
			if (p.getX() + p.getWidth()/2.0 - ball.getX() <= 0 && p.getX() + p.getWidth()/2.0 - ball.getX() >= -(p.getWidth() / 2.0) ||
					p.getX() - ball.getX() - ball.getWidth() <= 0 && p.getX() - ball.getX() - ball.getWidth() >= -(p.getWidth() / 2.0)) {
				ball.setVX(1.5 * p.getVX());
			}
		}
	}
	
	public void goalInteraction()
	{
		if(ball.getX()<=150 && ball.getY() >= 150 && ball.getY()<=150+400)
		{
			p2Score++;
			ball = new Ball(700, 0, 30);
			ball.setup(this);
		}
		if(ball.getX()>=1120 && ball.getY() >= 150 && ball.getY()<=150+400)
		{
			p1Score++;
			ball = new Ball(700, 0, 30);
			ball.setup(this);
		}
		//need to add bounce off crossbar
		
	}
}
