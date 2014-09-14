package map;

import java.awt.geom.Point2D;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

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
	private MapXmlParser templateXmlParser;
	
	TemplateXmlParser(Entity entity)
	{
		absoluteTemplatePath = entity.getPathFromTemplatePath();
		templateXmlParser = new MapXmlParser(absoluteTemplatePath);
		System.out.println(absoluteTemplatePath);
		NodeList entity_list = templateXmlParser.extractElementList("*");

		if(entity_list.item(0) == null )
			return;

		for(int i = 0; i < entity_list.getLength(); i++)
		{
			if(entity_list.item(i).getNodeName().equals("Footprint"))
				parseFootprint((Element)entity_list.item(i));
			if(entity_list.item(i).getNodeName().equals("Obstruction"))
				parseObstruction((Element)entity_list.item(i));
		}
	}
	
	private void parseObstruction(Element footprint){
		isObstruction = true;
		NodeList footprint_children = footprint.getChildNodes();
		
		if(footprint_children.item(1).getNodeName().equals("Unit"))
			parseUnitObstruction(footprint_children);
		else if(footprint_children.item(1).getNodeName().equals("Obstructions"))
			parseEdgeObstruction(footprint_children);
		else if(footprint_children.item(1).getNodeName().equals("Static"))
			parseStaticObstruction(footprint_children);
	}
	
	private void parseUnitObstruction(NodeList obstructionChildren){
		obs_radius = templateXmlParser.getFloatAttribute("radius", (Element)obstructionChildren.item(1));
	}

	private void parseStaticObstruction(NodeList obstructionChildren){
		obs_length = templateXmlParser.getFloatAttribute("width", (Element)obstructionChildren.item(1));
		obs_width = templateXmlParser.getFloatAttribute("depth", (Element)obstructionChildren.item(1));
	}

	private void parseEdgeObstruction(NodeList obstructionChildren){
		NodeList obstructionsChildren = obstructionChildren.item(1).getChildNodes();
		obs_length = templateXmlParser.getFloatAttribute("width", (Element)obstructionsChildren.item(1));
		obs_width = templateXmlParser.getFloatAttribute("depth", (Element)obstructionsChildren.item(1));
	}
		
	
	private void parseFootprint(Element footprint){
		NodeList footprint_children = footprint.getChildNodes();
		if(footprint_children.item(1).getNodeName().equals("Circle"))
			parseCircularFootprint(footprint_children);
		else
			parseSquareFootprint(footprint_children);
	}
	
	private void parseCircularFootprint(NodeList footPrintChildren){
			isCircular = true;
			radius = templateXmlParser.getFloatAttribute("radius", (Element)footPrintChildren.item(1));
	}
	
	private void parseSquareFootprint(NodeList footPrintChildren){
			isSquare = true;
			length = templateXmlParser.getFloatAttribute("width",(Element) footPrintChildren.item(1));
			width = templateXmlParser.getFloatAttribute("depth", (Element) footPrintChildren.item(1));
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
		return obs_radius;
	}
	
	public float getObstructionLength(){
		return obs_length;
	}
	
	public float getObstructionWidth(){
		return obs_width;
	}
	
	public Point2D.Float getObstructionLocation(){
		return location;
	}
	
	public String getTemplatePath()	{
		return absoluteTemplatePath;
	}
	
	public static void main(String args[]){
		
		//MapXmlParser map_element_entities = new MapXmlParser("../0ad/0ad/binaries/data/mods/public/maps/scenarios/Arcadia 02.xml");

		List obstructionList = new ArrayList();
		//EntityXmlParser entityParser = new EntityXmlParser("../0ad/0ad/binaries/data/mods/public/maps/scenarios/Arcadia 02.xml");
		//EntityXmlParser entityParser = new EntityXmlParser("../0ad/0ad/binaries/data/mods/public/maps/scenarios/Battle for the Tiber.xml");
		EntityXmlParser entityParser = new EntityXmlParser("../0ad/0ad/binaries/data/mods/public/maps/scenarios/Sandbox - Ptolemies.xml");
		List entityList = entityParser.getEntityList();
		Iterator listIterator = entityList.iterator();
		while(listIterator.hasNext()){
			TemplateXmlParser test = new TemplateXmlParser((Entity)listIterator.next());
			if(test.isObstruction())
				obstructionList.add(test);
		}
		
		System.out.println("number of obstructions" + obstructionList.size());
	}
	
}
