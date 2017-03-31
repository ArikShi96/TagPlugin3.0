package IOperation;

import java.util.List;

import Model.Class;

public interface IClassOperation{
	/**
	 * basic operation
	 */
	public List<Class> getAll();
	public Class getByID(int id);
	public void insert(Class cla);
	public void update(Class cla);
	public void delete(int id);
	
	/**
	 * extra operation
	 */
	public List<Class>  getClassByPage(int start,int end);
}