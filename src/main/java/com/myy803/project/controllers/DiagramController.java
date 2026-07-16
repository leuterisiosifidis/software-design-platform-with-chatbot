package com.myy803.project.controllers;

import com.myy803.project.services.DiagramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects/{projectId}/diagrams")
public class DiagramController {
	
	private final DiagramService diagramService;
	
	public DiagramController(DiagramService diagramService) {
		this.diagramService = diagramService;
	}
	
	@GetMapping
	public String showDiagramPage(@PathVariable int projectId, Model model) {
		model.addAttribute("projectId", projectId);
		return "diagrams";
	}
	
	@GetMapping("/usecase")
	public String generateUseCaseDiagram(@PathVariable int projectId, 
										 @RequestParam String format, Model model) {
		String script = diagramService.generateUseCaseDiagram(projectId, format);
		model.addAttribute("projectId", projectId);
		model.addAttribute("script", script);
		model.addAttribute("format", format);
		model.addAttribute("diagramType", "Use Case");
		return "diagram-result";
	}
	
	@GetMapping("/class")
	public String generateClassDiagram(@PathVariable int projectId,
									   @RequestParam String format,
									   Model model) {
		String script = diagramService.generateClassDiagram(projectId, format);
		model.addAttribute("projectId", projectId);
		model.addAttribute("script", script);
		model.addAttribute("format", format);
		model.addAttribute("diagramType", "Class");
		return "diagram-result";
	}
}
