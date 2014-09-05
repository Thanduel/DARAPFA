package render.map;

import java.awt.Color;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.List;

import map.Obstruction;

// QUESTION: Should the maxHeight change with the camera height?
// ANSWER: Not sure, but the camera should definitely hold the maxHeight
public class MapRenderer implements Paint {
	/* The map is used to determine the tile corners, the heights of which
	 * are used to render the landscape. The map also stores the base value
	 * for the tile width, prior to any scaling from camera height.
	 */
	private MapStub map;
	
	/* The camera holds a position (of its center) and a height from the
	 * landscape. This height is used to determine the maxHeight of rendering
	 * for height interpolation as well as the scale factor of the tile width.
	 * The higher the camera, the smaller the scale of the tile width and the
	 * dimmer the heights get (when rendered). This gives the impression of
	 * increased distance from the landscape.
	 */
	private Camera camera;
	
	public MapRenderer(MapStub map, Camera camera)	{
		this.map = map;
		this.camera = camera;
	}
	
	@Override
	public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)	{
		return createContext();
	}
	
	public PaintContext createContext()	{
		return new MapRendererContext();
	}
	
	@Override
	public int getTransparency()	{
		return Transparency.TRANSLUCENT;
	}
	
	private final class MapRendererContext implements PaintContext	{
		// This class leeches off of its parent class
		public MapRendererContext()	{}
		
		/* Interpolate between two values, and a fraction between them
		 * in [0,1]
		 */
		private float interpolateHeight(float height1, float height2, float fraction)	{
			float interpolatedHeight = (height2 - height1) * fraction + height1;
			return Math.min(Math.max(interpolatedHeight, 0), camera.maxRenderHeight);
		}
		
		private float bilinearInterpolateHeight(MapStub.HeightQuad heights, float fractionX, float fractionY){
			float interpolationX1 = interpolateHeight(heights.topLeft, heights.topRight, fractionX);
			float interpolationX2 = interpolateHeight(heights.bottomLeft, heights.bottomRight, fractionX);
			return interpolateHeight(interpolationX1, interpolationX2, fractionY);
		}
		
		private Color getColorFromHeight(float height)	{
			height = Math.min(Math.max(0, height), camera.maxRenderHeight);
			return new Color(0f, 0f, 0f, height/camera.maxRenderHeight);
		}
		
		@Override
		public void dispose()	{}
		
		@Override
		public ColorModel getColorModel()	{
			return ColorModel.getRGBdefault();
		}
		
		/* This is where we render everything. This will take into account the position
		 * and height of the camera for offsetting and scaling.
		 */
		@Override
		public Raster getRaster(int startX, int startY, int tileWidth, int tileHeight)	{
			List<Obstruction> potentialObstructions = map.getPotentialObstructions(startX + camera.position.x, startY + camera.position.y, tileWidth, tileHeight);
			WritableRaster raster = getColorModel().createCompatibleWritableRaster(tileWidth, tileHeight);
			int [] pixelData = new int[tileWidth * tileHeight * 4];
			for(int y = 0; y < tileHeight; y++)	{
				for(int x = 0; x < tileWidth; x++)	{
					// calculate the position in the world this pixel relates to
					float posX = startX + x + camera.position.x,
						  posY = startY + y + camera.position.y;
					
					// the colours we'll eventually render this pixel with
					int r = 0, g = 0, b = 0, a = 0;
					
					Obstruction ob = null;
					for(Obstruction o: potentialObstructions)
						if(o.getShape().contains(new Point2D.Float(posX, posY)))	{
							ob = o;
							break;
						}
					
					if(ob == null)	{
						MapStub.HeightQuad heights = map.getHeightsForPoint(posX, posY);
						if(heights != null)	{
							// we need to find fractionX and fractionY
							float fractionX = ((posX) % map.tileWidth)/map.tileWidth,
								  fractionY = ((posY) % map.tileWidth)/map.tileWidth;
							
							// if the fractions fit in the ranges, interpolate for color
							// otherwise the pixel is completely transparent
							if(0 <= fractionX && fractionX <= 1 && 0 <= fractionY && fractionY <= 1)	{
								Color c = getColorFromHeight(bilinearInterpolateHeight(heights, fractionX, fractionY));
								r = c.getRed();
								g = c.getGreen();
								b = c.getBlue();
								a = c.getAlpha();
							}
						}
					}
					else	{
						r = 255;
						a = 255;
					}
					
					// now fill in the values in pixelData
					int base = (tileWidth * y + x) * 4;
					pixelData[base + 0] = r;
					pixelData[base + 1] = g;
					pixelData[base + 2] = b;
					pixelData[base + 3] = a;
				}
			}
			
			// set the data and return
			raster.setPixels(0, 0, tileWidth, tileHeight, pixelData);
			return raster;
		}
	}
}
