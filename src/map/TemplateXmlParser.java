package map;

import java.awt.geom.Point2D;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class TemplateXmlParser  {
	
	private String absoluteTemplatePath;
	private boolean isCircular;
	private boolean isSquare;
	private boolean isObstruction;
	private float radius;
	private float length;
	private float width;
	private float obs_radius;
	private float obs_length;
	private float obs_width;
	private Point2D.Float location;
	
	TemplateXmlParser(Entity entity)
	{
		absoluteTemplatePath = entity.getPathFromTemplatePath();
		MapXmlParser templateXmlParser = new MapXmlParser(absoluteTemplatePath);
		NodeList entity_list = templateXmlParser.extractElementList("Entity");
		NodeList footprint_list = templateXmlParser.getTagInElement( (Element)entity_list.item(0), "Footprint");
		NodeList square_list = templateXmlParser.getTagInElement( (Element)entity_list.item(0), "Square");
		if(square_list != null || square_list.getLength() > 0){
			isSquare = true;
			length = templateXmlParser.getFloatAttribute("width", (Element)square_list.item(0));
			width = templateXmlParser.getFloatAttribute("depth", (Element)square_list.item(0));
		}
		else
		{
			isCircular = true;
			NodeList circle_list = templateXmlParser.getTagInElement( (Element)entity_list.item(0), "Circle");
			radius = templateXmlParser.getFloatAttribute("radius", (Element)square_list.item(0));
		}

		NodeList obstruction_list = templateXmlParser.getTagInElement( (Element)entity_list.item(0), "Obstruction");
		NodeList static_list = templateXmlParser.getTagInElement( (Element)entity_list.item(0), "Static");
		if(isSquare() && (static_list != null || static_list.getLength() > 0)){
			obs_length = templateXmlParser.getFloatAttribute("width", (Element)static_list.item(0));
			obs_width = templateXmlParser.getFloatAttribute("depth", (Element)static_list.item(0));
		}
		//else
		//{
			obs_radius = templateXmlParser.getFloatAttribute("radius", (Element)static_list.item(0));
		//}
	}
	
	private void extractObstruction(){
		
	}
	
	public boolean isObstruction(){
		return isObstruction;
	}
	
	public boolean isCircular(){
		return isCircular;
	}

	public boolean isSquare(){
		return isSquare;
	}
	
	public float getObstructionRadius(){
		return radius;
	}
	
	public float getObstructionLength(){
		return length;
	}
	
	public float getObstructionWidth(){
		return width;
	}
	
	public Point2D.Float getObstructionLocation(){
		return location;
	}

}
