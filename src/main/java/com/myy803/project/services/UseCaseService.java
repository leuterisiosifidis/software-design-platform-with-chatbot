package com.myy803.project.services;

import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import java.util.List;

public interface UseCaseService {

	List<UseCase> findByProject(Project project);
	UseCase findById(int id);
	void saveUseCase(UseCase useCase);
	void deleteUseCase(int id);
}
