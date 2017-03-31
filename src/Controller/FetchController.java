
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
		// ��ҳ����ҳ���Сת��Ϊʼĩλ�ã�����functionalTag�Ĳ����������ض�ҳ�������
		List<Class> result = operation.getClassByTagType(type, (pageCount - 1) * pageSize, pageSize);
		return result;
	}

	public static List<Class> getTagsByPage(int pageCount, int pageSize, SqlSession session) {
		IClassOperation operation = session.getMapper(IClassOperation.class);
		List<Class> result = operation.getClassByPage((pageCount - 1) * pageSize, pageSize);
		// ��ҳ����ҳ���Сת��Ϊʼĩλ�ã�����functionalTag�Ĳ����������ض�ҳ�������
		return result;
	}
}
