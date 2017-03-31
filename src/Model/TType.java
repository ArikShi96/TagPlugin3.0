package Model;

public class TType {
	String name;
	String class_name;
	int class_id;
	public TType(String n, String cn) {
		// TODO Auto-generated constructor stub
		name=n;
		class_name=cn;
		class_id=-1;
	}
	
	public String getName() {
		return name;
	}
	public String getClass_name() {
		return class_name;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
}