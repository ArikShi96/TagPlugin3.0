package IOperation;

import java.util.List;

import Model.Implementation;

public interface IInplementationOperation {
	/**
	 * basic operation
	 * CRUD
	 */
	public List<Implementation> getAll();
	public Implementation getByID(int id);
	public void insert(Implementation inpl);
	public void update(Implementation inpl);
	public void delete(int id);
	
	/**
	 * extra operation
	 */
}
