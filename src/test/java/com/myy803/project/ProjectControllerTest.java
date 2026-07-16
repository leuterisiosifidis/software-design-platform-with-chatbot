package com.myy803.project;                                                                       
                                                                                                    
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.myy803.project.controllers.ProjectController;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.User;
import com.myy803.project.services.ProjectService;
import com.myy803.project.services.UserService;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProjectService projectService;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void showProjects_ShouldReturnProjectsView() throws Exception {
		User user = new User();
		List<Project> projects = Arrays.asList(new Project(), new Project());
		
		when(userService.findByUsername("testuser")).thenReturn(user);
		when(projectService.findByOwner(user)).thenReturn(projects);
		
		mockMvc.perform(get("/projects")
					.with(user("testuser").roles("USER")))
					.andExpect(status().isOk())
					.andExpect(view().name("projects"));
	}
	
	@Test
	public void createProject_ShouldRedirectToProjects() throws Exception {
		User user = new User();
		
		when(userService.findByUsername("testuser")).thenReturn(user);
		
		mockMvc.perform(post("/projects")
				.with(user("testuser").roles("USER"))
				.with(csrf())
				.param("name", "My Project")
				.param("description", "A description"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/projects"));
		
		verify(projectService).saveProject(any(Project.class));
	}
	
	@Test
	public void deleteProject_ShouldRedirectToProjects() throws Exception {
		mockMvc.perform(post("/projects/1/delete")
				.with(user("testuser").roles("USER"))
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/projects"));
				
		verify(projectService).deleteProject(1);
	}
}
