package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dao.UserRepository;
import com.example.entity.User;
import com.example.service.UserStatisticsService;

@Controller
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
	private UserStatisticsService statisticsService;

	@GetMapping("/admin/allUsers")
	public String allUsers(Model model) {
		List<User> users = repository.findAllOrderByUsername();
		model.addAttribute("allUsers", users);
		return "/admin/allUsers";
	}

	@GetMapping("/createUser")
	public String createUser(Model model) {
		model.addAttribute("user", new User());
		return "createUser";
	}
	
	@PostMapping("/createUser")
	public String createUser(Model model, @ModelAttribute("user") User requestUser,
			final RedirectAttributes redirectAttributes) {

		String encrytedPassword = passwordEncoder.encode(requestUser.getPassword());
		User newUser = new User(requestUser.getUsername(), encrytedPassword, requestUser.isAdmin());

		try {
			repository.save(newUser);

			statisticsService.registerSignUp(newUser.getUsername());

			redirectAttributes.addFlashAttribute("registeredUser", newUser);
			return "redirect:/home";
		}
		catch(DataIntegrityViolationException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Username already exists.");
			return "redirect:/createUser";
		}
	}

}
