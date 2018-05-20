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
public class MenuPanel extends DrawingSurface {

	private PImage background;

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
			PApplet.main("InsPanel");
		}
		
	}
}
