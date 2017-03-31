package Model;

public class Project {

	public Project() {
		
	}
	private int id;
	private String name;
	private String source;
	
	public int getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
}
