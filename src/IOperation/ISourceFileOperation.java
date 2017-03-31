package IOperation;

import java.util.List;

import Model.SourceFile;;

public interface ISourceFileOperation {
	
	/**
	 * basic operation
	 * CRUD
	 */
	public List<SourceFile> getAll();
	public SourceFile getByID(int id);
	public void insert(SourceFile sourcefile);
	public void update(SourceFile sourcefile);
	public void delete(int id);
	
	/**
	 * extra operation
	 */
	
}
