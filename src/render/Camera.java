package render;

import java.awt.geom.Point2D;

public class Camera {
	public Point2D.Float position;
	public float height;
	public float maxRenderHeight;
	public float multiplier = 1;
	
	public Camera(float x, float y, float height, float maxRenderHeight, float multiplier)	{
		position = new Point2D.Float(x, y);
		this.height = height;
		this.maxRenderHeight = maxRenderHeight;
		this.multiplier = multiplier;
	}
	
	public void move(float x, float y)	{
		position.x += x * multiplier;
		position.y += y * multiplier;
	}
}
