package com.trex.api.authentication.service;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.trex.api.authentication.UserInfo;
import com.trex.api.authentication.persistor.UserPersitor;

@Repository
public class UserService {
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired 
	 private UserPersitor userPersitor;
	 	
	private final String CREATE_USER = "INSERT INTO users (firstName, middleName, lastName, userName, password)\n" + 
			"VALUES (?, ?, ?, ?, ?);";
		
	public int createUser(UserInfo user) {
		String pw_hash = passwordEncoder.encode(user.getPassword());


		
		jdbcTemplate.update(CREATE_USER, 
				user.getFirstName(),
				user.getMiddleName(),
				user.getLastName(),
				user.getUserName(),
				pw_hash
				);
		//TODO: Have db return userId
		return 1;
	}
	
	
	
	
	public UserInfo getUserInfoByUserName(String userName) {	
		return userPersitor.getUserInfoByUserName(userName);     
	}
	
}
