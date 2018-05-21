import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
//import processing.sound.*;

/**
 * Represents the in-game screen
 * @author Brent Delano, Mira Khosla, Tony Yu
 * @version 5/15/18
 *
 */
public class GamePanel {

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
	private int time;
	private int delay;
	private boolean paused;
	private int pauseDelay;
	private PowerUp p1Power;
	private PowerUp p2Power;

	private PApplet p;

	public GamePanel(PApplet p) {
		this.p = p;
		ball = new Ball(700, 0, 30);
		p1 = new Tekkist(225, 520, 100, 135);
		p2 = new Tekkist(1000, 520, 100, 135);
		boundaries = new Surface[6];
		background = new PImage();
		leftGoal = new Goal((float)(p.width/25.6), (float)((p.height*3.0)/16.0), true,100, 400); 
		rightGoal = new Goal(1120, 150, false,100, 400);
		pauseButton = new PImage();
		p1Score = 0;
		time = 0;
		delay = 0;
		paused = false;
		pauseDelay = 0;
		p1Power = new PowerUp(1, 110, (float)((p.height*15.0)/16.0));
		p2Power = new PowerUp(1, p.width-110, (float)((p.height*15.0)/16.0));
	}

	public void createBoundaries() {
		//		boundaries[0] = new Surface(0, 0, (int) (3.0 * width / 4.0), (int) (3.0 * height / 5.0));
		boundaries[0] = new Surface(0, (int) (p.height / 2.0) + 70,  p.width, (int) (p.height / 2.0));

	}

	public void setup() {	
		p.frame.setResizable(false);
		background = p.loadImage("field.jpeg");
		createBoundaries();
		ball.setup(p);
		p1.setup(p, "batman.gif");	
		p2.setup(p, "street fighter.gif");
		leftGoal.setup(p);
		rightGoal.setup(p);
		pauseButton = p.loadImage("pauseButton.png");
		p2Score = 0;
		p1Power.setup(p);
		p2Power.setup(p);
	}

	public void settings() {
		p.size(1280, 800, PConstants.P2D);
	}

	public void draw() {			
		if (delay == 0) 
			delay = p.millis();
		if (!paused) {
			time = p.millis();
			int displayTime = 60  - (time/1000)  + (delay/1000) + (pauseDelay/1000);

			if (displayTime >= 0) {

				p.clear();		

				// draws everything onto the screen

				leftGoal.setX((float)(p.width/25.6));
				leftGoal.setY((float)((p.height*3.0)/16.0));
				rightGoal.setX((float)((p.width*7.0)/8.0));
				rightGoal.setY((float)((p.height*3.0)/16.0));
				p1Power.setY((float)((p.height*15.0)/16.0)-60);
				p2Power.setY((float)((p.height*15.0)/16.0)-60);
				p2Power.setX(p.width-110-100);
				p.image(background, 0, 0, p.width, p.height);
				p.image(pauseButton, p.width - 60, 10, 50, 50);

				p1.draw(p);
				p2.draw(p);	
				ball.draw(p);
				rightGoal.draw(p);
				leftGoal.draw(p);
				p1Power.draw(p);
				p2Power.draw(p);

				// timing and scoring

				p.textSize(40);
				p.fill(0);
				if (displayTime >= 10)
					p.text(displayTime, 12 * p.width / 25, p.height / 10);
				else if (displayTime >= 0)
					p.text(displayTime, 12 * p.width / 25, p.height / 10);
				else
					p.text("0", p.width / 2, p.height / 10);
				p.fill(255);
				p.text("SCORE: " + p1Score + " - " + p2Score, (float)(p.width/2.56),(float)((p.height*15.0)/16.0));

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
					ballInteraction(p1);
				if (Math.abs(p2.getX() - ball.getX()) < 150)
					ballInteraction(p2);

				if(Math.abs(p1.getY()+p1.getHeight()-ball.getY()) <5 && ball.getX()  + ball.getWidth() >p1.getX() && ball.getX()  <p1.getX()+p1.getWidth())
				{
					if(ball.getX() >= p1.getX() +p1.getHeight()/2)
					{
						ball.setX(p1.getX()+ p1.getWidth());
					}
					else
					{
						ball.setX(p1.getX());
					}

					ball.setVX(1.5* p1.getVX());
				}
				if(Math.abs(p2.getY()+p2.getHeight()-ball.getY()) <5 && ball.getX()  + ball.getWidth() >p2.getX() && ball.getX()  <p2.getX()+p2.getWidth())
				{
					if(ball.getX() >= p2.getX() +p2.getHeight()/2)
					{
						ball.setX(p2.getX()+ p2.getWidth());
					}
					else
					{
						ball.setX(p2.getX());
					}

					ball.setVX(1.5* p2.getVX());
				}


				goalInteraction();
			}
		} else {
			pauseDelay = p.millis() - time;
		}
	}

