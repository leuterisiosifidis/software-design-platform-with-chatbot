package com.myy803.project.repositories;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CrcCardRepository extends JpaRepository<CrcCard, Integer> {
	List<CrcCard> findByProject(Project project);
}
