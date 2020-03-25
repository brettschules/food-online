package com.trex.api.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.trex.api.authentication.User;

@Repository
public class AuthService {
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	
	private final String CREATE_USER = "INSERT INTO users (firstName, middleName, lastName, userName, password)\n" + 
			"VALUES (?, ?, ?, ?, ?);";
	
	public int createUser(User user) {
		jdbcTemplate.update(CREATE_USER, 
				user.getFirstName(),
				user.getMiddleName(),
				user.getLastName(),
				user.getUserName(),
				user.getPassword()
				);
		return 1;
	}

}
