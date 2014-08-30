package render.map;

public class MapStub {
	public float [][] heights;
	public float tileWidth;
	
	public MapStub(int width, int height, float tileWidth)	{
		heights = new float[width][height];
		this.tileWidth = tileWidth;
	}
	
	public float getHeight()	{
		return (heights[0].length - 1) * tileWidth;
	}
	
	public float getWidth()	{
		return (heights.length - 1) * tileWidth;
	}
	
	public boolean isPointOnMap(float x, float y)	{
		return 0 <= x &&
			   x < tileWidth * (heights.length - 1) &&
			   0 <= y &&
			   y < tileWidth * (heights[0].length - 1);
	}
	
	public HeightQuad getHeightsForPoint(float x, float y)	{
		if(isPointOnMap(x, y))	{
			int i = (int)(x/tileWidth),
				j = (int)(y/tileWidth);
			return new HeightQuad(
				heights[i][j],
				heights[i+1][j],
				heights[i][j+1],
				heights[i+1][j+1]
			);
		}
		return null;
	}
	
	public static MapStub generateRandomMap(int width, int height, float tileWidth, float maxHeight)	{
		MapStub m = new MapStub(width, height, tileWidth);
		for(int j = 0; j < height; j++)
			for(int i = 0; i < width; i++)
				m.heights[i][j] = (float)Math.random()*maxHeight;
		return m;
	}
	
	public class HeightQuad	{
		public float topLeft, topRight, bottomLeft, bottomRight;
		public HeightQuad(float topLeft, float topRight, float bottomLeft, float bottomRight)	{
			this.topLeft = topLeft;
			this.topRight = topRight;
			this.bottomLeft = bottomLeft;
			this.bottomRight = bottomRight;
		}
	}
}
