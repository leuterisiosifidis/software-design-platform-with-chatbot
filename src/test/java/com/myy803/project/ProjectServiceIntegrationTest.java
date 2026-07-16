package com.myy803.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.myy803.project.domain.Project;
import com.myy803.project.domain.User;
import com.myy803.project.services.ProjectService;
import com.myy803.project.services.UserService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProjectServiceIntegrationTest {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void saveProject_WhenSaved_ShouldBeRetrievableById() {
		User user = new User();
		user.setUsername("testuser");
		user.setPassword("password");
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEmail("john@test.com");
		userService.saveUser(user);
		
		User savedUser = userService.findByUsername("testuser");
		
		Project project = new Project();
		project.setName("Test Project");
		project.setDescription("A test project");
		project.setOwner(savedUser);
		projectService.saveProject(project);
		
		Project found = projectService.findById(project.getId());
		
		Assertions.assertNotNull(found);
		Assertions.assertEquals("Test Project", found.getName());
	}
	
	@Test
	public void deleteProject_WhenDeleted_ShouldNotBeRetrievable() {
		User user = new User();
		user.setUsername("testuser2");
		user.setPassword("password");
		user.setFirstName("Jane");
		user.setLastName("Doe");
		user.setEmail("jane@test.com");
		userService.saveUser(user);
		
		User savedUser = userService.findByUsername("testuser2");
		
		Project project = new Project();
		project.setName("Test project to delete");
		project.setDescription("A test project that will be deleted");
		project.setOwner(savedUser);
		projectService.saveProject(project);
		
		int id = project.getId();
		projectService.deleteProject(id);
		
		Project found = projectService.findById(id);
		
		Assertions.assertNull(found);
	}
}
