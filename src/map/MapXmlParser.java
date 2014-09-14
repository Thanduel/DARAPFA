package map;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MapXmlParser {

	//No generics
	Document dom;
	private String map_xml_path;


	public MapXmlParser(String map_xml_path){
		
		this.map_xml_path = map_xml_path;

		parseXmlFile();
	}

	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			dom = db.parse(map_xml_path);


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public NodeList getElementList(String tag){
		Element docEle = dom.getDocumentElement();

		//get a nodelist of <employee> elements
		//docEle.
		return docEle.getElementsByTagName(tag);
	}

	public NodeList extractElementList(String elementTag){
		//parseXmlFile();
		return getElementList(elementTag);
	}
	
	public float getFloatAttribute(String attributeName, Element ele){
		
		String textVal = null;

		textVal = ele.getAttribute(attributeName);
		System.out.println(textVal);
		return Float.parseFloat(textVal);
	}
	
	public NodeList getTagInElement(Element element, String tagName){
		return element.getElementsByTagName(tagName);
	}


	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is name I will return John
	 * @param ele
	 * @param tagName
	 * @return
	 */
	
	//this knows to much about entities
	public String getTextValue(Element ele, String tagName) {

		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);

		if(nl != null && nl.getLength() > 0) 
		{
			Element el = (Element)nl.item(0);
			if(tagName.equals("Orientation"))
				textVal = getOrientation(el);
			else
				textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
	
	
	//this is shouldn't be here
	private String getOrientation(Element ele) {

		String textVal = null;

		textVal = ele.getAttribute("y");
		return textVal;
	}


	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	public int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}

	public float getFloatValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Float.parseFloat(getTextValue(ele,tagName));
	}

	public static void main(String[] args){
		//create an instance
		//MapXmlParser dpe = new MapXmlParser("C:/Users/FNB/git/0ad/0ad/binaries/data/mods/public/maps/scenarios/Arcadia 02.xml");
		MapXmlParser dpe = new MapXmlParser("../0ad/0ad/binaries/data/mods/public/maps/scenarios/Arcadia 02.xml");
	}

}
