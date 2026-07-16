package com.myy803.project.services;

public interface DiagramService {
	String generateUseCaseDiagram(int projectId, String format);
	String generateClassDiagram(int projectId, String format);
}
