package map;

import java.awt.geom.Point2D;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.util.ArrayList;
import java.util.List;

public class EntityXmlParser {
	
	private List entityList;
	private MapXmlParser mapData;
	private NodeList mapXmlElements;
	
	EntityXmlParser(String xmlMapFile){
		entityList = new ArrayList();
		mapData = new MapXmlParser(xmlMapFile);
		mapXmlElements = mapData.getElementList("Entity");
		parseElementListForEntities(mapXmlElements);
	}

	private Entity getEntity(Element entityElement) {

		//for each <employee> element get text or int values of
		//name ,id, age and name
		String templatePath = mapData.getTextValue(entityElement,"Template");
		//int uid = getIntValue(entityElement, "Entity");
		float theta = mapData.getFloatValue(entityElement,"Orientation");
		Point2D.Float position = getPoint2DFloat(entityElement, "Position");

		//Create a new Employee with the value read from the xml nodes
		Entity e = new Entity(0, position.x, position.y, theta, templatePath);

		return e;
	}
	
	private void parseElementListForEntities(NodeList elementList){
		//get the root elememt
		if(elementList != null && elementList.getLength() > 0) {
			for(int i = 0 ; i < elementList.getLength();i++) {

				//get the employee element
				Element el = (Element)elementList.item(i);

				//get the Employee object
				Entity e = getEntity(el);

				//add it to list
				entityList.add(e);
			}
		}
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

				return new Point2D.Float(Float.parseFloat(x), Float.parseFloat(y));
			}
			else
				return null;
		}
		else
			return null;
	}
	
	public List getEntityList(){
		return entityList;
	}

}