	public void keyPressed() {		
		if (p.keyPressed) {

			// player 1

			if (p.key == 'a')
				if (p1.canMoveLeft())
					p1.walkHorizontally(-1);
			if (p.key == 'd')
				if (p1.canMoveRight())
					p1.walkHorizontally(1);
			if (p.key == 'w') {
				p1.jump();
				//		inJump = true;
			}
			if(p.key == 's')
			{
				if(ballInteraction(p1))
				{
					p1.kick(ball, boundaries[0], true);
				}
			}
			// player 2

			if (p.keyCode == PConstants.LEFT)
				if (p2.canMoveLeft())
					p2.walkHorizontally(-1);
			if (p.keyCode == PConstants.RIGHT)
				if (p2.canMoveRight())
					p2.walkHorizontally(1);
			if (p.keyCode == PConstants.UP) {
				p2.jump();
				//		inJump = true;
			}
			if(p.keyCode == PConstants.DOWN) {
				if(ballInteraction(p2))
				{
					p2.kick(ball, boundaries[0], false);
				}
			}

			if (p.key == ' ' ) {
				System.out.print("rightX: " + p1.getX() + p1.getWidth());
				System.out.print(", Player Width: " + p1.getWidth());
				System.out.println(", BallX: " + ball.getX());
			}

			if (p.key == 'p')
				paused = !paused;
		}	
	}

	public void mousePressed()
	{
		if(p.mouseX>=p.width-60 && p.mouseY<=60 && p.mouseY>10 && p.mouseX<p.width-10)
		{
			//TONY ADD PAUSE SCREEN GRAPHIC

		}

	}

	public void keyReleased() {
		// player 1	

		if (p.key == 'a') 
			p1.walkHorizontally(0);
		if (p.key == 'd')
			p1.walkHorizontally(0);

		// player 2	

		if (p.keyCode == PConstants.LEFT)
			p2.walkHorizontally(0);
		if (p.keyCode == PConstants.RIGHT)
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

	public boolean ballInteraction(Tekkist p) {

		if (p.getY() <= ball.getY() - ball.getHeight() && p.getY() + p.getHeight() >= ball.getY()) {
			if (p.getX() + p.getWidth() >= ball.getX() && p.getX() + p.getWidth()/2.0 < ball.getX() ||
					p.getX() <= ball.getX() + ball.getWidth() && p.getX() >= ball.getX() + ball.getWidth()/2.0) {
				ball.setVX(1.5 * p.getVX());
				return true;
			}
		}
		return false;
	}

	public void goalInteraction()
	{
		if (ball.getX()<=100+leftGoal.getX() && ball.getY() >= leftGoal.getY() && ball.getY()<=leftGoal.getY()+400)
		{
			p2Score++;
			//		cheer.play();
			ball = new Ball(700, 0, 30);
			ball.setup(p);
		}
		if (ball.getX()>=rightGoal.getX() && ball.getY() >= rightGoal.getY() && ball.getY()<=rightGoal.getY()+400)
		{
			p1Score++;
			//		cheer.play();
			ball = new Ball(700, 0, 30);
			ball.setup(p);
		}

		if (p1.getX() + p1.getWidth() < leftGoal.getX()+100)
		{
			p1.setX(leftGoal.getX()+100-p1.getWidth());
		}
		else if (p1.getX() >rightGoal.getX())
		{
			p1.setX(rightGoal.getX());
		}
		if (p2.getX() + p2.getWidth() < leftGoal.getX()+100)
		{
			p2.setX(leftGoal.getX()+100-p2.getWidth());
		}
		else if (p2.getX() >rightGoal.getX())
		{
			p2.setX(rightGoal.getX());
		}

		//need to add bounce off crossbar
	}
}
