
package Controller;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import IOperation.*;
import Model.Class;

public class FetchController {
	public FetchController() {

	}

	public static List<Class> getClassByType(String type, int pageCount, int pageSize, SqlSession session) {
		IFuncTagOperation operation = session.getMapper(IFuncTagOperation.class);
		// 将页号与页面大小转换为始末位置，调用functionalTag的操作，返回特定页面的数据
		List<Class> result = operation.getClassByTagType(type, (pageCount - 1) * pageSize, pageSize);
		return result;
	}

	public static List<Class> getTagsByPage(int pageCount, int pageSize, SqlSession session) {
		IClassOperation operation = session.getMapper(IClassOperation.class);
		List<Class> result = operation.getClassByPage((pageCount - 1) * pageSize, pageSize);
		// 将页号与页面大小转换为始末位置，调用functionalTag的操作，返回特定页面的数据
		return result;
	}
}
