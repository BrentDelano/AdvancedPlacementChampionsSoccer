import processing.core.*;

/**
 * Represents the health that a Tekkist has
 * @author Brent Delano
 * @version 5/15/18
 */
public class Health {

	private float x, y;
	private double health;
	
	public Health(float x, float y) {
		this.x = x;
		this.y = y;
		health = 100;
	}
	
	public double getHealthAmount() {
		return health;
	}
	
	public void setHealthAmount(double h) {
		health = h;
	}
	
	public void draw(PApplet drawer) {
		// health bars
		
		drawer.pushMatrix();
		drawer.noFill();
		drawer.rect(x, y, 300, 20);
		
		if (health > 66)
			drawer.fill(0, 255, 0);
		else if (health > 33)
			drawer.fill(255, 255, 0);
		else
			drawer.fill(255, 0, 0);
		
		if (health > 0) 
			drawer.rect(x, y, (float) (health * 3), 20);
		
		drawer.textSize(15);
		drawer.fill(0);
		drawer.text("HEALTH", x, y - 2);
		drawer.popMatrix();
	}
}
