package map;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class SquareObstruction extends Obstruction {
	
	private Shape rotatedSquare;
	
	public SquareObstruction(float x, float y, float width, float depth, float rotation) {
		Rectangle2D.Float sq = new Rectangle2D.Float(x, y, width, depth);
		/* This creates a rotation from the positive x to the positive y
		 * by +rotation+ radians.
		 */
		AffineTransform at = AffineTransform.getRotateInstance(rotation, x + width/2, y + depth/2);
		rotatedSquare = at.createTransformedShape(sq);
	}

	@Override
	public Shape getShape() {
		return rotatedSquare;
	}

}
