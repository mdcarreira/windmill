package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.dao.UserAction;

@Entity
public class UserStats {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private Date date;

	@Column(length = 36, nullable = false)
	private String username;

	@Column(nullable = false)
	private UserAction action;

	protected UserStats() {}

	public UserStats(String username, UserAction action) {
		this.username = username;
		this.action = action;
		this.date = new Date();
	}

	public String getUsername() {
		return username;
	}

	public UserAction getAction() {
		return action;
	}

	public Date getDate() {
		return date;
	}
	
}
