package com.myy803.project.controllers;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import com.myy803.project.services.CrcCardService;
import com.myy803.project.services.ProjectService;
import com.myy803.project.services.UseCaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projects/{projectId}/crccards")
public class CrcCardController {
	
	private final CrcCardService crcCardService;
	private final ProjectService projectService;
	private final UseCaseService useCaseService;
	
	public CrcCardController(CrcCardService crcCardService, ProjectService projectService, UseCaseService useCaseService) {
		this.crcCardService = crcCardService;
		this.projectService = projectService;
		this.useCaseService = useCaseService;
	}
	
	@GetMapping
	public String listCrcCards(@PathVariable int projectId, Model model) {
		Project project = projectService.findById(projectId);
		List<CrcCard> crcCards = crcCardService.findByProject(project);
		model.addAttribute("project", project);
		model.addAttribute("crcCards", crcCards);
		return "crccards";
	}
	
	@PostMapping
	public String createCrcCard(@PathVariable int projectId,
								@RequestParam String className,
								@RequestParam String responsibilities,
								@RequestParam String collaborations) {
		Project project = projectService.findById(projectId);
		CrcCard crcCard = new CrcCard();
		crcCard.setClassName(className);
		crcCard.setResponsibilities(responsibilities);
		crcCard.setCollaborations(collaborations);
		crcCard.setProject(project);
		crcCardService.saveCrcCard(crcCard);
		return "redirect:/projects/" + projectId +"/crccards";
	}
	
	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable int projectId,
							   @PathVariable int id,
							   Model model) {
		Project project = projectService.findById(projectId);
		CrcCard crcCard = crcCardService.findById(id);
		List<UseCase> allUseCases = useCaseService.findByProject(project);
		model.addAttribute("project", project);
		model.addAttribute("crcCard", crcCard);
		model.addAttribute("allUseCases", allUseCases);
		return "crccards-edit";
	}
	
	@PostMapping("/{id}/edit")
	public String updateCrcCard(@PathVariable int projectId,
								@PathVariable int id,
								@RequestParam String className,
								@RequestParam String responsibilities,
								@RequestParam String collaborations,
								@RequestParam(required = false) List<Integer> useCaseIds) {
		CrcCard crcCard = crcCardService.findById(id);
		crcCard.setClassName(className);
		crcCard.setResponsibilities(responsibilities);
		crcCard.setCollaborations(collaborations);
		
		List<UseCase> linkedUseCases = new ArrayList<>();
		if (useCaseIds != null) {
			for (int ucId : useCaseIds) {
				UseCase uc = useCaseService.findById(ucId);
				if (uc != null) {
					linkedUseCases.add(uc);
				}
			}
		}
		crcCard.setUseCases(linkedUseCases);
		crcCardService.saveCrcCard(crcCard);
		return "redirect:/projects/" + projectId + "/crccards";
	}
	
	@PostMapping("/{id}/delete")
	public String deleteCrcCard(@PathVariable int projectId,
								@PathVariable int id) {
		crcCardService.deleteCrcCard(id);
		return "redirect:/projects/" + projectId + "/crccards";
	}
}
