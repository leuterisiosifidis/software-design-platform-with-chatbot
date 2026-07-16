package com.myy803.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.myy803.project.domain.User;
import com.myy803.project.services.UserService;
import com.myy803.project.services.ProjectService;
import com.myy803.project.domain.Project;
import java.util.List;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	@GetMapping("/")
	public String root() {
		return "redirect:/login";
	}
	
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, Model model) {
		if (userService.existsByUsername(user.getUsername())) {
			model.addAttribute("error", "Username already exists");
			return "register";
		}
		userService.saveUser(user);
		return "redirect:/login";
	}
	
	@GetMapping("/dashboard")
	public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		User user = userService.findByUsername(userDetails.getUsername());
		List<Project> projects = projectService.findByOwner(user);
		model.addAttribute("currentUser", user);
		model.addAttribute("projects", projects);
		return "dashboard";
	}

	@GetMapping("/profile")
	public String showProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		User user = userService.findByUsername(userDetails.getUsername());
		model.addAttribute("user", user);
		return "profile";
	}

	@PostMapping("/profile")
	public String updateProfile(
			@AuthenticationPrincipal UserDetails userDetails,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String email,
			@RequestParam(required = false) String newPassword) {
		userService.updateProfile(userDetails.getUsername(), firstName, lastName, email, newPassword);
		return "redirect:/profile?success";
	}
}
