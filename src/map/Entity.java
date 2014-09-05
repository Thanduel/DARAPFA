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
}
