package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClassBuilderControler {

	// ��ȡһ��.Java�ļ�������import����Ϣ;
	public static List<String> getImport(File file) throws IOException {

		FileReader fd = new FileReader(file);
		BufferedReader bd = new BufferedReader(fd);

		List<String> import_infos = new ArrayList<>();
		String content = null;
		boolean status = false;

		while ((content = bd.readLine()) != null) {
			content = content.replace(" ", "");
			if (content.length() > 6 && content.substring(0, 6).equals("import")) {
				status = true;
				content = content.substring(6, content.length() - 1);
				import_infos.add(content);
			} else if (status) {
				break;
			}
		}
		fd.close();
		bd.close();
		return import_infos;
	}

	public static List<String> getClassInfo(File file) throws IOException {

		FileReader fd = new FileReader(file);
		BufferedReader bd = new BufferedReader(fd);

		Stack<Character> sign_table = new Stack<>();
		List<String> class_infos = new ArrayList<>();
		StringBuilder result = new StringBuilder();
		String content = null;

		while ((content = bd.readLine()) != null) {
			// һ��������;

			if (isClassBegin(content)) {

				result.append(content);
				result.append("\n");
				sign_table.push('{');

				while (!sign_table.isEmpty() && (content = bd.readLine()) != null) {
					if (matchSign(content, sign_table)) {
						result.append(content);
						result.append("\n");
					} else {
						result.delete(0, result.length());
						break;
					}
				}
				class_infos.add(result.toString());
				// System.out.println(getClassName(result.toString().substring(0,
				// 64)));
				sign_table.clear();
				result.delete(0, result.length());
			}
		}
		fd.close();
		bd.close();
		return class_infos;
	}

	// ���һ�д����Ƿ�Ϊ�ļ��� һ���� �Ŀ�ͷ
	private static boolean isClassBegin(String content) {

		// ��ֹ�����м�������ĳ���
		String hdlcont = content.replace(" ", "");
		int len = hdlcont.length();
		if (hdlcont.substring(0, min(9, len)).equals("interface")
				|| hdlcont.substring(0, min(5, len)).equals("class")) {
			return true;
		}
		if (hdlcont.substring(0, min(15, len)).equals("publicinterface")
				|| hdlcont.substring(0, min(11, len)).equals("publicclass")) {
			return true;
		}
		return false;
	}

	// ������ƥ�䣬��Ҫ�Ǹ�������
	private static boolean matchSign(String class_info, Stack<Character> sign_table) {
		char[] fileStream = class_info.toCharArray();
		for (char ch : fileStream) {
			if (ch == '{' || ch == '[' || ch == '(') {
				sign_table.push(ch);
			} else if (ch == '}' || ch == ']' || ch == ')') {
				if (sign_table.isEmpty())
					return false;
				int delta = ch - sign_table.pop();
				if (delta != 1 && delta != 2) {
					return false;
				}
			}
		}
		return true;
	}

	public static ArrayList<String> getClassExtraInfo(String content) { // ���������֣�����id(Ĭ��Ϊobject),type
		ArrayList<String> extraInfos = new ArrayList<>();
		String class_name = "default";
		String super_name = "object";
		String type_name = "class";
		String[] strArr = content.split("\\s+");
		for (int i = 0; i < strArr.length; i++) {
			String str = strArr[i];
			if (str.equals("interface")) {
				class_name = strArr[i + 1];
				super_name = "object";
				type_name = "interface";
				break;
			}
			if (str.equals("class")) {
				class_name = strArr[i + 1];
				if (strArr[i + 2].equals("{") || class_name.contains("{"))
					break;
				continue;
			}
			if (str.equals("extends")) {
				super_name = strArr[i + 1];
				break;
			}
		}
		if (class_name.contains("{"))
			class_name = class_name.substring(0, class_name.length() - 1);
		extraInfos.add(class_name);
		extraInfos.add(type_name);
		extraInfos.add(super_name);
		return extraInfos;
	}

	static int min(int a, int b) {
		return a < b ? a : b;
	}
}