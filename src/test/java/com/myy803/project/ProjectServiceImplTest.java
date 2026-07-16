package com.myy803.project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import com.myy803.project.domain.User;
import com.myy803.project.repositories.ProjectRepository;
import com.myy803.project.repositories.UseCaseRepository;
import com.myy803.project.services.ProjectServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {
	
	@Mock
	private ProjectRepository projectRepository;
	
	@Mock
	private UseCaseRepository useCaseRepository;
	
	@InjectMocks
	private ProjectServiceImpl projectService;
	
	@Test
	public void findByOwner_ShouldReturnProjectList() {
		User owner = new User();
		Project p1 = new Project();
		Project p2 = new Project();
		List<Project> projects = Arrays.asList(p1, p2);
		
		when(projectRepository.findByOwner(owner)).thenReturn(projects);
		
		List<Project> result = projectService.findByOwner(owner);
		
		Assertions.assertEquals(2, result.size());
		verify(projectRepository).findByOwner(owner);
	}
	
	@Test
	public void saveProject_ShouldCallRepositorySave() {
		Project project = new Project();
		
		projectService.saveProject(project);
		
		verify(projectRepository).save(project);
	}
	
	@Test
	public void deleteProject_ShouldCascadeDeleteUseCasesAndProject() {
		Project project = new Project();
		List<UseCase> useCases = Arrays.asList(new UseCase(), new UseCase());
		
		when(projectRepository.findById(1)).thenReturn(Optional.of(project));
		when(useCaseRepository.findByProject(project)).thenReturn(useCases);
		
		projectService.deleteProject(1);
		
		verify(useCaseRepository).deleteAll(useCases);
		verify(projectRepository).deleteById(1);
	}
	
	@Test
	public void deleteProject_WhenProjectNotFound_ShouldStillCallDeleteById() {
		when(projectRepository.findById(1)).thenReturn(Optional.empty());
		
		projectService.deleteProject(1);
		
		verify(useCaseRepository, never()).deleteAll(any());
		verify(projectRepository).deleteById(1);
	}
	
	@Test
	public void findById_ShouldReturnProject() {
		Project project = new Project();
		
		when(projectRepository.findById(1)).thenReturn(Optional.of(project));
		
		Project result = projectService.findById(1);
		
		Assertions.assertNotNull(result);
		verify(projectRepository).findById(1);
	}
	
	@Test
	public void findById_WhenNotFound_ShouldReturnNull() {
		when(projectRepository.findById(1)).thenReturn(Optional.empty());
		
		Project result = projectService.findById(1);
		
		Assertions.assertNull(result);
		verify(projectRepository).findById(1);
	}
}
