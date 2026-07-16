package com.myy803.project.services;

import com.myy803.project.domain.User;

public interface UserService {
	
	void updateProfile(String username, String firstName, String lastName, String email, String newPassword);
	void saveUser(User user);
	User findByUsername(String username);
	boolean existsByUsername(String username);
	
	
}
