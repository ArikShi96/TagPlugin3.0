package Controller;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBConn {

	private static SqlSessionFactory sessionFactory=null;
	public static SqlSessionFactory getInstance(){
		if(sessionFactory==null){
			try{
			String resource = "Conf.xml";
	        Reader is = Resources.getResourceAsReader(resource);
	        sessionFactory = new SqlSessionFactoryBuilder().build(is);
	        }catch(IOException e){
	        	e.printStackTrace();
	        }
		}
		return sessionFactory;
	}
}
