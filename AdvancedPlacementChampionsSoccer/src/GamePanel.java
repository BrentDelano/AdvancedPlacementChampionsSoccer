import processing.core.PImage;
//import processing.sound.*;

/**
 * Represents the in-game screen
 * @author Brent Delano & Mira Khosla
 * @version 5/15/18
 *
 */
public class GamePanel extends DrawingSurface {

	private Ball ball;
	private Tekkist p1;
	private Tekkist p2;
	private Surface ground;
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
	private MysteryBox mysteryBox;
	private PowerUp boxPowerP1;
	private PowerUp boxPowerP2;

	public GamePanel() {
		ball = new Ball(625, 0, 30);
		p1 = new Tekkist(225, 520, 100, 135, new PowerUpBar(20, 20), new Health(20, 60));
		p2 = new Tekkist(1000, 520, 100, 135, new PowerUpBar(960, 20), new Health(960, 60));
		ground = new Surface(0, 470, 1280, 400);
		background = new PImage();
		leftGoal = new Goal((float)(width/25.6), (float)((height*3.0)/16.0), true, 100, 400); 
		rightGoal = new Goal(1120, 150, false, 100, 400);
		p1Score = 0;
		time = 0;
		delay = 0;
		paused = false;
		pauseDelay = 0;
		mysteryBox = new MysteryBox(608, -75);
		boxPowerP1 = null;
		boxPowerP2 = null;
	}

	public void setup() {	
		frame.setResizable(false);
		background = loadImage("field.jpeg");
		ball.setup(this);
		p1.setup(this, "batman.gif");	
		p2.setup(this, "street fighter.gif");
		leftGoal.setup(this);
		rightGoal.setup(this);
		pauseButton = loadImage("pauseButton.png");
		p2Score = 0;
		mysteryBox.setup(this);
	}

	public void settings() {
		size(1280, 800, P2D);
	}

	public void draw() {			
		if (delay == 0) 
			delay = millis();
		if (!paused) {
			time = millis();
			int displayTime = 60  - (time/1000) + (delay/1000) + (pauseDelay/1000);

			if (displayTime >= 0) {
				clear();		

				// some objects being drawn here

				leftGoal.setX((float)(width/25.6));
				leftGoal.setY((float)((height*3.0)/16.0));
				rightGoal.setX((float)((width*7.0)/8.0));
				rightGoal.setY((float)((height*3.0)/16.0));
				image(background, 0, 0, width, height);
				image(pauseButton, width - 60, height - 60, 50, 50);

				p1.draw(this);
				p2.draw(this);	
				ball.draw(this);
				rightGoal.draw(this);
				leftGoal.draw(this);
				mysteryBox.draw(this);

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
					p1.fall(ground);

				p2.act();
				if (!p2.isOnSurface()) 
					p2.fall(ground);

				if (displayTime <= 55) {
					mysteryBox.act();
					if (!mysteryBox.isOnSurface()) 
						mysteryBox.fall(ground);
				}

				ball.act();
				if (!ball.isOnSurface()) 
					ball.fall(ground);
				if (ball.getVX() != 0)
					ball.applyFriction();

				//  collision detecting

				if (Math.abs(p1.getX() - p2.getX()) < 100 && Math.abs(p1.getY() - p2.getY()) < 135)
					playerCollisionDetection();
				else {
					p1.setRightMobility(true);
					p2.setLeftMobility(true);
				}

				if (Math.abs(p1.getX() - ball.getX()) < 150)
					ballInteraction(p1);
				if (Math.abs(p2.getX() - ball.getX()) < 150)
					ballInteraction(p2);

				if (Math.abs(p1.getY()+p1.getHeight()-ball.getY()) <5 && ball.getX()  + ball.getWidth() >p1.getX() && ball.getX()  <p1.getX()+p1.getWidth()) {
					if(ball.getX() >= p1.getX() +p1.getHeight()/2) {
						ball.setX(p1.getX()+ p1.getWidth());
					}
					else {
						ball.setX(p1.getX());
					}

					ball.setVX(1.5* p1.getVX());
				}
				if (Math.abs(p2.getY()+p2.getHeight()-ball.getY()) <5 && ball.getX()  + ball.getWidth() >p2.getX() && ball.getX()  <p2.getX()+p2.getWidth()) {
					if(ball.getX() >= p2.getX() +p2.getHeight()/2) {
						ball.setX(p2.getX()+ p2.getWidth());
					}
					else {
						ball.setX(p2.getX());
					}

					ball.setVX(1.5* p2.getVX());
				}

				goalInteraction();
				
				if (Math.abs(p1.getX() - mysteryBox.getX()) <= 100 && Math.abs(p1.getY()-mysteryBox.getY())<100) {
					if(mysteryBoxCollisionDetection(p1));
					{
						p1.collectBox();
						boxPowerP1 = p1.getBoxPower();
						boxPowerP1.setup(this);
					}
					
				}
					
				if (Math.abs(p2.getX() - mysteryBox.getX()) <= 100&& Math.abs(p1.getY()-mysteryBox.getY())<100) {
					if(mysteryBoxCollisionDetection(p2));
					{
						p2.collectBox();
						boxPowerP2 = p2.getBoxPower();
						boxPowerP2.setup(this);
					}
				}

				// power up, mystery boxes, & health bars

				p1.getPowerUpBar().draw(this);
				p2.getPowerUpBar().draw(this);
				p1.getHealth().draw(this);
				p2.getHealth().draw(this);	
				
				if(boxPowerP1 != null)
				{
					boxPowerP1.draw(this, 20, 90);
				}
				if(boxPowerP2 != null)
				{
					boxPowerP2.draw(this, 1100, 90);
				}
				
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
			if (key == 'w')
				p1.jump();
			if (key == 's')
				if(ballInteraction(p1))
					p1.kick(ball, ground, true);
			if (key == ' ')
			{
				if(boxPowerP1 != null)
				{
					boxPowerP2 = null;
				}
				else {
					p1.makeSuper();
				}
				
			}
				

			// player 2

			if (keyCode == LEFT)
				if (p2.canMoveLeft())
					p2.walkHorizontally(-1);
			if (keyCode == RIGHT)
				if (p2.canMoveRight())
					p2.walkHorizontally(1);
			if (keyCode == UP)
				p2.jump();
			if (keyCode == DOWN) 
				if(ballInteraction(p2))
					p2.kick(ball, ground, false);
			if (key == ENTER) {
				if(boxPowerP2 != null)
				{
					boxPowerP2 = null;
				}
				else {
					p2.makeSuper();
				}
			}
		}	
	}

	public void mousePressed() {
		if (mouseX >= width - 60 && mouseY >= height - 60 && mouseY < height - 10 && mouseX < width - 10)
			paused = !paused;
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
			p1.setRightMobility(false);
			p2.setVX(0);
			p2.setLeftMobility(false);
		}
	}

