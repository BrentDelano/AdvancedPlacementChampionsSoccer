import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class AdvancedPlacementChampionsSoccer {

	public static void main(String[] args) {
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
		
		p.draw();
	}

}
