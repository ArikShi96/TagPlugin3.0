package Model;

import java.util.HashMap;

public class TMethod {

	public TMethod() {
		// TODO Auto-generated constructor stub
	}
	private int id;
	private String name;
	private int owner;
	private String content;
	
	private HashMap<String, TType> ttype_map=new HashMap<>();
	public int getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void addVariable(String n, String ntype){
		TType ttype=new TType(n, ntype);
		ttype_map.put(n, ttype);
	}
	
	public HashMap<String, TType> getVariables(){
		return ttype_map;
	}

}
