package com.myy803.project.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myy803.project.domain.Project;
import com.myy803.project.domain.User;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	List<Project> findByOwner(User owner);
}
