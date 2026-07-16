package com.myy803.project.services;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import com.myy803.project.services.diagram.DiagramGenerator;
import com.myy803.project.services.diagram.DiagramGeneratorFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DiagramServiceImpl implements DiagramService {
	
	private final ProjectService projectService;
	private final UseCaseService useCaseService;
	private final CrcCardService crcCardService;
	
	public DiagramServiceImpl(ProjectService projectService, UseCaseService useCaseService, CrcCardService crcCardService) {
		this.projectService = projectService;
		this.useCaseService = useCaseService;
		this.crcCardService = crcCardService;
	}
	
	@Override
	public String generateUseCaseDiagram(int projectId, String format) {
		Project project = projectService.findById(projectId);
		List<UseCase> useCases = useCaseService.findByProject(project);
		DiagramGenerator generator = DiagramGeneratorFactory.create(format);
		return generator.generateUseCaseDiagram(project, useCases);
	}
	
	@Override
	public String generateClassDiagram(int projectId, String format) {
		Project project = projectService.findById(projectId);
		List<CrcCard> crcCards = crcCardService.findByProject(project);
		DiagramGenerator generator = DiagramGeneratorFactory.create(format);
		return generator.generateClassDiagram(project, crcCards);
	}
}
