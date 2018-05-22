import processing.core.*;

/**
 * Represents the power up bar that a Tekkist has
 * @author Brent Delano
 * @version 5/21/18
 */
public class PowerUpBar {

	private double power;
	private boolean enabled;
	private float x, y;
	
	public PowerUpBar(float x, float y) {
		this.x = x;
		this.y = y;
		power = 0;
		enabled = false;
	}
	
	public boolean isCapable() {
		return enabled;
	}
	
	public void setCapability(boolean s) {
		enabled = s;
	}
	
	public double getPowerAmount() {
		return power;
	}
	
	public void setPowerAmount(double p) {
		power = p;
	}
	
	public void draw(PApplet drawer) {
		// power bars
		
		drawer.pushMatrix();
		drawer.noFill();
		drawer.rect(x, y, 300, 20);
		
		power += 0.25;
		drawer.fill(51, 255, 255);
		if (power < 300)
			drawer.rect(x, y, (float) power, 20);
		else {
			drawer.rect(x, y, 300, 20);
			enabled = true;
		}
		
		drawer.textSize(15);
		drawer.fill(0);
		drawer.text("POWER", x, y - 2);
		drawer.popMatrix();
	}
}
