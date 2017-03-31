package IOperation;

import java.util.List;

import Model.Import_Info;

public interface IImoprt_InfoOperation{
	/**
	 * basic operation
	 */
	public List<Import_Info> getAll();
	public Import_Info getByID(int id);
	public void insert(Import_Info imp);
	public void update(Import_Info imp);
	public void delete(int id);
}