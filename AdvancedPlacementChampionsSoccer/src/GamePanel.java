import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
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
	private int startTime;								// DO THIS LATER!!!!
	private int delay;
	private boolean paused;
	private int pauseDelay;
	private MysteryBox mysteryBox;
	private PowerUp boxPowerP1;
	private PowerUp boxPowerP2;
//<<<<<<< HEAD
	private Minim m;
	private AudioPlayer a;
//=======
	private boolean goalChange;
	private int isPowered;
//>>>>>>> branch 'master' of https://github.com/bdelano255/AdvancedPlacementChampionsSoccer

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
		startTime = 5;
		delay = 0;
		paused = false;
		pauseDelay = 0;
		mysteryBox = new MysteryBox(608, -75);
		boxPowerP1 = null;
		boxPowerP2 = null;
//<<<<<<< HEAD
		m = new Minim(this);
		a = m.loadFile("");			// if it gives errors move to setup, use a.play() whenever you want to play it, a.pause() and a.rewind()
//=======
		goalChange = false;
		isPowered = 0;
//>>>>>>> branch 'master' of https://github.com/bdelano255/AdvancedPlacementChampionsSoccer
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
			int displayTime = 60  - (time/1000) + (delay/1000) + (pauseDelay/1000) + startTime;

			if (displayTime >= 0) {
				clear();		

				// some objects being drawn here
				if(displayTime == 30 && mysteryBox.getX()<0)
				{
					mysteryBox.setX(608);
					mysteryBox.setY(-75);
					mysteryBox.setState(false);
				}
				if(goalChange && time-isPowered >=10*1000) {
					resetGoalSizes();
					goalChange = !goalChange;
				}
				if(!goalChange) {
				
				leftGoal.setX((float)(width/25.6));
				leftGoal.setY((float)((height*3.0)/16.0));
				rightGoal.setX((float)((width*7.0)/8.0));
				rightGoal.setY((float)((height*3.0)/16.0));
				}
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

				// creates physics

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
						boxPowerP1.setup(this);
					}
				}

				if (Math.abs(p2.getX() - mysteryBox.getX()) <= 100 && Math.abs(p1.getY()-mysteryBox.getY())<100) {
					if (mysteryBoxCollisionDetection(p2)) {
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
					boxPowerP1.draw(this, 20, 90);
				if(boxPowerP2 != null)
					boxPowerP2.draw(this, 1100, 90);	

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
				if (p1.canMoveUp())
					p1.jump();
			if (key == 's') {
				if (p1.canKick()) {
					if (ballInteraction(p1))
						p1.kickBall(ball, true);
					if (playerCollisionDetection())
						p1.kickPlayer(p2, true);
				}
			}
			if (key == ' ') {
				if (boxPowerP1 != null) {
					useBoxPower(boxPowerP1, 1);
					goalChange = true;
					isPowered = time;
					boxPowerP1 = null;
					
				}
				else
					p1.makeSuper();
			}

			// player 2

			if (keyCode == LEFT)
				if (p2.canMoveLeft())
					p2.walkHorizontally(-1);
			if (keyCode == RIGHT)
				if (p2.canMoveRight())
					p2.walkHorizontally(1);
			if (keyCode == UP)
				if (p2.canMoveUp())
					p2.jump();
			if (keyCode == DOWN) {
				if (p2.canKick()) {
					if (ballInteraction(p2))
						p2.kickBall(ball, false);
					if (playerCollisionDetection())
						p2.kickPlayer(p1, false);
				}
			}
			if (key == ENTER) {
				if (boxPowerP2 != null)
				{
					useBoxPower(boxPowerP2, 2);
					goalChange = true;
					isPowered = time;
					boxPowerP2 = null;
					
				}
					
				else
					p2.makeSuper();
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
			if(ball.getX()+ball.getWidth()<=p.getX()+p.getWidth() && ball.getX()>=p.getX()) {
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
					p.freeze();
					ball.setVY(0);
					ball.setVX(0);
				}

				return true;
			}
		}
		return false;
	}

	public void goalInteraction() {
//<<<<<<< HEAD
		//if (ball.getX() <= leftGoal.getX() + 35 && ball.getY() >= leftGoal.getY() && ball.getY()<=leftGoal.getY() + 400) {
//=======
		if (ball.getX()<=leftGoal.getWidth()+leftGoal.getX() && ball.getY() >= leftGoal.getY() && ball.getY()<=leftGoal.getY()+leftGoal.getHeight()) {
//>>>>>>> branch 'master' of https://github.com/bdelano255/AdvancedPlacementChampionsSoccer
			p2Score++;

			ball.setX(width/2-15);
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
//<<<<<<< HEAD
	//	if (ball.getX() >= rightGoal.getX() + 35 && ball.getY() >= rightGoal.getY() && ball.getY()<=rightGoal.getY()+400) {
//=======
		if (ball.getX()>=rightGoal.getX() && ball.getY() >= rightGoal.getY() && ball.getY()<=rightGoal.getY()+rightGoal.getHeight()) {
//>>>>>>> branch 'master' of https://github.com/bdelano255/AdvancedPlacementChampionsSoccer
			p1Score++;

			ball.setX(width/2-15);
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
	}
	
	public void resetGoalSizes()
	{
		leftGoal.setHeight(400);
		leftGoal.setWidth(100);
		rightGoal.setWidth(100);
		rightGoal.setHeight(400);
	}
}
