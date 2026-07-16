package com.myy803.project.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.User;
import com.myy803.project.services.ProjectService;
import com.myy803.project.services.UserService;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	//View list:
	@GetMapping("/projects")
	public String showProjects(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		User owner = userService.findByUsername(userDetails.getUsername());
		List<Project> projects = projectService.findByOwner(owner);
		model.addAttribute("projects", projects);
		model.addAttribute("newProjects", new Project());
		return "projects";
	}
	
	//Create new project:
	@PostMapping("/projects")
	public String createProject(
			@AuthenticationPrincipal UserDetails userDetails,
			@RequestParam String name,
			@RequestParam String description) {
		User owner = userService.findByUsername(userDetails.getUsername());
		Project project = new Project();
		project.setName(name);
		project.setDescription(description);
		project.setOwner(owner);
		projectService.saveProject(project);
		return "redirect:/projects";
	}
	
	//Delete project:
	@PostMapping("/projects/{id}/delete")
	public String deleteProject(@PathVariable int id) {
		projectService.deleteProject(id);
		return "redirect:/projects";
	}
}
