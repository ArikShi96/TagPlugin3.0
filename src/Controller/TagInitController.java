package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import IOperation.IClassOperation;
import IOperation.IClass_TagOperation;
import IOperation.IPackageOperation;
import IOperation.ISourceFileOperation;
import IOperation.ITMethodOperation;
import IOperation.ITMethod_InvokeOperation;
import Model.Package;
import Model.SourceFile;
import Model.TInvocation;
import Model.TMethod;
import Model.TType;
import Model.Class;
import Model.Class_Tag;
import Model.Import_Info;
import Model.Method_Invoke;

public class TagInitController {
	private datacache datacache = new datacache();
	ArrayList<Class> ClassList = new ArrayList<>();
	ArrayList<TMethod> MethodList = new ArrayList<>();
	ArrayList<TInvocation> InvocationList = new ArrayList<>();

	private void init() {
		datacache.init();
		ClassList.clear();
		// MethodList.clear();
		InvocationList.clear();
	}

	public boolean initProject(String path, SqlSession session) {
		init();
		long bg = System.currentTimeMillis();
		boolean val = false;
		try {
			File dir = new File(path);
			Package project = new Package();
			project.setName(path);
			project.setIsroot(1);
			project.setparent_id(0);
			IPackageOperation operation = session.getMapper(IPackageOperation.class);
			operation.insert(project);

			int id = project.getId();
			File[] childfiles = dir.listFiles();
			for (File elem : childfiles) {
				if ((elem.isDirectory() && initPackage(elem, id, session))
						|| (elem.isFile() && elem.getName().endsWith(".java") && initSource(elem, id, session)))
					val = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!val) {
			session.rollback();
			return false;
		}
		long state1 = System.currentTimeMillis();
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 以上是第一轮预处理，接下来是第二轮进行类、方法之间调用的分析
		if (!initMethodInvokes(session)) {
			return false;
		}
		session.commit();
		long state2 = System.currentTimeMillis();
		System.out.println("state1: " + (state1 - bg));
		System.out.println("state2: " + (state2 - state1));

		return true;
	}

	public boolean initPackage(File packfile, int projectid, SqlSession session) {
		boolean val = false;
		try {
			Package pack = new Package();
			pack.setName(packfile.getName());
			pack.setIsroot(0);
			pack.setparent_id(projectid);
			IPackageOperation operation = session.getMapper(IPackageOperation.class);
			operation.insert(pack);

			int id = pack.getId();
			File[] childfiles = packfile.listFiles();
			for (File elem : childfiles) {
				if ((elem.isDirectory() && initPackage(elem, id, session))
						|| (elem.isFile() && elem.getName().endsWith(".java") && initSource(elem, id, session))) {
					val = true;
				}
			}
			if (!val) {
				operation.delete(id);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean initSource(File source, int packageid, SqlSession session) {
		boolean val = false;
		try {
			SourceFile sour_file = new SourceFile();
			sour_file.setName(source.getName());
			sour_file.setPackage_id(packageid);
			ISourceFileOperation operation = session.getMapper(ISourceFileOperation.class);
			operation.insert(sour_file);

			List<String> get_imp_infos = ClassBuilderControler.getImport(source);
			List<String> get_class_infos = ClassBuilderControler.getClassInfo(source);

			for (String str : get_class_infos) {
				ArrayList<String> extraInfos = ClassBuilderControler.getClassExtraInfo(str);
				if (initClass(str, sour_file.getId(), extraInfos, session, get_imp_infos))
					val = true;
			}
			if (!val) {
				operation.delete(sour_file.getId());
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean initClass(String cla, int sourceid, ArrayList<String> extraInfos, SqlSession session,
			List<String> get_imp_infos) {
		boolean val = false;
		Class cl = new Class();
		cl.setName(extraInfos.get(0));
		if (extraInfos.get(1).equals("interface")) {
			cl.setCtype(1); // 设置1为接口的标识
		}
		cl.setSuper_name(extraInfos.get(2));// 这里可以获得继承的类的名字，但是获得id有点困难
		cl.setFile_id(sourceid);
		cl.setContent(cla);
		IClassOperation operation = session.getMapper(IClassOperation.class);
		operation.insert(cl);
		datacache.insertClass(cl);
		ClassList.add(cl);

		ASTParserController astController = new ASTParserController(cla);
		// 以下是调用初始化方法的函数
		ArrayList<String> contentList = astController.getContentList();
		ArrayList<String> nameList = astController.getMethodNameList();
		for (int i = 0; i < nameList.size(); i++) {
			if (initMethod(contentList.get(i), nameList.get(i), cl.getId(), session)) {
				val = true;
			}
		}
		if (!val) {
			operation.delete(cl.getId());
			return true;
		}

		// 类变量设置
		ArrayList<String> fieldnamelist = astController.getFieldNameList();
		ArrayList<String> fieldtypelist = astController.getFieldTypeList();
		for (int i = 0; i < fieldnamelist.size(); i++) {
			cl.addClassMember(fieldnamelist.get(i), fieldtypelist.get(i));
		}

		// 接下来 是import的包与预先定义的包进行比较，加上class-level tag
		List<Import_Info> import_Infos = Import_Info.getImport_Infos(session);
		Set<Integer> tagidset = new HashSet<>();
		for (String get_imp_info : get_imp_infos) {
			for (Import_Info import_Info : import_Infos) {
				if (parseImport(get_imp_info, import_Info.getKeyword())) {
					tagidset.add(import_Info.getTag_id());
				}
			}
		}
		IClass_TagOperation tagOperation = session.getMapper(IClass_TagOperation.class);
		for (int i : tagidset) {
			Class_Tag class_tag = new Class_Tag();
			class_tag.setClass_id(cl.getId());
			class_tag.setFunc_tag_id(i);
			tagOperation.insert(class_tag);
		}
		return true;
	}

	public boolean initMethod(String methodDeclaration, String methodName, int class_id, SqlSession session) {
		TMethod method = new TMethod();
		method.setOwner(class_id);
		method.setContent(methodDeclaration);
		method.setName(methodName);
		MethodList.add(method);
		ITMethodOperation operation = session.getMapper(ITMethodOperation.class);
		operation.insert(method);
		datacache.insertMethod(method);
		/////////////////////////////// 我是分割线//////////////////////////////////////////////////////////////////////////////////
		methodDeclaration = "public class test { \n" + methodDeclaration + "}";
		ASTParserController astController = new ASTParserController(methodDeclaration);
		// initial variable list
		ArrayList<String> variableList = astController.getVariableList();
		for (String variable : variableList) {
			String[] tmpString = variable.split("\\s+");
			String name = "";
			String ntype = tmpString[0];
			if (tmpString.length >= 2) {
				if (tmpString[1].contains("=")) {
					tmpString = tmpString[1].split("=");
					name = tmpString[0];
				} else if (tmpString[1].contains(";")) {
					name = tmpString[1].substring(0, tmpString[1].length() - 1);
				}
				method.addVariable(name, ntype);
			}
		}
		// initial parameter list
		ArrayList<String> statementList = astController.getParameterList();
		for (String statement : statementList) {
			String[] tmpString = statement.split("\\s+");
			if (tmpString.length >= 2) {
				method.addVariable(tmpString[0], tmpString[1]);
			}
		}
		// initial invocation list
		ArrayList<String> TypeList = astController.getInvokacionTypeList();
		ArrayList<String> NameList = astController.getInvokacionNameList();
		try {
			for (int i = 0; i < NameList.size(); i++) {
				InvocationList.add(new TInvocation(method.getId(), TypeList.get(i), NameList.get(i)));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}

	public boolean initClassImplement(SqlSession session) {
		for (Class cl : ClassList) {
			int super_id = datacache.containClass(cl.getSuper_name());
			if (super_id > 0) {
				cl.setSuper_id(super_id);
			}
		}
		return true;
	}

	public boolean initMethodInvokes(SqlSession session) {
		removeRedundancy();
		ITMethod_InvokeOperation operation = session.getMapper(ITMethod_InvokeOperation.class);
		for (TInvocation type : InvocationList) {
			Method_Invoke newItem = new Method_Invoke();
			int func_owner_id = -1;
			String func_owner = type.getFunc_owner();
			String func_name = type.getFunc_name();
			newItem.setInvoker_id(type.getCaller_id());
			// 遍历方法变量
			TMethod caller = datacache.geTMethodById(type.getCaller_id());
			HashMap<String, TType> variables = caller.getVariables();
			if (variables.containsKey(func_owner)) {
				func_owner_id = variables.get(func_owner).getClass_id();
			}
			// 若方法变量中不存在该变量，则遍历类变量
			if (func_owner_id < 0) {
				Class caller_owner = datacache.getClassById(caller.getOwner());
				HashMap<String, TType> members = caller_owner.getMembers();
				if (members.containsKey(func_owner)) {
					func_owner_id = members.get(func_owner).getClass_id();
				}
			}
			// 若类变量中不存在该变量，则遍历类名
			if (func_owner_id < 0) {
				func_owner_id = datacache.containClass(func_owner);
			}
			// 若该变量由本项目定义，则查询是否有该函数
			if (func_owner_id >= 0) {
				int method_id = datacache.containMethod(func_name, func_owner_id);
				if (method_id >= 0) {
					newItem.setMethod_id(method_id);
					operation.insert(newItem);
				}
			}
		}
		return true;
	}

	private void removeRedundancy() {
		for (Class cl : ClassList) {
			HashMap<String, TType> ttypes = cl.getMembers();
			for (Iterator<Map.Entry<String, TType>> it = ttypes.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, TType> item = it.next();
				int val = datacache.containClass(item.getValue().getClass_name());
				if (val >= 0) {
					item.getValue().setClass_id(val);
				} else {
					it.remove();
				}
			}
		}
		for (TMethod method : MethodList) {
			HashMap<String, TType> ttypes = method.getVariables();
			for (Iterator<Map.Entry<String, TType>> it = ttypes.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, TType> item = it.next();
				int val = datacache.containClass(item.getValue().getClass_name());
				if (val >= 0) {
					item.getValue().setClass_id(val);
				} else {
					it.remove();
				}
			}
		}
	}

	private static boolean parseImport(String item, String keyword) {
		if (keyword.contains("*") && item.contains(keyword.substring(0, keyword.length() - 2))) {
			return true;
		} else if (item.equals(keyword)) {
			return true;
		}
		return false;
	}

	static int min(int a, int b) {
		return a < b ? a : b;
	}
}

class datacache {
	private static final int Total = 1000;
	private static final int Count = 20;

	private TMethod[] methods = new TMethod[Total];
	private HashMap<Integer, TMethod> id_mtd_map = new HashMap<>();

	private HashMap<String, Class> str_cl_map = new HashMap<>();
	private HashMap<Integer, Class> id_cl_map = new HashMap<>();

	public void init() {
		for (int i = 0; i < Total; i++) {
			methods[i] = null;
		}
		id_mtd_map.clear();
		str_cl_map.clear();
		id_cl_map.clear();
	}

	public boolean insertMethod(TMethod tmethod) {
		id_mtd_map.put(tmethod.getId(), tmethod);
		int index = tmethod.getName().hashCode();
		while (index < 0) {
			index += Total;
		}
		int pos = 0;
		while (methods[(index + pos * pos) % Total] != null && pos <= Count) {
			pos += 1;
		}
		if (methods[(index + pos * pos) % Total] == null) {
			methods[(index + pos * pos) % Total] = tmethod;
			return true;
		}
		return false;
	}

	public void insertClass(Class cla) {
		str_cl_map.put(cla.getName(), cla);
		id_cl_map.put(cla.getId(), cla);
	}

	public int containClass(String name) {
		if (str_cl_map.containsKey(name)) {
			return str_cl_map.get(name).getId();
		}
		return -1;
	}

	public int containMethod(String name, int class_id) {
		int index = name.hashCode();
		while (index < 0) {
			index += Total;
		}
		int pos = 0;
		TMethod tm = methods[(index + pos * pos) % Total];
		while (tm != null && pos <= 30 && tm.getName().equals(name)) {
			if (tm.getOwner() == class_id) {
				return tm.getId();
			}
			pos += 1;
			tm = methods[(index + pos * pos) % Total];
		}
		return -1;
	}

	public TMethod geTMethodById(int id) {
		return id_mtd_map.get(id);
	}

	public Class getClassById(int id) {
		return id_cl_map.get(id);
	}
}