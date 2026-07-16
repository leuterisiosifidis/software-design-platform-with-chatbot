package com.myy803.project.repositories;

import com.myy803.project.domain.Project;
import com.myy803.project.domain.UseCase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UseCaseRepository extends JpaRepository<UseCase, Integer> {
	List<UseCase> findByProject(Project project);
}
