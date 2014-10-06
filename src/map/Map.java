package map;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class Map {

	private int sizeOfMapSideInTiles;
	private List<Obstruction> obstructions;
	private String xmlFilePath;
	
	public MapTileCorner[][] mapTileCornerArray;

	public float [][] randomHeights;
	public final float TILEWIDTH = (float)4.0;

	public Map(MapReader pmp_map_reader){

		sizeOfMapSideInTiles = (pmp_map_reader.getMapSize() * 16) + 1;
		String pmpFilePath = pmp_map_reader.getPmpPath();
		xmlFilePath = pmpFilePath.split("\\.")[0] + ".xml";
		System.out.println(xmlFilePath);
		mapTileCornerArray = new MapTileCorner[sizeOfMapSideInTiles][sizeOfMapSideInTiles];
		for(int i = 0; i < sizeOfMapSideInTiles; i++)
			for(int j = 0; j < sizeOfMapSideInTiles; j++)
				mapTileCornerArray[i][j] = pmp_map_reader.getNextMapTileCorner();
		
		int counter = 0;
		while(pmp_map_reader.getNextMapTileCorner() != null)
			counter++;
		
		System.out.println("counter: " + counter);

		obstructions = new LinkedList<Obstruction>();
		
		extractObstructionsFromMapFile();
	}

	public void setMapTileCorner(MapTileCorner mapTileCorner){
		mapTileCornerArray[mapTileCorner.getxIndex()][mapTileCorner.getyIndex()] = mapTileCorner;
	}
	
	public MapTileCorner getMapTileCorner(int xIndex, int yIndex){
		return mapTileCornerArray[xIndex][yIndex];
	}
	
	private void extractObstructionsFromMapFile(){
		
		//EntityXmlParser entityParser = new EntityXmlParser("../0ad/0ad/binaries/data/mods/public/maps/scenarios/Arcadia 02.xml");
		//EntityXmlParser entityParser = new EntityXmlParser("../0ad/0ad/binaries/data/mods/public/maps/scenarios/Battle for the Tiber.xml");
		EntityXmlParser entityParser = new EntityXmlParser(xmlFilePath);
		List entityList = entityParser.getEntityList();
		Iterator listIterator = entityList.iterator();
		while(listIterator.hasNext()){
			TemplateXmlParser template = new TemplateXmlParser((Entity)listIterator.next());
			if(template.isObstruction())
				obstructions.add(createObstructionFromTemplate(template));
		}
		System.out.println(obstructions.size());
	}
	
	private Obstruction createObstructionFromTemplate(TemplateXmlParser template){
		if(template.isCircular())
			return new CircleObstruction((float)template.getObstructionLocation().getY(), (float)template.getObstructionLocation().getX(), template.getObstructionRadius());
		else if(template.isSquare())
			return new SquareObstruction((float)template.getObstructionLocation().getY(), (float)template.getObstructionLocation().getX(), template.getObstructionLength(), template.getObstructionWidth(), template.getObstructionOrientation());
		else
			return null;
			
	}
	
	public List<Obstruction> getPotentialObstructions(){
		return obstructions;
	}

	public List<Obstruction> getPotentialObstructions(float x, float y, float width, float height)	{
		return obstructions;
	}
	
	
	public float getHeight()	{
		return mapTileCornerArray.length * TILEWIDTH;
		//return (heights[0].length - 1) * tileWidth;
	}
	
	public float getWidth()	{
		return mapTileCornerArray.length * TILEWIDTH;
	}
	
	public boolean isPointOnMap(float x, float y)	{
		return 0 <= x &&
			   x < TILEWIDTH * (mapTileCornerArray.length - 1) &&
			   0 <= y &&
			   y < TILEWIDTH * (mapTileCornerArray.length - 1);
	}
	
	public HeightQuad getHeightsForPoint(float x, float y)	{
		if(isPointOnMap(x, y))	{
			int i = (int)(x/TILEWIDTH),
				j = (int)(y/TILEWIDTH);
			return new HeightQuad(
				mapTileCornerArray[i][j].getHeight(),
				mapTileCornerArray[i+1][j].getHeight(),
				mapTileCornerArray[i][j+1].getHeight(),
				mapTileCornerArray[i+1][j+1].getHeight()
			);
		}
		return null;
	}
	
	//public static Map generateRandomMap(int width, int height, float tileWidth, float maxHeight)	{
	//	Map m = new Map(width, height, tileWidth);
	//	for(int j = 0; j < height; j++)
	//		for(int i = 0; i < width; i++)
//				m.randomHeights[i][j] = (float)Math.random()*maxHeight;
//		return m;
//	}
	
	public class HeightQuad	{
		public float topLeft, topRight, bottomLeft, bottomRight;
		public HeightQuad(float topLeft, float topRight, float bottomLeft, float bottomRight)	{
			this.topLeft = topLeft;
			this.topRight = topRight;
			this.bottomLeft = bottomLeft;
			this.bottomRight = bottomRight;
		}
	}

	//................................................end of mapstub.......................................
	public static void main(String[] args){
		MapTileCorner tempTile;
		MapReader rawr = new MapReader("C:/Users/FNB/git/0ad/0ad/binaries/data/mods/public/maps/scenarios/Arcadia 02.pmp", "placeholder");
		Map map = new Map(rawr);

		//tempTile = rawr.getNextMapTileCorner();

		//while(tempTile != null){
		//	map.setMapTileCorner(tempTile);
		//	System.out.println("x index " + tempTile.getxIndex());
		//	System.out.println("y index" + tempTile.getyIndex());
		//	System.out.println("height " + tempTile.getHeight());
		//	tempTile = rawr.getNextMapTileCorner();
		//}
	}
	
}
