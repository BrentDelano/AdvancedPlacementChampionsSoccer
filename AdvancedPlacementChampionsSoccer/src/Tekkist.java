import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the player in the game, which is a MovingObject
 * @author Brent Delano
 * @version 5/21/18
 */
public class Tekkist extends MovingObject {

	private PImage tekkist;
	private PImage aura;
	private PImage gravestone;
	private PowerUpBar power;
	private Health health;
	private boolean superSaiyan;
	private PowerUp boxPower;
	private boolean hasBoxPower;
	private boolean isWalking;
	private boolean hasHeartbeat;
	private int timeOfDeath;
	private boolean frozen;
	private boolean canKick;

	public Tekkist(int x, int y, int w, int h, PowerUpBar p, Health health) {
		super(x, y, w, h);
		tekkist = new PImage();
		aura = new PImage();
		gravestone = new PImage();
		superSaiyan = false;
		power = p;
		this.health = health;
		boxPower = null;
		hasBoxPower = false;
		isWalking = false;
		hasHeartbeat = true;
		timeOfDeath = 0;
		frozen = false;
		canKick = true;
	}
	
	public void reset() {
		superSaiyan = false;
		boxPower = null;
		hasBoxPower = false;
		isWalking = false;
		hasHeartbeat = true;
		timeOfDeath = 0;
		frozen = false;
		canKick = true;
	}

	public void setPowerUpBar(PowerUpBar p)
	{
		power = p;
	}
	
	public void setHealth(Health h)
	{
		health = h;
	}
	
	public void walkHorizontally(int direction) {
		if (direction > 0) {
			isWalking = true;
			while (getVX() < 4.0)
				setVX(getVX() + 0.1);

		} else if (direction < 0) {
			isWalking = true;
			while (getVX() > -4.0)
				setVX(getVX() - 0.1);

		} else {
			isWalking = false;
			if (getVX() > 0) {
				while(getVX() > 0) 
					setVX(getVX() - 0.1);
			}
			if (getVX() < 0) {
				while(getVX() < 0) 
					setVX(getVX() + 0.1);
			}
			setVX(0);
		}
	}

	public void dash(int direction)  {
		if (direction > 0) {
			isWalking = true;
			while (getVX() < 25.0)
				setVX(getVX() + 0.1);

		} else if (direction < 0) {
			isWalking = true;
			while (getVX() > -25.0)
				setVX(getVX() - 0.1);

		} else {
			isWalking = false;
			if (getVX() > 0) {
				while(getVX() > 0) 
					setVX(getVX() - 0.1);
			}
			if (getVX() < 0) {
				while(getVX() < 0) 
					setVX(getVX() + 0.1);
			}
			setVX(0);
		}
	}

	public void jump() {
		if (isOnSurface() && canMoveUp()) {
			setVY(-7.5);
			setState(false);
		}
	}

	public void setup(PApplet drawer, String imageFile) {
		tekkist = drawer.loadImage(imageFile);
		aura = drawer.loadImage("aura.png");
		gravestone = drawer.loadImage("gravestone.png");
	}

	public void draw(PApplet drawer) {	
		// tekkist image
		
		if (hasHeartbeat)
			drawer.image(tekkist, getX(), getY(), getWidth(), getHeight());	
		else {
			drawer.image(gravestone, getX()-10, getY(), getWidth(), getHeight());
			
			if (health.getHealthAmount() < 100)
				health.setHealthAmount(health.getHealthAmount() + 0.2);
		}
			
		// power up
		
		if (superSaiyan) {
			drawer.image(aura, getX() - 35, getY() - 35, getWidth() + 70, getHeight() + 70);
		}
	}

	public void kickBall(Ball b, boolean isLeft) {		
		if (isLeft)
			b.setVX(10);
		else
			b.setVX(-10);
		
		b.setVY(-10);
		b.setState(false);	
		power.setPowerAmount(power.getPowerAmount() + 1);
	}
	
	public void kickPlayer(Tekkist t, boolean isLeft) {
		if (isLeft)
			t.setVX(4);
		else
			t.setVX(-4);
		
		t.getHealth().setHealthAmount(t.getHealth().getHealthAmount() - 5);
		power.setPowerAmount(power.getPowerAmount() + 1);
	}

	public PowerUpBar getPowerUpBar() {
		return power;
	}
	
	public Health getHealth() {
		return health;
	}

	public void makeSuper() {
		if (power.isCapable()) {
			superSaiyan = true;
			power.setCapability(false);
			power.setPowerAmount(0);
		}
	}
	
	public void makeNotSuper() {
		superSaiyan = false;
		power.setCapability(true);
	}
	
	public boolean getSuperStatus() {
		return superSaiyan;
	}
	
	public void freeze() {
		frozen = true;
		
		setRightMobility(false);
		setLeftMobility(false);
		setUpwardsMobility(false);
		canKick = false;
		
		setVX(0);
		setVY(0);
	}
	
	public void unfreeze() {		
		frozen = false;
		
		setRightMobility(true);
		setLeftMobility(true);
		setUpwardsMobility(true);
		canKick = true;
	}
	
	public boolean isFrozen() {
		return frozen;
	}
	
	public boolean getHasBoxPower() {
		return hasBoxPower;
	}
	
	public void collectBox() {
		boxPower = new PowerUp();
		hasBoxPower = true;
	}
	
	public PowerUp getBoxPower() {
		return boxPower;
	}
	
	public boolean getIsWalking() {
		return isWalking;
	}
	
	public void findHeartbeat() {
		if (health.getHealthAmount() <= 0)
			hasHeartbeat = false;
	}
	
	public boolean hasHeartbeat() {
		return hasHeartbeat;
	}
	
	public void defibrillation() {
		hasHeartbeat = true;
	}
	
	public void setTimeOfDeath(int t) {
		timeOfDeath = t;
	}
	
	public int getTimeOfDeath() {
		return timeOfDeath;
	}
	
	public boolean canKick() {
		return canKick;
	}
}