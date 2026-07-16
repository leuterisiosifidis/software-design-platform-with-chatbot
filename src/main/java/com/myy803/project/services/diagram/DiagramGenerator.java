package com.myy803.project.services.diagram;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import java.util.List;

public interface DiagramGenerator {
	String getFormat();
	String generateUseCaseDiagram(Project project, List<UseCase> useCases);
	String generateClassDiagram(Project project, List<CrcCard> crcCards);
}
