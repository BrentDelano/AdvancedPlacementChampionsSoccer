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
public class PlayerSelect extends PApplet{

	private ArrayList<PImage> pictures;
	private int imgWidth;
	private int imgHeight;
	private PImage background;
	private JFrame window;
	
	public PlayerSelect() {

		background = new PImage();
	}

	public PlayerSelect(ArrayList<PImage> pictures, int imgWidth, int imgHeight)
	{
		this.pictures = pictures;
		this.imgHeight = imgHeight;
		this.imgWidth= imgWidth;

		background = new PImage();
	}
	//dont necessarily multiply by 5 in return statement, multiply by # of pics per row
	public PImage getTekkistPicture(double mouseX, double mouseY)
	{
		int a = (int) (mouseY/imgHeight);
		int b = (int) (mouseX/imgWidth);
		return pictures.get(b*5 + a);
	}
	
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
			
			GamePanel p = new GamePanel();
			PApplet.runSketch(new String[]{""}, p);
			PSurfaceAWT surf = (PSurfaceAWT) p.getSurface();
			PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
			JFrame window = (JFrame)canvas.getFrame();

			window.setSize(316, 159);
			window.setMinimumSize(new Dimension(100,100));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
			window.setVisible(true);

			this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			p.draw();
			this.window.setVisible(false);
		}
		
	}
	public void setWindow(JFrame window) {
		
		this.window = window;
		
	}
}
