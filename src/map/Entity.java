package map;

public class Entity {

  private int uid;
  private float x, y, theta;
  private String template_path;

  Entity(int uid, float x, float y, float theta, String template_path)
  {
		this.uid = uid;
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.template_path = template_path;
  }
  
  public void PrintEntity()
  {
	  System.out.println("Entity: " + this.x + " " + this.y + " " + this.theta + " ");
  }
  
  public String toString(){
	  return new String("Entity: " + this.x + " " + this.y + " " + this.theta + " ");
  }

public int getUid() {
	return uid;
}

public void setUid(int uid) {
	this.uid = uid;
}

public float getX() {
	return x;
}

public void setX(float x) {
	this.x = x;
}

public float getY() {
	return y;
}

public void setY(float y) {
	this.y = y;
}

public float getTheta() {
	return theta;
}

public void setTheta(float theta) {
	this.theta = theta;
}

public String getTemplate_path() {
	return template_path;
}

public void setTemplate_path(String template_path) {
	this.template_path = template_path;
}

	public String getPathFromTemplatePath(){

		String[] stringArray = this.template_path.split("|");
		if(stringArray[0].equals("actor"))
				return new String("../0ad/0ad/binaries/data/mods/public/simulation/templates/");
		else
			return null;
	}
}
