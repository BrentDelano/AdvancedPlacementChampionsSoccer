import processing.core.PApplet;

public class Surface extends PhysicsObject {
	
	public Surface() {
		super(0, 30, 400, 320);
	}
	
	public Surface(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public boolean isPointInSurface(int otherX, int otherY) {
		if (otherX >= getX() && otherY >= getY() && otherX <= getX() + getWidth() && otherY <= getY() + getHeight())
			return true;
		return false;
	}
	
	public void draw(PApplet drawer) {
		drawer.rectMode(drawer.CENTER);
		drawer.fill(51);
		drawer.stroke(255);

		drawer.translate(250, 250, 0);
		drawer.rotateX(drawer.PI/4);
		drawer.rect(0, 30, 400, 320);
	}
}
