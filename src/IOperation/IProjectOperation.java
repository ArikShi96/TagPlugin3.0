package IOperation;

import java.util.List;

import Model.Project;

public interface IProjectOperation {
	public List<Project> getAll();
	public Project getByID(int id);
	public void insert(Project project);
	public void update(Project project);
	public void delete(int id);
}