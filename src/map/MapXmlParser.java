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
	List myEmpls;
	Document dom;
	private String map_xml_path;


	public MapXmlParser(String map_xml_path){
    this.map_xml_path = map_xml_path;
		//create a list to hold the employee objects
    //
		myEmpls = new ArrayList();
	}

	public void runExample() {

		//parse the xml file and get the dom object
		parseXmlFile();

		//get each entity element and create a Entity object
		parseDocument();

		//Iterate through the list and print the data
		printData();

	}


	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			System.out.println(map_xml_path);
			dom = db.parse(map_xml_path);


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}


	private void parseDocument(){
		//get the root elememt
		Element docEle = dom.getDocumentElement();

		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("Entity");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {

				//get the employee element
				Element el = (Element)nl.item(i);

				//get the Employee object
				Entity e = getEntity(el);

				//add it to list
				myEmpls.add(e);
			}
		}
	}


	/**
	 * I take an employee element and read the values in, create
	 * an Employee object and return it
	 * @param empEl
	 * @return
	 */
	private Entity getEntity(Element empEl) {

		//for each <employee> element get text or int values of
		//name ,id, age and name
		String templatePath = getTextValue(empEl,"Template");
		//int uid = getIntValue(empEl, "Entity");
		float theta = getFloatValue(empEl,"Orientation");
		Point2D.Float position = getPoint2DFloat(empEl, "Position");

		//Create a new Employee with the value read from the xml nodes
		Entity e = new Entity(0, position.x, position.y, theta, templatePath);

		return e;
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
	private String getTextValue(Element ele, String tagName) {

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

		System.out.println(textVal);
		return textVal;
	}
	
	
	private String getOrientation(Element ele) {

		String textVal = null;

		textVal = ele.getAttribute("y");
		System.out.println(textVal);
		return textVal;
	}


	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}

	private float getFloatValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Float.parseFloat(getTextValue(ele,tagName));
	}

	private Point2D.Float getPoint2DFloat(Element ele, String tagName) {
		//in production application you would catch the exception
		if(tagName.equals("Position")){

			NodeList nl = ele.getElementsByTagName("Position");

			if(nl != null && nl.getLength() > 0) 
			{
				String x, y;
				Element el = (Element)nl.item(0);
				x = el.getAttribute("x");
				y = el.getAttribute("z");
				System.out.println(x);
				System.out.println(y);

				return new Point2D.Float(Float.parseFloat(x), Float.parseFloat(y));
			}
			else
				return null;
		}
		else
			return null;
	}
	/**
	 * Iterate through the list and print the
	 * content to console
	 */
	private void printData(){

		System.out.println("No of Entity '" + myEmpls.size() + "'.");

		Iterator it = myEmpls.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}
	
	
	
	


	public static void main(String[] args){
		//create an instance
		//MapXmlParser dpe = new MapXmlParser("C:/Users/FNB/git/0ad/0ad/binaries/data/mods/public/maps/scenarios/Arcadia 02.xml");
		MapXmlParser dpe = new MapXmlParser("../0ad/0ad/binaries/data/mods/public/maps/scenarios/Arcadia 02.xml");

		//call run example
		dpe.runExample();
	}

}
