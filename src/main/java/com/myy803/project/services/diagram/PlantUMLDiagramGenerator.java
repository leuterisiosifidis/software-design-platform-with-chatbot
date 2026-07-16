package com.myy803.project.services.diagram;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PlantUMLDiagramGenerator implements DiagramGenerator {
	
	@Override
	public String getFormat() {
		return "plantuml";
	}
	
	@Override
	public String generateUseCaseDiagram(Project project, List<UseCase> useCases) {
		StringBuilder sb = new StringBuilder();
		sb.append("@startuml\n");
		sb.append("title ").append(project.getName()).append(" - Use Case Diagram\n\n");
		
		Set<String> actors = new LinkedHashSet<>();
		for (UseCase uc : useCases) {
			if (uc.getActors() != null && !uc.getActors().isBlank()) {
				for (String actor : uc.getActors().split(",")) {
					actors.add(actor.trim());
				}
			}
		}
		
		for (String actor : actors) {
			sb.append("actor \"").append(actor).append("\" as").append(toAlias(actor)).append("\n");
		}
		
		sb.append("\nrectangle \"").append(project.getName()).append("\" {\n");
		for (int i = 0; i < useCases.size(); i++) {
			sb.append("   usecase \"").append(useCases.get(i).getName()).append("\" as UC").append(i + 1).append("\n");
		}
		sb.append("}\n\n");
		
		for (int i = 0; i < useCases.size(); i++) {
			UseCase uc = useCases.get(i);
			if (uc.getActors() != null && !uc.getActors().isBlank()) {
				for (String actor : uc.getActors().split(",")) {
					sb.append(toAlias(actor.trim())).append(" ---> UC").append(i + 1).append("\n");
				}
			}
		}
		sb.append("@enduml");
		return sb.toString();
	}
	
	@Override
	public String generateClassDiagram(Project project, List<CrcCard> crcCards) {
		StringBuilder sb = new StringBuilder();
		sb.append("@startuml\n");
		sb.append("title ").append(project.getName()).append(" - Class Diagram\n\n");
		
		Set<String> knownClasses = new LinkedHashSet<>();
		for (CrcCard card : crcCards) {
			knownClasses.add(card.getClassName().trim());
		}
		
		for (CrcCard card : crcCards) {
			sb.append("class \"").append(card.getClassName()).append("\" {\n");
			if (card.getResponsibilities() != null && !card.getResponsibilities().isBlank()) {
				for (String line : card.getResponsibilities().split("\n")) {
					String trimmed = line.trim();
					if (!trimmed.isEmpty()) {
						sb.append("    + ").append(trimmed).append("\n");
					}
				}
			}
			sb.append("}\n\n");
		}
		
		for (CrcCard card : crcCards) {
			if (card.getCollaborations() != null && !card.getCollaborations().isBlank()) {
				for(String collab : card.getCollaborations().split(",")) {
					String target = collab.trim();
					if (knownClasses.contains(target)) {
						sb.append("\"").append(card.getClassName()).append("\" --> \"").append(target).append("\"\n");
					}
				}
			}
		}
		
		sb.append("\n@enduml");
		return sb.toString();
	}
	
	private String toAlias(String name) {
		return name.replaceAll("[^a-zA-Z0-9]", "_");
	}
}
 