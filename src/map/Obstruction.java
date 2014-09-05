package map;

import java.awt.Shape;
import java.awt.geom.Point2D;

public abstract class Obstruction {
	
	public abstract Shape getShape();
	
	public static Obstruction getObstructionFromEntry(Entity e)	{
		TemplateXmlParser txmlp = new TemplateXmlParser(e);
		if(txmlp.isObstruction())	{
			Point2D.Float location = txmlp.getObstructionLocation();
			if(txmlp.isSquare())	{
				return new SquareObstruction(location.x,
										location.y,
										txmlp.getObstructionWidth(),
										txmlp.getObstructionHeight(),
										e.getTheta());
			}
			else if(txmlp.isCircle())	{
				return new CircleObstruction(location.x,
											location.y,
											txmlp.getObstructionRadius());
			}
			else	{
				throw new ParsingException("Entity needs to be either a Cirlce or a Square");
			}
		}
		return null;
	}
}
