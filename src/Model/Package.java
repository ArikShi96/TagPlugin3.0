package Model;

public class Package {

	public Package() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getparent_id() {
		return parent_id;
	}
	public void setparent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public int getIsroot() {
		return isroot;
	}
	public void setIsroot(int isroot) {
		this.isroot = isroot;
	}
	private int id;
	private String name;
	private int parent_id;
	private int isroot;
	
}
