package com.myy803.project;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myy803.project.domain.User;
import com.myy803.project.repositories.UserRepository;
import com.myy803.project.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Test
	public void saveUser_ShouldEncodePasswordAndSave() {
		User user = new User();
		user.setPassword("plainpassword");
		
		when(passwordEncoder.encode("plainpassword")).thenReturn("encodedpassword");
		
		userService.saveUser(user);
		
		Assertions.assertEquals("encodedpassword", user.getPassword());
		verify(passwordEncoder).encode("plainpassword");
		verify(userRepository).save(user);
	}
	
	@Test
	public void findByUsername_ShouldReturnUser() {
		User user = new User();
		
		when(userRepository.findByUsername("john")).thenReturn(user);
		
		User result = userService.findByUsername("john");
		
		Assertions.assertNotNull(result);
		verify(userRepository).findByUsername("john");
	}
	
	@Test
	public void existsByUsername_WhenUserExists_ShouldReturnTrue() {
		when(userRepository.findByUsername("john")).thenReturn(new User());
		
		boolean result = userService.existsByUsername("john");
		
		Assertions.assertTrue(result);
		verify(userRepository).findByUsername("john");
	}
	
	@Test
	public void existsByUsername_WhenUserDoesNotExist_ShouldReturnFalse() {
		when(userRepository.findByUsername("john")).thenReturn(null);
		
		boolean result = userService.existsByUsername("john");
		
		Assertions.assertFalse(result);
		verify(userRepository).findByUsername("john");
	}
	
	@Test
	public void updateProfile_ShouldUpdateFieldsAndSave() {
		User user = new User();
		
		when(userRepository.findByUsername("john")).thenReturn(user);
		when(passwordEncoder.encode("newpassword")).thenReturn("encodednewpassword");
		
		userService.updateProfile("john", "John", "Doe", "john@email.com", "newpassword");
		
		Assertions.assertEquals("John", user.getFirstName());
		Assertions.assertEquals("Doe", user.getLastName());
		Assertions.assertEquals("john@email.com", user.getEmail());
		Assertions.assertEquals("encodednewpassword", user.getPassword());
		verify(userRepository).save(user);
	}
	
	@Test
	public void updateProfile_WithBlankPassword_ShouldNotEncodeNewPassword() {
		User user = new User();
		user.setPassword("oldencoded");
		
		when(userRepository.findByUsername("john")).thenReturn(user);
		
		userService.updateProfile("john", "John", "Doe", "john@email.com", "");
		
		Assertions.assertEquals("oldencoded", user.getPassword());
		verify(userRepository).save(user);
	}
}
