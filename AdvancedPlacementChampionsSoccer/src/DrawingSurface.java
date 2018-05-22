import processing.core.PApplet;

public class DrawingSurface extends PApplet {

	private Screen p;
	private GamePanel gp;
	private int n;
	private boolean inGame;

	public DrawingSurface() {
		p = new MenuPanel();
	}

	public void settings() {
		size(1280, 800, "processing.opengl.PGraphics2D");
		if (inGame) {
			gp.settings();
		}
	}

	public void setup() {
		if (inGame) {
			gp.setup();
		}
	}

	public void draw() {

		clear();

		if (inGame) {
			gp.draw();
		} else {
			p.draw(this);
		}
	}

	public void mousePressed() {

		if (mouseButton == LEFT) {
			n++;
		}

		if (n==1) {
			InsPanel ip = new InsPanel();
			p = ip;
		} else if (n==2) {
			PlayerSelect ps = new PlayerSelect();
			p = ps;
		} else if (n==3) {
			gp = new GamePanel(this);
			inGame = true;
			settings();
			setup();
		}

		if(inGame) {
			gp.mousePressed();
			
		}
	}

	public void keyPressed() {
		if(inGame) {
			gp.keyPressed();
			
		}
	}
	
	public void keyReleased() {
		if(inGame) {	
			gp.keyReleased();
			
		}
	}
}