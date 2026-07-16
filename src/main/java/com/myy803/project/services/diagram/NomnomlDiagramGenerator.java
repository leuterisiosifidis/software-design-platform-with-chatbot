package com.myy803.project.services.diagram;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NomnomlDiagramGenerator implements DiagramGenerator {
	
	@Override
	public String getFormat() {
		return "nomnoml";
	}
	
	@Override
	public String generateUseCaseDiagram(Project project, List<UseCase> useCases) {
		StringBuilder sb = new StringBuilder();
		
		Set<String> actors = new LinkedHashSet<>();
		for (UseCase uc : useCases) {
			if (uc.getActors() != null && !uc.getActors().isBlank()) {
				for (String actor : uc.getActors().split(",")) {
					actors.add(actor.trim());
				}
			}
		}
		
		for (int i = 0; i < useCases.size(); i++) {
			UseCase uc = useCases.get(i);
			if (uc.getActors() != null && !uc.getActors().isBlank()) {
				for (String actor : uc.getActors().split(",")) {
					sb.append("[<actor> ").append(actor.trim()).append("] -> [").append(uc.getName()).append("]\n");
				}
			}
		}
		return sb.toString();
	}
	
	@Override
	public String generateClassDiagram(Project project, List<CrcCard> crcCards) {
		StringBuilder sb = new StringBuilder();
		
		Set<String> knownClasses = new LinkedHashSet<>();
		for (CrcCard card : crcCards) {
			knownClasses.add(card.getClassName().trim());
		}
		
		for (CrcCard card : crcCards) {
			sb.append("[").append(card.getClassName()).append("|");
			if (card.getResponsibilities() != null && !card.getResponsibilities().isBlank()) {
				for (String line : card.getResponsibilities().split("\n")) {
					String trimmed = line.trim();
					if (!trimmed.isEmpty()) {
						sb.append(trimmed).append(";");
					}
				}
			}
			sb.append("]\n");
		}
		
		sb.append("\n");
		
		for (CrcCard card : crcCards) {
			if (card.getCollaborations() != null && !card.getCollaborations().isBlank()) {
				for(String collab : card.getCollaborations().split(",")) {
					String target = collab.trim();
					if (knownClasses.contains(target)) {
						sb.append("[").append(card.getClassName()).append("] -> [").append(target).append("]\n");
					}
				}
			}
		}
		
		return sb.toString();
	}
}
 