package map;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class CircleObstruction extends Obstruction {
	private Ellipse2D.Float circle; 
	
	public CircleObstruction(float x, float y, float radius) {
		circle = new Ellipse2D.Float(x-radius, y-radius, radius*2, radius*2);
	}

	@Override
	public Shape getShape() {
		return circle;
	}
}
