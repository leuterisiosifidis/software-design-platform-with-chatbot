package com.myy803.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.myy803.project.domain.User;
import com.myy803.project.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public boolean existsByUsername(String username) {
		return userRepository.findByUsername(username) != null;
	}

	@Override
	public void updateProfile(String username, String firstName, String lastName, String email, String newPassword) {
		User user = userRepository.findByUsername(username);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		if (newPassword != null && !newPassword.isBlank()) {
			user.setPassword(passwordEncoder.encode(newPassword));
		}
		userRepository.save(user);
	}

}
