
public abstract class PhysicsObject {

	private int x, y;
	private int width, height;
	
	public PhysicsObject(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setX(int d) {
		this.x = d;
	}
	
	public void setY(int y) {
		this.y = y;
	}		
}
