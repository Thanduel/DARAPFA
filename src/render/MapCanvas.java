package render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JLabel;

public class MapCanvas extends JLabel {
	public MapStub map;
	public Camera camera;
	
	public MapCanvas(MapStub map, Camera camera)	{
		super();
		
		this.map = map;
		this.camera = camera;
		
		// this.setDoubleBuffered(true);
	}
	
	public void paint(Graphics g)	{
		Graphics2D g2 = (Graphics2D) g;
//		g2.setPaint(new MapGradient(new Rectangle(10, 10, 400, 400), 5, 0, 10, 0, 10));
//		g2.translate(100, 0);
//		g2.fill(new Rectangle(10, 10, 400, 400));
		g2.setPaint(new MapRenderer(map, camera));
		g2.fill(new Rectangle(0, 0, getSize().width, getSize().height));
	}
}
