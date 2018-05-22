import processing.core.PApplet;
import processing.core.PImage;

public class DrawingSurface extends PApplet {

	private Screen p;
	private GamePanel gp;
	private int n;
	private boolean inGame;
	private String p1;
	private String p2;

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

			if (n==4) {
				n=2;
			} else {
				n++;
			}

			System.out.println(n);
		}

		if (n==1) {
			InsPanel ip = new InsPanel();
			p = ip;
		} else if (n==2 || n==3) {

			PlayerSelect ps = new PlayerSelect();
			p = ps;
			ps.draw(this);
			if (n==2) {
				p1=ps.getTekkistPicture(mouseX, mouseY, true);
			} else if (n==3) {
				p2=ps.getTekkistPicture(mouseX, mouseY, false);
			}
			
		}else if (n==4){
			gp = new GamePanel(this, p1, p2);
			inGame = true;
			settings();
			setup();
			gp.draw();
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
