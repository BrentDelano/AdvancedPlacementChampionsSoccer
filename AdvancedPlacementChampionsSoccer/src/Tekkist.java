import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the player in the game, which is a MovingObject
 * @author Brent Delano
 * @version 5/15/18
 */
public class Tekkist extends MovingObject {

	private PImage tekkist;
	private PImage aura;
	private PowerUpBar power;
	private Health health;
	private boolean superSaiyan;
	private PowerUp boxPower;
	private boolean hasBoxPower;

	

	public Tekkist(int x, int y, int w, int h, PowerUpBar p, Health health) {
		super(x, y, w, h);
		tekkist = new PImage();
		aura = new PImage();
		superSaiyan = false;
		power = p;
		this.health = health;
		boxPower = null;
		hasBoxPower = false;
		
	}

	public void walkHorizontally(int direction) {
		if (direction > 0) {
			while (getVX() < 4.0)
				setVX(getVX() + 0.1);

		} else if (direction < 0) {
			while (getVX() > -4.0)
				setVX(getVX() - 0.1);

		} else {
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
			while (getVX() < 25.0)
				setVX(getVX() + 0.1);

		} else if (direction < 0) {
			while (getVX() > -25.0)
				setVX(getVX() - 0.1);

		} else {
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
	}

	public void draw(PApplet drawer) {
		
		// tekkist image
		
		drawer.image(tekkist, getX(), getY(), getWidth(), getHeight());	
		
		// power up
		
		if (superSaiyan) {
			drawer.image(aura, getX() - 35, getY() - 35, getWidth() + 70, getHeight() + 70);
		}
	}

	public void kick(Ball b, Surface s, boolean isLeft) {		
		if (isLeft)
			b.setVX(10);
		else
			b.setVX(-10);
		b.setVY(-10);
		b.setState(false);	
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
	
	public boolean getSuperStatus() {
		return superSaiyan;
	}
	
	public void freeze() {
		setRightMobility(false);
		setLeftMobility(false);
		setUpwardsMobility(false);
	}
	
	public boolean getHasBoxPower()
	{
		return hasBoxPower;
	}
	
	public void collectBox()
	{
		boxPower = new PowerUp();
		hasBoxPower = true;
	}
	
	public PowerUp getBoxPower()
	{
		return boxPower;
	}
}
