package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserAction;
import com.example.dao.UserStatisticsRepository;
import com.example.entity.UserStats;

@Service
public class UserStatisticsService {

	@Autowired
	private UserStatisticsRepository repository;

	public void registerSignUp(String username) {
		UserStats stat = new UserStats(username, UserAction.SIGN_UP);
		repository.save(stat);
	}

	public void registerLogIn(String username) {
		UserStats stat = new UserStats(username, UserAction.LOGIN);
		repository.save(stat);
	}

	public List<UserStats> retrieveAllUsersStatistics() {
		return repository.findAll();
	}

	public List<UserStats> retrieveMyUsersStatistics(String username) {
		return repository.findByUsername(username);
	}
}
