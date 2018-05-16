import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * Contains the main method
 * @author Brent Delano
 * @version 5/10/18
 *
 */
public class AdvancedPlacementChampionsSoccer {

	public static void main(String[] args) {
		MenuPanel m = new MenuPanel();
		PApplet.runSketch(new String[]{""}, m);
		PSurfaceAWT surf = (PSurfaceAWT) m.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();
		m.setWindow(window);

		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setVisible(true);		
	}

}
