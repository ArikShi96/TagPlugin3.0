package IOperation;

import java.util.List;

import Model.*;
import Model.Class;

public interface IFuncTagOperation {
	/**
	 * basic operation
	 */
	public List<FunctionalTag> getAll();
	public FunctionalTag getByID(int id);
	public void insert(FunctionalTag func_tag);
	public void update(FunctionalTag func_tag);
	public void delete(int id);
	
	/**
	 * extra operation
	 */
	
	public List<Class> getClassByTagType(String type,int start,int end);
	
}