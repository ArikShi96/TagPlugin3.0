package Controller;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class ASTParserController {

	private ASTParser parser;
	private MethodVisitor visitor;

	@SuppressWarnings("deprecation")
	public ASTParserController(String str) {
		parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		visitor = new MethodVisitor();
		cu.accept(visitor);
	}

	/**
	 * 
	 * @return获得类中所有方法的声明
	 */
	public ArrayList<String> getContentList() {
		ArrayList<String> contentList = new ArrayList<>();
		ArrayList<MethodDeclaration> methodList = visitor.getMethodList();
		for (MethodDeclaration elem : methodList) {
			contentList.add(elem.toString());
			// break;
		}
		return contentList;
	}

	/**
	 * 
	 * @return获得类中所有方法的调用的变量名
	 */
	public ArrayList<String> getInvokacionTypeList() {
		ArrayList<String> invokeList = new ArrayList<>();
		ArrayList<MethodInvocation> methodList = visitor.getInvokeList();
		
		try {
			for (MethodInvocation elem : methodList) {
				invokeList.add(elem.getExpression().toString());
			}
		} 
		catch (Exception e) {
		}
		return invokeList;
	}
	
	/**
	 * 
	 * @return获得类中所有方法的调用的方法名
	 */
	public ArrayList<String> getInvokacionNameList() {
		ArrayList<String> invokeList = new ArrayList<>();
		ArrayList<MethodInvocation> methodList = visitor.getInvokeList();
		try {
			for (MethodInvocation elem : methodList) {
				invokeList.add(elem.getName().toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return invokeList;
	}

	/**
	 * 
	 * @return获得类中所有方法的名字
	 */
	public ArrayList<String> getMethodNameList() {
		ArrayList<String> nameList = new ArrayList<>();
		ArrayList<MethodDeclaration> methodList = visitor.getMethodList();
		for (MethodDeclaration elem : methodList) {
			nameList.add(elem.getName().toString());
			// break;
		}
		return nameList;
	}
	public ArrayList<String> getFieldTypeList() {
		ArrayList<String> feildList = new ArrayList<>();
		ArrayList<FieldDeclaration> methodList = visitor.getFieldList();
		for (FieldDeclaration elem : methodList) {
			feildList.add(elem.getType().toString());
			// break;
		}
		return feildList;
	}
	/**
	 * 
	 * @return获得类中声明的私有值的名字
	 */
	public ArrayList<String> getFieldNameList() {
		ArrayList<String> feildList = new ArrayList<>();
		ArrayList<FieldDeclaration> methodList = visitor.getFieldList();
		for (FieldDeclaration elem : methodList) {
			String str = elem.toString();
			String[] tmp = str.split("=");
			String[] tmpString = tmp[0].split("\\s+");
			str = tmpString[tmpString.length - 1];
			if(str.contains(";")){
				str = str.substring(0, str.length()-1);
			}
			feildList.add(str);
			// break;
		}
		return feildList;
	}
	/**
	 * 
	 * @return参数列表
	 */
	public ArrayList<String> getParameterList(){
		ArrayList<String> variableList = new ArrayList<>();
		ArrayList<SingleVariableDeclaration> methodList = visitor.getParameterList();
		for (SingleVariableDeclaration elem : methodList) {
			variableList.add(elem.toString());
			// break;
		}
		return variableList;
	}
	
	/**
	 * 
	 * @return声明的变量
	 */
	public ArrayList<String> getVariableList(){
		ArrayList<String> StatementList = new ArrayList<>();
		ArrayList<VariableDeclarationStatement> methodList = visitor.getVariableList();
		for (VariableDeclarationStatement elem : methodList) {
			StatementList.add(elem.toString());
			// break;
		}
		return StatementList;
	}
}