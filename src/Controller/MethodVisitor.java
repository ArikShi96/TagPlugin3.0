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
	// ��÷����������Լ�����
	public boolean visit(MethodDeclaration node) {
		// TODO Auto-generated method stub
		methodList.add(node);
		return super.visit(node);
	}

	// ��������
	@Override
	public boolean visit(FieldAccess node) {
		// TODO Auto-generated method stub
		blockList.add(node);
		return super.visit(node);
	}

	// ��������
	@Override
	public boolean visit(MethodInvocation node) {
		invokeList.add(node);
		return super.visit(node);

	}

	// ���췽����������
	@Override
	public boolean visit(ClassInstanceCreation node) {
		creationList.add(node);
		return super.visit(node);

	}

	// ���ط����������ı���
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		variableStatementList.add(node);
		return super.visit(node);
	}

	// ������������ı���
	@Override
	public boolean visit(FieldDeclaration node) {
		// TODO Auto-generated method stub
		fieldList.add(node);
		return super.visit(node);
	}

	// ��÷����Ĳ����б�
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
	 * @return���ط�������������
	 */
	public ArrayList<MethodDeclaration> getMethodList() {
		return methodList;
	}

	public ArrayList<FieldAccess> getBlockList() {
		return blockList;
	}

	/**
	 * 
	 * @return���ط����к������õı��ʽ
	 */
	public ArrayList<MethodInvocation> getInvokeList() {
		return invokeList;
	}

	/**
	 * 
	 * @return���ط����й��캯���ı��
	 */
	public ArrayList<ClassInstanceCreation> getCreationList() {
		return creationList;
	}

	/**
	 * 
	 * @return���˽����
	 */
	public ArrayList<FieldDeclaration> getFieldList() {
		return fieldList;
	}

	/**
	 * 
	 * @return���������ı���
	 */
	public ArrayList<VariableDeclarationStatement> getVariableList() {
		return variableStatementList;
	}

	/**
	 * 
	 * @return�����Ĳ����б�
	 */
	public ArrayList<SingleVariableDeclaration> getParameterList() {
		return variableList;
	}
	
	public ArrayList<VariableDeclarationFragment> getTestList() {
		return testList;
	}
}