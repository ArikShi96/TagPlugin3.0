package IOperation;

import java.util.List;

import Model.Package;

public interface IPackageOperation {
	/**
	 * basic operation
	 * CRUD
	 */
	public List<Package> getAll();
	public Package getByID(int id);
	public void insert(Package pack);
	public void update(Package pack);
	public void delete(int id);
	/**
	 * extra operation
	 */
	
}
