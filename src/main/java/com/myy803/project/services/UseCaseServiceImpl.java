package com.myy803.project.services;

import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import com.myy803.project.repositories.UseCaseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UseCaseServiceImpl implements UseCaseService {
	
	private final UseCaseRepository useCaseRepository;
	
	public UseCaseServiceImpl(UseCaseRepository useCaseRepository) {
		this.useCaseRepository = useCaseRepository;
	}
	
	@Override
	public List<UseCase> findByProject(Project project) {
		return useCaseRepository.findByProject(project);
	}
	
	@Override
	public UseCase findById(int id) {
		return useCaseRepository.findById(id).orElse(null);
	}
	
	@Override
	public void saveUseCase(UseCase useCase) {
		useCaseRepository.save(useCase);
	}
	
	@Override
	public void deleteUseCase(int id) {
		useCaseRepository.deleteById(id);
	}
}
