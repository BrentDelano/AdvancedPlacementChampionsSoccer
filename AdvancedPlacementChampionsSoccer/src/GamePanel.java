import processing.core.PApplet;
import processing.core.PConstants;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PImage;
/**
 * Represents the in-game screen
 * @author Brent Delano, Mira Khosla
 * @version 5/15/18
 *
 */
public class GamePanel{

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
	private int startTime;								// DO THIS LATER!!!!
	private int delay;
	private boolean paused;
	private int pauseDelay;
	private MysteryBox mysteryBox;
	private PowerUp boxPowerP1;
	private PowerUp boxPowerP2;
	private boolean anyChange;
	private int isPowered;
	private PApplet p;
	private Minim m;
	private AudioPlayer crowd;
	private String player1Pic;
	private String player2Pic;

	public GamePanel(PApplet n, String player1Pic, String player2Pic) {
		
		this.p = n;
		this.player1Pic = player1Pic;
		this.player2Pic = player2Pic;
		ball = new Ball(625, 0, 30);
		p1 = new Tekkist(225, 520, 100, 135, new PowerUpBar(20, 20), new Health(20, 60));
		p2 = new Tekkist(1000, 520, 100, 135, new PowerUpBar(960, 20), new Health(960, 60));
		ground = new Surface(0, 470, 1280, 400);
		background = new PImage();
		leftGoal = new Goal((float)(p.width/25.6), (float)((p.height*3.0)/16.0), true, 100, 400); 
		rightGoal = new Goal(1120, 150, false, 100, 400);
		p1Score = 0;
		time = 0;
		startTime = 5;
		delay = 0;
		paused = false;
		pauseDelay = 0;
		mysteryBox = new MysteryBox(608, -75);
		boxPowerP1 = null;
		boxPowerP2 = null;
		m = new Minim(p);
		anyChange = false;
		isPowered = 0;
	}

	public void setup() {
		crowd = m.loadFile("crowd.mp3");
		crowd.play();
		p.frame.setResizable(false);
		ball.setup(p);
		p1.setup(p, "people//"+player1Pic +".jpeg");	
		p2.setup(p, "people//"+player2Pic + ".jpeg");
		leftGoal.setup(p);
		rightGoal.setup(p);
		pauseButton = p.loadImage("pauseButton.png");
		p2Score = 0;
		mysteryBox.setup(p);
		background = p.loadImage("field.jpeg");
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

				
				// some objects being drawn here
				if(displayTime == 30 && mysteryBox.getX()<0)
				{
					mysteryBox.setX(608);
					mysteryBox.setY(-75);
					mysteryBox.setState(false);
				}

				if(anyChange && time-isPowered >=10*1000) {
					resetAllSizes();
					anyChange = !anyChange;
				}
				if(!anyChange) {

					leftGoal.setX((float)(p.width/25.6));
					leftGoal.setY((float)((p.height*3.0)/16.0));
					rightGoal.setX((float)((p.width*7.0)/8.0));
					rightGoal.setY((float)((p.height*3.0)/16.0));

				}

				p.image(background, 0, 0, p.width, p.height);
				p.image(pauseButton, p.width - 60, 10, 50, 50);

				
				p1.draw(p);
				p2.draw(p);	
				ball.draw(p);
				rightGoal.draw(p);
				leftGoal.draw(p);
				mysteryBox.draw(p);

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
				if (!p1.getIsWalking())
					p1.applyFriction();
				if (!p1.isOnSurface()) 
					p1.fall(ground);

				p2.act();	
				if (!p2.getIsWalking())
					p2.applyFriction();
				if (!p2.isOnSurface()) 
					p2.fall(ground);

				if (displayTime <= 60) {
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
					if (!p1.isFrozen())
						p1.setRightMobility(true);
					if (!p2.isFrozen())
						p2.setLeftMobility(true);
				}

				if (Math.abs(p1.getX() - ball.getX()) < 150)
					ballInteraction(p1);
				if (Math.abs(p2.getX() - ball.getX()) < 150)
					ballInteraction(p2);


				goalInteraction();

				if (Math.abs(p1.getX() - mysteryBox.getX()) <= 100 && Math.abs(p1.getY()-mysteryBox.getY())<100) {
					if (mysteryBoxCollisionDetection(p1)) {
						p1.collectBox();
						boxPowerP1 = p1.getBoxPower();
						boxPowerP1.setup(p);
					}
				}

				if (Math.abs(p2.getX() - mysteryBox.getX()) <= 100 && Math.abs(p1.getY()-mysteryBox.getY())<100) {
					if (mysteryBoxCollisionDetection(p2)) {
						p2.collectBox();
						boxPowerP2 = p2.getBoxPower();
						boxPowerP2.setup(p);
					}
				}

				// power up, mystery boxes, & health bars

				p1.getPowerUpBar().draw(p);
				p2.getPowerUpBar().draw(p);
				p1.getHealth().draw(p);
				p2.getHealth().draw(p);	

				if(boxPowerP1 != null)
					boxPowerP1.draw(p, 20, 90);
				if(boxPowerP2 != null)
					boxPowerP2.draw(p, 1100, 90);	

				p1.findHeartbeat();
				p2.findHeartbeat();
				if (!p1.hasHeartbeat()) {
					if (p1.getTimeOfDeath() == 0) {
						p1.freeze();
						p1.setTimeOfDeath(displayTime);
					} else {
						if (p1.getHealth().getHealthAmount() >= 100) {
							p1.defibrillation();
							p1.unfreeze();
							p1.setTimeOfDeath(0);
							p1.getHealth().setHealthAmount(100);
						}
					}
				}
				if (!p2.hasHeartbeat()) {
					if (p2.getTimeOfDeath() == 0) {
						p2.freeze();
						p2.setTimeOfDeath(displayTime);
					} else {
						if (p2.getHealth().getHealthAmount() >= 100) {
							p2.defibrillation();
							p2.unfreeze();
							p2.setTimeOfDeath(0);
							p2.getHealth().setHealthAmount(100);
						}
					}
				}
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
				if (p1.canMoveUp())
					p1.jump();
			}if (p.key == 's') {
				if (ballInteraction(p1))
					p1.kickBall(ball, true);
				if (playerCollisionDetection())
					p1.kickPlayer(p2, true);
			}if (p.key == ' ') {
				if (boxPowerP1 != null) {
					useBoxPower(boxPowerP1, 1);
					anyChange = true;
					isPowered = time;
					boxPowerP1 = null;

				}
				else
					p1.makeSuper();
			}


