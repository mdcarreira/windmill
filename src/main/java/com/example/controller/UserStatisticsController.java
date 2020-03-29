package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.entity.UserStats;
import com.example.service.UserStatisticsService;

@Controller
public class UserStatisticsController {

	@Autowired
	private UserStatisticsService statisticsService;
	
	@GetMapping("/admin/allUsersStatistics")
	public String allUserStats(Model model) {
		List<UserStats> stats = statisticsService.retrieveAllUsersStatistics();
		model.addAttribute("stats", stats);
		return "/user/usersStats";
	}
	
	@GetMapping("/user/myUsersStatistics")
	public String myUserStats(Model model, @AuthenticationPrincipal User user) {
		List<UserStats> stats = statisticsService.retrieveMyUsersStatistics(user.getUsername());
		model.addAttribute("stats", stats);
		model.addAttribute("username", user.getUsername());
		return "/user/usersStats";
	}

}
