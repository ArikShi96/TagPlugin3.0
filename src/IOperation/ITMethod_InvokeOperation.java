package IOperation;

import java.util.List;

import Model.Method_Invoke;

public interface ITMethod_InvokeOperation {
	/**
	 * basic operation
	 * CRUD
	 */
	public List<Method_Invoke> getAll();
	public Method_Invoke getByID(int id);
	public void insert(Method_Invoke mi);
	public void update(Method_Invoke mi);
	public void delete(int id);
	/**
	 * extra operation
	 */
}
