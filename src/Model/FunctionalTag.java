package Model;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import IOperation.IFuncTagOperation;

public class FunctionalTag {
	private int id;
	private String type;
	
	public int getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	private static List<FunctionalTag> tags=null;
	
	public static List<FunctionalTag> getFunctionalTags(SqlSession session){
		if(tags==null){
			IFuncTagOperation operation=session.getMapper(IFuncTagOperation.class);
			tags=operation.getAll();
		}
		return tags;
	}
}