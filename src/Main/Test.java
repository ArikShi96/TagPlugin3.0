package Main;

import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import Controller.FetchController;
import Controller.MethodVisitor;
import Controller.TagInitController;
import IOperation.IClassOperation;
import Model.Class;

public class Test {

	public static void main(String[]args) {
		// TODO Auto-generated method stub
		try {
			String resource = "Conf.xml";
			Reader is = Resources.getResourceAsReader(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
			SqlSession session = sessionFactory.openSession();

			TagInitController tcontroller = new TagInitController();
			FetchController fcontroller = new FetchController();
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.println("running");
			while (true) {
				String cmd = scanner.nextLine();
				String cont = scanner.nextLine();
				if (cmd.equals("init")) {
					if (tcontroller.initProject(cont, session)) {
						System.out.println("Done!");
					} else {
						System.out.println("Failed!");
					}
				} else if (cmd.equals("Get")) {
					for (Class cl : fcontroller.getClassByType(cont, 1, 2, session)) {
						System.out.println(cl.getName());
						System.out.println(cl.getId());
						System.out.println(cl.getContent());
					}
				} else if (cmd.equals("method")) {
					IClassOperation coperation = session.getMapper(IClassOperation.class);
					Class cl = coperation.getByID(Integer.parseInt(cont));

					@SuppressWarnings("deprecation")
					ASTParser parser = ASTParser.newParser(AST.JLS3);
					parser.setSource(cl.getContent().toCharArray());
					parser.setKind(ASTParser.K_COMPILATION_UNIT);
					final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
					MethodVisitor visitor = new MethodVisitor();
					cu.accept(visitor);
				} else if (cmd.equals("quit")) {
					break;
				}
			}
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}