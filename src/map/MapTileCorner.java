package map;

public class MapTileCorner {

	private int height;
	private int xIndex;
	private int yIndex;
	
	MapTileCorner(int height, int xIndex, int yIndex){
		this.height = height;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getxIndex() {
		return xIndex;
	}

	public void setxIndex(int xIndex) {
		this.xIndex = xIndex;
	}

	public int getyIndex() {
		return yIndex;
	}

	public void setyIndex(int yIndex) {
		this.yIndex = yIndex;
	}
	
	
}