			// player 2

			if (p.keyCode == PConstants.LEFT)
				if (p2.canMoveLeft())
					p2.walkHorizontally(-1);
			if (p.keyCode == PConstants.RIGHT)
				if (p2.canMoveRight())
					p2.walkHorizontally(1);
			if (p.keyCode == PConstants.UP)
				if (p2.canMoveUp())
					p2.jump();
			if (p.keyCode == PConstants.DOWN) {
				if (p2.canKick()) {
					if (ballInteraction(p2))
						p2.kickBall(ball, false);
					if (playerCollisionDetection())
						p2.kickPlayer(p1, false);
				}

			}
			if (p.key == PConstants.ENTER) {
				if (boxPowerP2 != null)
				{
					useBoxPower(boxPowerP2, 2);
					anyChange = true;
					isPowered = time;
					boxPowerP2 = null;

				}
				else
					p2.makeSuper();
			}
		}

	}
	public void mousePressed()
	{
		if(p.mouseX>=p.width-60 && p.mouseY<=60 && p.mouseY>10 && p.mouseX<p.width-10)

			paused = !paused;
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
	public boolean playerCollisionDetection() {
		if (p1.getX() + p1.getWidth() >= p2.getX()) {
			p1.setVX(0);
			p1.setRightMobility(false);
			p2.setVX(0);
			p2.setLeftMobility(false);
			return true;
		}
		return false;
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
		if (p.getY() <= ball.getY() && p.getY() + p.getHeight() >= ball.getY()+ball.getHeight()) {
			if(ball.getX()+ball.getWidth()<=p.getX()+p.getWidth() && ball.getX()+30>=p.getX()) {
				if (!p.getSuperStatus()) {



					ball.setVX(1.5*p.getVX());
					if(p.getY()-ball.getY()-ball.getHeight() <10 && ball.getY()<=p.getY() +ball.getHeight()) {

						ball.setVY(-.85 * ball.getVY());

					}
					if(p.getX()+p.getWidth()-ball.getX() <10 && ball.getX() <=p.getX()+p.getWidth()) {
						if(ball.getVX() <0)
						{
							ball.setVX(-1*ball.getVX());
						}
					}



				}
				else {
				
					int x = (int)(Math.random()*2);
					if(x==1)
					{
					ball.setY(p.getY()-10);
					ball.setVY(10);
					
					ball.setState(false);
					if(Math.abs(p.getX()-p1.getX())<0.01)
					{
						ball.setVX(20);
					}
					else
					{
						ball.setVX(-20);
					}
					}
					else {
						if(Math.abs(p.getX()-p1.getX())<0.01)
						{
							ball.setVX(40);
						}
						else
						{
							ball.setVX(-40);
						}
					}
					
					
//					if(p.getY()-ball.getY()-ball.getHeight() <10 && ball.getY()<=p.getY() +ball.getHeight()) {
//
//						ball.setVY(-.85 * ball.getVY());
//						
//					}
//					if(p.getX()+p.getWidth()-ball.getX()-30 <10 && ball.getX() <=p.getX()+p.getWidth()+30) {
//						if(ball.getVX() <0)
//						{
//							ball.setVX(-1*ball.getVX());
//						}
//					}
					
					p.makeNotSuper();
					
				}

				return true;
			}
		}
		return false;
	}

	public void goalInteraction() {
		if (ball.getX()<=leftGoal.getWidth()+leftGoal.getX() && ball.getY() >= leftGoal.getY() && ball.getY()<=leftGoal.getY()+leftGoal.getHeight()) {
			p2Score++;

			ball.setX(p.width/2-15);
			ball.setY(0);
			ball.setState(false);
			ball.setVX(0);
			PowerUpBar p1P = p1.getPowerUpBar();
			Health p1H = p1.getHealth();
			PowerUpBar p2P = p2.getPowerUpBar();
			Health p2H = p2.getHealth();
			p1.setX(225);
			p1.setHealth(p1H);
			p1.setPowerUpBar(p1P);
			p2.setX(1000);
			p2.setPowerUpBar(p2P);
			p2.setHealth(p2H);
			p1.reset();
			p2.reset();
		}

		if (ball.getX()>=rightGoal.getX() && ball.getY() >= rightGoal.getY() && ball.getY()<=rightGoal.getY()+rightGoal.getHeight()) {
			p1Score++;

			ball.setX(p.width/2-15);
			ball.setY(0);
			ball.setState(false);
			ball.setVX(0);
			//ball.setup(this);
			PowerUpBar p1P = p1.getPowerUpBar();
			Health p1H = p1.getHealth();
			PowerUpBar p2P = p2.getPowerUpBar();
			Health p2H = p2.getHealth();
			p1.setX(225);
			p1.setHealth(p1H);
			p1.setPowerUpBar(p1P);
			p2.setX(1000);
			p2.setPowerUpBar(p2P);
			p2.setHealth(p2H);
			p1.reset();
			p2.reset();
		}

		if (p1.getX() < leftGoal.getX() + leftGoal.getWidth()) {
			p1.setX(leftGoal.getX() + leftGoal.getWidth());
		} else if (p1.getX() + p1.getWidth() > rightGoal.getX()) {
			p1.setX(rightGoal.getX() - p1.getWidth());
		}
		if (p2.getX() < leftGoal.getX() + leftGoal.getWidth()) {
			p2.setX(leftGoal.getX() + leftGoal.getWidth());
		} else if (p2.getX() + p2.getWidth() > rightGoal.getX()) {
			p2.setX(rightGoal.getX() - p2.getWidth());
		}

	}

	public void useBoxPower(PowerUp boxPower, int p1orp2) {
		if(boxPower.getPower().equals("growGoal"))
		{
			if(p1orp2 == 2)
			{
				leftGoal.setHeight(leftGoal.getHeight()*2);
				leftGoal.setWidth(leftGoal.getWidth()*2);
				leftGoal.setX(leftGoal.getX()-leftGoal.getWidth()/2+80);
				leftGoal.setY(leftGoal.getY()-leftGoal.getHeight()/2+100);
			}
			else
			{
				rightGoal.setHeight(rightGoal.getHeight()*2);
				rightGoal.setWidth(rightGoal.getWidth()*2);
				rightGoal.setX(rightGoal.getX()+rightGoal.getWidth()/4-80);
				rightGoal.setY(rightGoal.getY()-rightGoal.getHeight()/2+100);
			}
		}
		else if(boxPower.getPower().equals("shrinkGoal"))
		{
			if(p1orp2 == 1)
			{
				leftGoal.setHeight(leftGoal.getHeight()/2);
				leftGoal.setWidth(leftGoal.getWidth()/2);
				leftGoal.setX(leftGoal.getX()+leftGoal.getWidth()*2-80);
				leftGoal.setY(leftGoal.getY()+150);
			}
			else
			{
				rightGoal.setHeight(rightGoal.getHeight()/2);
				rightGoal.setWidth(rightGoal.getWidth()/2);
				rightGoal.setX(rightGoal.getX()+rightGoal.getWidth()-40);
				rightGoal.setY(rightGoal.getY()+rightGoal.getHeight()/2+50);
			}
		}
		else if(boxPower.getPower().equals("growTekkist")) {
			if(p1orp2 == 1)
			{
				p1.setHeight(p1.getHeight()*2);
				p1.setY(p1.getY()-p1.getHeight()/2);
			}
			else
			{
				p2.setHeight(p2.getHeight()*2);
				p2.setY(p2.getY()-p2.getHeight()/2);
			}
		}
	}

	public void resetAllSizes()	{
		leftGoal.setHeight(400);
		leftGoal.setWidth(100);
		rightGoal.setWidth(100);
		rightGoal.setHeight(400);
		p1.setHeight(135);
		p2.setHeight(135);
		p1.setState(false);
		p2.setState(false);
	}
}
