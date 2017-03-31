package Model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import IOperation.IImoprt_InfoOperation;

public class Import_Info {

	private int id;
	private String keyword;
	private int tag_id;
	
	public Import_Info() {
		
	}
	
	public void  setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getTag_id() {
		return tag_id;
	}
	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}
	
	private static List<Import_Info> imps=null;
	
	public static List<Import_Info> getImport_Infos(SqlSession session){
		if(imps==null){
			IImoprt_InfoOperation operation=session.getMapper(IImoprt_InfoOperation.class);
			imps=operation.getAll();
		}
		return imps;
	}
}
