
package IOperation;

import java.util.List;

import Model.*;
public interface IClass_TagOperation {
	
	/**
	 * basic operation
	 */
	public List<Class_Tag> getAll();
	public List<Class_Tag> getByTagId(int id);
	public List<Class_Tag> getByClassId(int id);
	public void insert(Class_Tag class_tag);
	public void deleteByTagId(int id);
	public void deleteByClassId(int id);
	
	/**
	 * extra
	 */
	public List<Project> getClassByTagId(int id);
}