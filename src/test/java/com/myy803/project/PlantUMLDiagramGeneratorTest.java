package com.myy803.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import com.myy803.project.services.diagram.PlantUMLDiagramGenerator;

import java.util.Arrays;
import java.util.List;

public class PlantUMLDiagramGeneratorTest {
	
	private PlantUMLDiagramGenerator generator;
	private Project project;
	
	@BeforeEach
	public void setUp() {
		generator = new PlantUMLDiagramGenerator();
		project = new Project();
		project.setName("TestProject");
	}
	
	@Test
	public void getFormat_ShouldReturnPlantuml() {
		Assertions.assertEquals("plantuml", generator.getFormat());
	}
	
	@Test
	public void generateUseCaseDiagram_ShouldContainStartEndUmlTags() {
		List<UseCase> useCases = Arrays.asList();
		
		String result = generator.generateUseCaseDiagram(project, useCases);
		
		Assertions.assertTrue(result.contains("@startuml"));
		Assertions.assertTrue(result.contains("@enduml"));
	}
	
	@Test
	public void generateUseCaseDiagram_ShouldContainProjectName() {
		List<UseCase> useCases = Arrays.asList();
		
		String result = generator.generateUseCaseDiagram(project, useCases);
		
		Assertions.assertTrue(result.contains("TestProject"));
	}
	
	@Test
	public void generateUseCaseDiagram_ShouldContainActorsAndUseCases() {
		UseCase uc = new UseCase();
		uc.setName("Login");
		uc.setActors("Admin");
		List<UseCase> useCases = Arrays.asList(uc);
		
		String result = generator.generateUseCaseDiagram(project, useCases);
		
		Assertions.assertTrue(result.contains("Admin"));
		Assertions.assertTrue(result.contains("Login"));
	}
	
	@Test
	public void generateClassDiagram_ShouldContainClassDefinitions() {
		CrcCard card = new CrcCard();
		card.setClassName("UserService");
		card.setResponsibilities("Handle login");
		List<CrcCard> cards = Arrays.asList(card);
		
		String result = generator.generateClassDiagram(project, cards);
		
		Assertions.assertTrue(result.contains("@startuml"));
		Assertions.assertTrue(result.contains("UserService"));
		Assertions.assertTrue(result.contains("Handle login"));
	}
	
	@Test
    public void generateClassDiagram_ShouldContainCollaborationArrows() {
        CrcCard card1 = new CrcCard();                                                            
        card1.setClassName("UserService");
        card1.setCollaborations("UserRepository");                                                
                                                                                                  
        CrcCard card2 = new CrcCard();
        card2.setClassName("UserRepository");                                                     
                                                                                                  
        List<CrcCard> cards = Arrays.asList(card1, card2);
                                                                                                  
        String result = generator.generateClassDiagram(project, cards);

        Assertions.assertTrue(result.contains("-->"));
        Assertions.assertTrue(result.contains("UserRepository"));
    }

}
