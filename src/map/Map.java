package map;

public class Map {

	private int sizeOfMapSideInPatches;
	
	private MapTileCorner[][] mapTileCornerArray;
	
	Map(int sizeOfMapSideInPatches){
		this.sizeOfMapSideInPatches = sizeOfMapSideInPatches;

		mapTileCornerArray = new MapTileCorner[sizeOfMapSideInPatches][sizeOfMapSideInPatches];
	}

	public void setMapTileCorner(MapTileCorner mapTileCorner){
		mapTileCornerArray[mapTileCorner.getxIndex()][mapTileCorner.getyIndex()] = mapTileCorner;
	}
	
	public MapTileCorner getMapTileCorner(int xIndex, int yIndex){
		return mapTileCornerArray[xIndex][yIndex];
	}
	
}
