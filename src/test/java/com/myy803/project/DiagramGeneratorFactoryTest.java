package com.myy803.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myy803.project.services.diagram.DiagramGenerator;
import com.myy803.project.services.diagram.DiagramGeneratorFactory;
import com.myy803.project.services.diagram.NomnomlDiagramGenerator;
import com.myy803.project.services.diagram.PlantUMLDiagramGenerator;

public class DiagramGeneratorFactoryTest {
	
	@Test
	public void create_WithPlantUml_ShouldReturnPlantUMLGenerator() {
		DiagramGenerator generator = DiagramGeneratorFactory.create("plantuml");
		
		Assertions.assertNotNull(generator);
		Assertions.assertInstanceOf(PlantUMLDiagramGenerator.class, generator);
	}
	
	@Test
	public void create_WithNomnoml_ShouldReturnNomnomlGenerator() {
		DiagramGenerator generator = DiagramGeneratorFactory.create("nomnoml");
		
		Assertions.assertNotNull(generator);
		Assertions.assertInstanceOf(NomnomlDiagramGenerator.class, generator);
	}
	
	@Test
	public void create_WithUnknownFormat_ShouldThrowIllegalArgumentException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DiagramGeneratorFactory.create("uknown");
		});
	}
}
