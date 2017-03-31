package IOperation;

import java.util.List;

import Model.TMethod;

public interface ITMethodOperation {
	/**
	 * basic operation
	 * CRUD
	 */
	public List<TMethod> getAll();
	public TMethod getByID(int id);
	public void insert(TMethod tmethod);
	public void update(TMethod tmethod);
	public void delete(int id);
	
	/**
	 * extra operation
	 */
}
