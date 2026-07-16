package com.myy803.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import com.myy803.project.services.diagram.NomnomlDiagramGenerator;

import java.util.Arrays;
import java.util.List;

public class NomnomlDiagramGeneratorTest {
	
	private NomnomlDiagramGenerator generator;
	private Project project;
	
	@BeforeEach
	public void setUp() {
		generator = new NomnomlDiagramGenerator();
		project = new Project();
		project.setName("TestProject");
	}
	
	@Test
	public void getFormat_ShouldReturnNomnoml() {
		Assertions.assertEquals("nomnoml", generator.getFormat());
	}
	
	@Test
	public void generateUseCaseDiagram_ShouldContainActorAndUseCaseName() {
		UseCase uc = new UseCase();
		uc.setName("Login");
		uc.setActors("Admin");
		List<UseCase> useCases = Arrays.asList(uc);
		
		String result = generator.generateUseCaseDiagram(project, useCases);
		
		Assertions.assertTrue(result.contains("[<actor> Admin]"));
		Assertions.assertTrue(result.contains("[Login]"));
	}
	
	@Test
	public void generateUseCaseDiagram_ShouldContainArrow() {
		UseCase uc = new UseCase();
		uc.setName("Login");
		uc.setActors("Admin");
		List<UseCase> useCases = Arrays.asList(uc);
		
		String result = generator.generateUseCaseDiagram(project, useCases);
		
		Assertions.assertTrue(result.contains("->"));
	}
	
	@Test
	public void generateClassDiagram_ShouldContainClassDefinition() {
		CrcCard card = new CrcCard();
		card.setClassName("UserService");
		card.setResponsibilities("Handle login");
		List<CrcCard> cards = Arrays.asList(card);
		
		String result = generator.generateClassDiagram(project, cards);
		
		Assertions.assertTrue(result.contains("[UserService|"));
		Assertions.assertTrue(result.contains("Handle login"));
	}
	
	@Test
	public void generateClassDiagram_ShouldContainCollaborationArrow() {
		CrcCard card1 = new CrcCard();
		card1.setClassName("UserService");
		card1.setCollaborations("UserRepository");
		
		CrcCard card2 = new CrcCard();
		card2.setClassName("UserRepository");
		card2.setCollaborations("");
		
		List<CrcCard> cards = Arrays.asList(card1, card2);
		
		String result = generator.generateClassDiagram(project, cards);
		
		Assertions.assertTrue(result.contains("[UserService] -> [UserRepository]"));
	}
}
