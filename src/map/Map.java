package map;

public class Map {

	private int sizeOfMapSideInTiles;
	
	private MapTileCorner[][] mapTileCornerArray;
	
	Map(int sizeOfMapSideInPatches){
		sizeOfMapSideInTiles = (sizeOfMapSideInPatches * 16) + 1;

		mapTileCornerArray = new MapTileCorner[sizeOfMapSideInTiles][sizeOfMapSideInTiles];
	}

	public void setMapTileCorner(MapTileCorner mapTileCorner){
		mapTileCornerArray[mapTileCorner.getxIndex()][mapTileCorner.getyIndex()] = mapTileCorner;
	}
	
	public MapTileCorner getMapTileCorner(int xIndex, int yIndex){
		return mapTileCornerArray[xIndex][yIndex];
	}
	
	public static void main(String[] args){
		MapTileCorner tempTile;
		MapReader rawr = new MapReader("C:/Users/FNB/git/0ad/0ad/binaries/data/mods/public/maps/scenarios/Arcadia 02.pmp", "placeholder");
		//MapReader rawr = new MapReader("Arcadia 02.pmp", "placeholder");
		Map map = new Map(rawr.getNumMapTileCorners());

		tempTile = rawr.getNextMapTileCorner();
		map.setMapTileCorner(tempTile);

		while(tempTile != null){
			tempTile = rawr.getNextMapTileCorner();
			map.setMapTileCorner(tempTile);
			System.out.println(tempTile.getxIndex());
			System.out.println(tempTile.getyIndex());
			System.out.println(tempTile.getHeight());
		}
	}
	
}
