package Model;

import java.util.HashMap;

public class Class {

	public Class() {

	}
	private int id;
	private String name;
	private String language = "Java";
	private int file_id;
	private String content;
	private int super_id = 0;
	private String super_name = "object";
	private int ctype = 0; // 这里规定0位类，1为接口
	
	private HashMap<String, TType> ttype_map=new HashMap<>();
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLangusge(String language) {
		this.language = language;
	}

	public int getFile_id() {
		return file_id;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSuper_id() {
		return super_id;
	}

	public void setSuper_id(int super_id) {
		this.super_id = super_id;
	}

	public int getCtype() {
		return ctype;
	}

	public void setCtype(int ctype) {
		this.ctype = ctype;
	}

	public String getSuper_name() {
		return super_name;
	}

	public void setSuper_name(String super_name) {
		this.super_name = super_name;
	}
	
	public void addClassMember(String n, String ntype){
		TType ttype=new TType(n, ntype);
		ttype_map.put(n, ttype);
	}
	
	public HashMap<String, TType> getMembers(){
		return ttype_map;
	}
}