package map;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class Map {

	private int sizeOfMapSideInTiles;
	private List<Obstruction> obstructions;
	private String xmlFilePath;
	
	private MapTileCorner[][] mapTileCornerArray;
	
	Map(MapReader pmp_map_reader){

		sizeOfMapSideInTiles = (pmp_map_reader.getNumMapTileCorners() * 16) + 1;
		String pmpFilePath = pmp_map_reader.getPmpPath();
		xmlFilePath = pmpFilePath.split("\\.")[0] + ".xml";
		System.out.println(xmlFilePath);
		mapTileCornerArray = new MapTileCorner[sizeOfMapSideInTiles][sizeOfMapSideInTiles];
		
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
	}
	
	private Obstruction createObstructionFromTemplate(TemplateXmlParser template){
		if(template.isCircular())
			return new CircleObstruction((float)template.getObstructionLocation().getX(), (float)template.getObstructionLocation().getY(), template.getObstructionRadius());
		else if(template.isSquare())
			return new SquareObstruction((float)template.getObstructionLocation().getX(), (float)template.getObstructionLocation().getY(), template.getObstructionLength(), template.getObstructionWidth(), template.getObstructionOrientation());
		else
			return null;
			
	}
	
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
