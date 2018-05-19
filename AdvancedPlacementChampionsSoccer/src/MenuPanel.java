import processing.core.PImage;

import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * Represents the screen where the player can begin the game
 * @author Tony Yu
 * @version 5/10/18
 *
 */
public class MenuPanel extends PApplet {

	private PImage background;
	private JFrame window;

	public MenuPanel() {
		background = new PImage();
	}
	public void setup() {
		background = loadImage("MENU.png");
	}

	public void settings() {
		size(1280, 800, P2D);
	}
	
	public void draw() {			
		clear();
		float ratioX = (float) width / 500; 		
		float ratioY = (float) height / 500; 
		scale(ratioX, ratioY);
		image(background, 0, 0, 500, 500);
	}
	
	public void mouseClicked() {
		if (mouseButton == LEFT) {
			
			InsPanel i = new InsPanel();
			PApplet.runSketch(new String[]{""}, i);
			PSurfaceAWT surf = (PSurfaceAWT) i.getSurface();
			PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
			JFrame window = (JFrame)canvas.getFrame();

			window.setSize(316, 159);
			window.setMinimumSize(new Dimension(100,100));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
			window.setVisible(true);

			this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			i.draw();
			this.window.setVisible(false);
		}
		
	}
	public void setWindow(JFrame window) {
		
		this.window = window;
		
	}
}
