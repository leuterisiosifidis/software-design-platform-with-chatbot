package com.myy803.project.controllers;

import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import com.myy803.project.services.ProjectService;
import com.myy803.project.services.UseCaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/projects/{projectId}/usecases")
public class UseCaseController {
	
	private final UseCaseService useCaseService;
	private final ProjectService projectService;
	
	public UseCaseController(UseCaseService useCaseService, ProjectService projectService) {
		this.useCaseService = useCaseService;
		this.projectService = projectService;
	}
	
	@GetMapping
	public String listUseCases(@PathVariable int projectId, Model model) {
		Project project = projectService.findById(projectId);
		List<UseCase> useCases = useCaseService.findByProject(project);
		model.addAttribute("project", project);
		model.addAttribute("useCases", useCases);
		model.addAttribute("newUseCase", new UseCase());
		return "usecases";
	}
	
	@PostMapping
	public String createUseCase(@PathVariable int projectId,
								@RequestParam String name,
								@RequestParam String actors,
								@RequestParam String preconditions,
								@RequestParam String mainFlow,
								@RequestParam String alternativeFlow,
								@RequestParam String postconditions) {
		Project project = projectService.findById(projectId);
		UseCase useCase = new UseCase();
		useCase.setName(name);
		useCase.setActors(actors);
		useCase.setPreconditions(preconditions);
		useCase.setMainFlow(mainFlow);
		useCase.setAlternativeFlow(alternativeFlow);
		useCase.setPostconditions(postconditions);
		useCase.setProject(project);
		useCaseService.saveUseCase(useCase);
		return "redirect:/projects/" + projectId + "/usecases";
				
	}
	
	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable int projectId,
							   @PathVariable int id,
							   Model model) {
		Project project = projectService.findById(projectId);
		UseCase useCase = useCaseService.findById(id);
		model.addAttribute("project", project);
		model.addAttribute("useCase", useCase);
		return "usecases-edit";
	}

	@PostMapping("/{id}/edit")
	public String updateUseCase(@PathVariable int projectId,
								@PathVariable int id,
								@RequestParam String name,
								@RequestParam String actors,
								@RequestParam String preconditions,
								@RequestParam String mainFlow,
								@RequestParam String alternativeFlow,
								@RequestParam String postconditions) {
		UseCase useCase = useCaseService.findById(id);
		useCase.setName(name);
		useCase.setActors(actors);
		useCase.setPreconditions(preconditions);
		useCase.setMainFlow(mainFlow);
		useCase.setAlternativeFlow(alternativeFlow);
		useCase.setPostconditions(postconditions);
		useCaseService.saveUseCase(useCase);
		return "redirect:/projects/" + projectId + "/usecases";
	}
	
	@PostMapping("/{id}/delete")
	public String deleteUseCase(@PathVariable int projectId,
								@PathVariable int id) {
			
		useCaseService.deleteUseCase(id);
		return "redirect:/projects/" + projectId + "/usecases";
	}
}