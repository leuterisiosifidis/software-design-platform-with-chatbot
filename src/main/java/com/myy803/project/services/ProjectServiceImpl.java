package com.myy803.project.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.User;
import com.myy803.project.repositories.UseCaseRepository;
import com.myy803.project.repositories.ProjectRepository;
import com.myy803.project.repositories.CrcCardRepository;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UseCaseRepository useCaseRepository;
	
	@Autowired
	private CrcCardRepository crcCardRepository;
	
	@Override
	public List<Project> findByOwner(User owner) {
		return projectRepository.findByOwner(owner);
	}
	
	@Override
	public void saveProject(Project project) {
		projectRepository.save(project);
	}
	
	@Override
	public void deleteProject(int id) {
		Project project = projectRepository.findById(id).orElse(null);
		if (project != null) {    												   //if project is not empty
			crcCardRepository.deleteAll(crcCardRepository.findByProject(project));
			useCaseRepository.deleteAll(useCaseRepository.findByProject(project)); //try to delete all UseCases (to solve key problems)
		}
		projectRepository.deleteById(id);
	}
	
	@Override
	public Project findById(int id) {
		return projectRepository.findById(id).orElse(null);
	}
}
