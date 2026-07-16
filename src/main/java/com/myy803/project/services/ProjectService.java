package com.myy803.project.services;

import java.util.List;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.User;

public interface ProjectService {
	
	List<Project> findByOwner(User owner);
	
	void saveProject(Project project);
	
	void deleteProject(int id);
	
	Project findById(int id);
	
}
