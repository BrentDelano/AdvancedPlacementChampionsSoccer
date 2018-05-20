import processing.core.PApplet;
import processing.core.PImage;
//import processing.sound.*;

/**
 * Represents the in-game screen
 * @author Brent Delano & Mira Khosla
 * @version 5/15/18
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
	private int time;
	private int delay;
	private boolean paused;
	private int pauseDelay;
	private PowerUp p1Power;
	private PowerUp p2Power;

	public GamePanel() {
		ball = new Ball(700, 0, 30);
		p1 = new Tekkist(225, 520, 100, 135);
		p2 = new Tekkist(1000, 520, 100, 135);
		boundaries = new Surface[6];
		background = new PImage();
		leftGoal = new Goal((float)(width/25.6), (float)((height*3.0)/16.0), true,100, 400); 
		rightGoal = new Goal(1120, 150, false,100, 400);
		pauseButton = new PImage();
		p1Score = 0;
		time = 0;
		delay = 0;
		paused = false;
		pauseDelay = 0;
		p1Power = new PowerUp(1, 110, (float)((height*15.0)/16.0));
		p2Power = new PowerUp(1, width-110, (float)((height*15.0)/16.0));
	}

	public void createBoundaries() {
		//		boundaries[0] = new Surface(0, 0, (int) (3.0 * width / 4.0), (int) (3.0 * height / 5.0));
		boundaries[0] = new Surface(0, (int) (height / 2.0) + 70,  width, (int) (height / 2.0));

	}

	public void setup() {	
		frame.setResizable(false);
		background = loadImage("field.jpeg");
		createBoundaries();
		ball.setup(this);
		p1.setup(this, "batman.gif");	
		p2.setup(this, "street fighter.gif");
		leftGoal.setup(this);
		rightGoal.setup(this);
		pauseButton = loadImage("pauseButton.png");
		p2Score = 0;
		p1Power.setup(this);
		p2Power.setup(this);
	}

	public void settings() {
		size(1280, 800, P2D);
	}

	public void draw() {			
		if (delay == 0) 
			delay = millis();
		if (!paused) {
			time = millis();
			int displayTime = 60  - (time/1000)  + (delay/1000) + (pauseDelay/1000);

				if (displayTime >= 0) {

					clear();		

					// draws everything onto the screen
					
					leftGoal.setX((float)(width/25.6));
					leftGoal.setY((float)((height*3.0)/16.0));
					rightGoal.setX((float)((width*7.0)/8.0));
					rightGoal.setY((float)((height*3.0)/16.0));
					p1Power.setY((float)((height*15.0)/16.0)-60);
					p2Power.setY((float)((height*15.0)/16.0)-60);
					p2Power.setX(width-110-100);
					image(background, 0, 0, width, height);
					image(pauseButton, width - 60, 10, 50, 50);

					p1.draw(this);
					p2.draw(this);	
					ball.draw(this);
					rightGoal.draw(this);
					leftGoal.draw(this);
					p1Power.draw(this);
					p2Power.draw(this);

					// timing and scoring

					textSize(40);
					fill(0);
					if (displayTime >= 10)
						text(displayTime, 12 * width / 25, height / 10);
					else if (displayTime >= 0)
						text(displayTime, 12 * width / 25, height / 10);
					else
						text("0", width / 2, height / 10);
					fill(255);
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
			pauseDelay = millis() - time;
		}
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
			if (key == 'w') {
				p1.jump();
				//		inJump = true;
			}
			if(key == 's')
			{
				if(ballInteraction(p1))
				{
					p1.kick(ball, boundaries[0], true);
				}
			}
			// player 2

			if (keyCode == LEFT)
				if (p2.canMoveLeft())
					p2.walkHorizontally(-1);
			if (keyCode == RIGHT)
				if (p2.canMoveRight())
					p2.walkHorizontally(1);
			if (keyCode == UP) {
				p2.jump();
				//		inJump = true;
			}
			if(keyCode == DOWN) {
				if(ballInteraction(p2))
				{
					p2.kick(ball, boundaries[0], false);
				}
			}

			if (key == ' ' ) {
				System.out.print("rightX: " + p1.getX() + p1.getWidth());
				System.out.print(", Player Width: " + p1.getWidth());
				System.out.println(", BallX: " + ball.getX());
			}

			if (key == 'p')
				paused = !paused;
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
			ball.setup(this);
		}
		if (ball.getX()>=rightGoal.getX() && ball.getY() >= rightGoal.getY() && ball.getY()<=rightGoal.getY()+400)
		{
			p1Score++;
			//		cheer.play();
			ball = new Ball(700, 0, 30);
			ball.setup(this);
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
