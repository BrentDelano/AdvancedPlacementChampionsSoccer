import processing.core.PApplet;

public class Surface extends PhysicsObject {
	
	public Surface() {
		super(0, 400, 500, 100);
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

		drawer.translate(drawer.width/2, drawer.height/2, 0);
		drawer.rotateX(drawer.PI/4);
		drawer.rect(0, 0, 100, 100);
	}
}
