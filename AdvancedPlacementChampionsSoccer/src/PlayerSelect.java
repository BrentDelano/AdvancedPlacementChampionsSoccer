import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the screen where the player selects his/her character
 * @author Tony Yu
 * @version 5/15/18
 */
public class PlayerSelect extends DrawingSurface{

	private ArrayList<PImage> pictures;
	private double imgWidth = 235.7;
	private double imgHeight = 313.3333;
	private PImage background;

	public PlayerSelect()
	{
//		for (int i = 1; i<=30; i++) {
//			pictures.add(loadImage(i + ".png"));
//		}
		background = new PImage();
	}
//	
//	public PImage getTekkistPicture(double mouseX, double mouseY)
//	{
//		int a = (int)(mouseX/imgWidth);
//		int b = (int)((mouseY-460)/imgHeight);
//		return pictures.get(b*10 + a + 1);
//	}
//	
	public void setup() {
		background = loadImage("SELECTION.png");
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
			
			PApplet.main("GamePanel");
		}
		
	}
}