	public boolean mysteryBoxCollisionDetection(Tekkist p) {
		if (p.getY() <= mysteryBox.getY() + mysteryBox.getHeight() && p.getY() + p.getHeight() >= mysteryBox.getY()) {
			if (p.getX() + p.getWidth() >= mysteryBox.getX() && p.getX() + p.getWidth()/2.0 < mysteryBox.getX() ||
					p.getX() <= mysteryBox.getX() + mysteryBox.getWidth() && p.getX() >= mysteryBox.getX() + mysteryBox.getWidth()/2.0) {
				
				mysteryBox.setX(-100);
				mysteryBox.setY(-100);
				return true;
			}
		}
		return false;
	}

	public boolean ballInteraction(Tekkist p) {
		if (p.getY() <= ball.getY() - ball.getHeight() && p.getY() + p.getHeight() >= ball.getY()) {
			if (p.getX() + p.getWidth() >= ball.getX() && p.getX() + p.getWidth()/2.0 < ball.getX() ||
					p.getX() <= ball.getX() + ball.getWidth() && p.getX() >= ball.getX() + ball.getWidth()/2.0) {

				if (!p.getSuperStatus())
					ball.setVX(1.5 * p.getVX());
				else {
					ball.setVY(0);
					ball.setVX(0);
				}

				return true;
			}
		}
		return false;
	}

	public void goalInteraction() {
		if (ball.getX()<=100+leftGoal.getX() && ball.getY() >= leftGoal.getY() && ball.getY()<=leftGoal.getY()+400) {
			p2Score++;
			ball = new Ball(width/2 - 15, 0, 30);
			ball.setup(this);
		}
		if (ball.getX()>=rightGoal.getX() && ball.getY() >= rightGoal.getY() && ball.getY()<=rightGoal.getY()+400) {
			p1Score++;
			ball = new Ball(width/2 - 15, 0, 30);
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
