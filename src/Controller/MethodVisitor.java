package Controller;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.*;

public class MethodVisitor extends ASTVisitor {
	ArrayList<MethodDeclaration> methodList = new ArrayList<>();
	ArrayList<FieldAccess> blockList = new ArrayList<>();
	ArrayList<MethodInvocation> invokeList = new ArrayList<>();
	ArrayList<ClassInstanceCreation> creationList = new ArrayList<>();
	ArrayList<VariableDeclarationStatement> variableStatementList = new ArrayList<>();
	ArrayList<SingleVariableDeclaration> variableList = new ArrayList<>();
	ArrayList<FieldDeclaration> fieldList = new ArrayList<>();
	
	ArrayList<VariableDeclarationFragment> testList = new ArrayList<>();

	public MethodVisitor() {
		// TODO Auto-generated creationListtructor stub
	}

	@Override
	// 获得方法的声明以及内容
	public boolean visit(MethodDeclaration node) {
		// TODO Auto-generated method stub
		methodList.add(node);
		return super.visit(node);
	}

	// 具体内容
	@Override
	public boolean visit(FieldAccess node) {
		// TODO Auto-generated method stub
		blockList.add(node);
		return super.visit(node);
	}

	// 方法调用
	@Override
	public boolean visit(MethodInvocation node) {
		invokeList.add(node);
		return super.visit(node);

	}

	// 构造方法方法调用
	@Override
	public boolean visit(ClassInstanceCreation node) {
		creationList.add(node);
		return super.visit(node);

	}

	// 返回方法中声明的变量
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		variableStatementList.add(node);
		return super.visit(node);
	}

	// 获得类中声明的变量
	@Override
	public boolean visit(FieldDeclaration node) {
		// TODO Auto-generated method stub
		fieldList.add(node);
		return super.visit(node);
	}

	// 获得方法的参数列表
	@Override
	public boolean visit(SingleVariableDeclaration node) {
		// TODO Auto-generated method stub
		
		variableList.add(node);
		return super.visit(node);
	}
	
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		// TODO Auto-generated method stub
		testList.add(node);
		return super.visit(node);
	}
	/**
	 * 
	 * @return返回方法的所有声明
	 */
	public ArrayList<MethodDeclaration> getMethodList() {
		return methodList;
	}

	public ArrayList<FieldAccess> getBlockList() {
		return blockList;
	}

	/**
	 * 
	 * @return返回方法中函数调用的表达式
	 */
	public ArrayList<MethodInvocation> getInvokeList() {
		return invokeList;
	}

	/**
	 * 
	 * @return返回方法中构造函数的表达
	 */
	public ArrayList<ClassInstanceCreation> getCreationList() {
		return creationList;
	}

	/**
	 * 
	 * @return类的私有域
	 */
	public ArrayList<FieldDeclaration> getFieldList() {
		return fieldList;
	}

	/**
	 * 
	 * @return函数声明的变量
	 */
	public ArrayList<VariableDeclarationStatement> getVariableList() {
		return variableStatementList;
	}

	/**
	 * 
	 * @return函数的参数列表
	 */
	public ArrayList<SingleVariableDeclaration> getParameterList() {
		return variableList;
	}
	
	public ArrayList<VariableDeclarationFragment> getTestList() {
		return testList;
	}
}