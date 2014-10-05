package map;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//import java.util.Scanner;

public class MapReader {
	
	
	FileInputStream fileInput;
//	Scanner fileInput;
	private int mapSize;
	private int xIndex;
	private int yIndex;
	private int counter;
	private int maxNumCorners;
	private String pmpPath;
	
	public MapReader(String pmpPath, String xmlPath)
	{
		this.pmpPath = pmpPath;
		xIndex = 0;
		yIndex = 0;
		counter = 0;

		System.out.println(pmpPath);
		//try{
	//		inputStream = new ObjectInputStream(new FileInputStream(pmpPath));
	//	}catch(IOException e){
	//		System.out.println("I don't actually care about this exception" + e);
	//	}
		
		try{
		fileInput = new FileInputStream(pmpPath);
		}catch(IOException e){
			System.out.println("I don't actually care about this exception" + e);
		}
		readMapSize();
		maxNumCorners = ((mapSize * 16) + 1) * ((mapSize * 16) + 1);
	}
	
	public int getNumMapTileCorners(){
		return mapSize + 1;
	}
	private int little2big(int i) {
	    return((i&0xff)<<24)+((i&0xff00)<<8)+((i&0xff0000)>>8)+((i>>24)&0xff);
	}
	
	private int readInt(){
		try{
		int tempInt = fileInput.read();
		tempInt = tempInt << 8;
		tempInt = tempInt | fileInput.read();
		tempInt = tempInt << 8;
		tempInt = tempInt | fileInput.read();
		tempInt = tempInt << 8;
		tempInt = tempInt | fileInput.read();
		return little2big(tempInt);
		}catch(IOException e){
			System.out.println("I don't actually care about this exception" + e);
			return -1;
		}
	}
	
	public String getPmpPath(){
		return pmpPath;
	}
	
	private short readShort(){
		try{
		int tempShort = fileInput.read();
		tempShort = tempShort | (fileInput.read() << 8);
		return (short)tempShort;
		}catch(IOException e){
			System.out.println("I don't actually care about this exception" + e);
			return -1;
		}
	}

	private char readChar(){
		try{
		int tempChar = fileInput.read();
		return (char)tempChar;
		}catch(IOException e){
			System.out.println("I don't actually care about this exception" + e);
			return (char)-1;
		}
	}
	
	private void moveFilePointerToStartofMapSize()
	{
		readInt();
		readInt();
		readInt();
	}
	
	private int nextXIndex(){
		int oldIndex = xIndex;
		xIndex++;

		if(xIndex == ((mapSize * 16) + 1))
			xIndex = 0;

		return oldIndex; 
	}

	private int nextYIndex(){
		int oldIndex = yIndex;

		if(xIndex == 0)
			yIndex++;

		return oldIndex; 
	}
	
	private void readMapSize()
	{
		moveFilePointerToStartofMapSize();
		mapSize = readInt();
		System.out.println(mapSize);
	}
	
	public int getMapSize(){
		return mapSize;
	}
	
	public MapTileCorner getNextMapTileCorner(){
		if(counter >= maxNumCorners)
			return null;

		 counter++;
		 return new MapTileCorner(readShort(), nextXIndex(), nextYIndex());
	}

}
