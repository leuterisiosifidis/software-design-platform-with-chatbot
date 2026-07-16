package com.myy803.project.services.diagram;

public class DiagramGeneratorFactory {
	
	public static DiagramGenerator create(String format) {
		switch (format.toLowerCase()) {
			case "plantuml": return new PlantUMLDiagramGenerator();
			case "nomnoml":  return new NomnomlDiagramGenerator();
			default: throw new IllegalArgumentException("Unknown format: " + format);
		}
	}
}
