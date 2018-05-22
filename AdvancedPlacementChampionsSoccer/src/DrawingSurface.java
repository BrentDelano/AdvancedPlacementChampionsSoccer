import processing.core.PApplet;

/**
 * Represents a drawing surface that can switch between panels
 * @author Tony Yu
 * @version 2/21/18
 *
 */
public class DrawingSurface extends PApplet {

	private Screen p;
	private GamePanel gp;
	private int n;

	public DrawingSurface() {
		p = new MenuPanel();
	}

	public void settings() {
		size(1280, 800, "processing.opengl.PGraphics2D");
		if (n==3) {
			gp.settings();
		}
	}

	public void setup() {
		if (n==3) {
			gp.setup();
		}
	}

	public void draw() {

		clear();

		if (n==3) {
			gp.draw();
		} else {
			p.draw(this);
		}
	}

	public void mousePressed() {

		if (mouseButton == LEFT) {
		
			if (n==3) {
				n--;
			} else {
				n++;
			}
		}

		if (n==1) {
			InsPanel ip = new InsPanel();
			p = ip;
		} else if (n==2) {
			PlayerSelect ps = new PlayerSelect();
			p = ps;
		} else if (n==3) {
			gp = new GamePanel(this);
			settings();
			setup();
		}

		if(n==3) {
			gp.mousePressed();
		}
	}

	public void keyPressed() {
		if(n==3) {
			gp.keyPressed();
		}
	}
	
	public void keyReleased() {
		if(n==3) {	
			gp.keyReleased();
		}
	}
}