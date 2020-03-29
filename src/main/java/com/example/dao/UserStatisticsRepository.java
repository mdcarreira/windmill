package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.UserStats;

public interface UserStatisticsRepository extends JpaRepository<UserStats, Long> {

	public List<UserStats> findByUsername(String username);
	
}
