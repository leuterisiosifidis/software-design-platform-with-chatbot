package com.myy803.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myy803.project.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
		User findByUsername(String username);
		
}
