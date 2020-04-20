package com.trex.api.authentication.persistor;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.trex.api.authentication.UserInfo;

@Repository
public class UserPersitor {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	//TODO: Make a stored proc to get user and roles info
	private final String GET_USER_BY_USERNAME = "SELECT * FROM users WHERE userName = ?;";
	
	private final String GET_USER_ROLES_BY_USERID = "select * from user_roles ur\n" + 
			"inner join users u on u.id = ur.userId;";
	
	public UserInfo getUserInfoByUserName(String userName) {	
		return jdbcTemplate.query(GET_USER_BY_USERNAME, new Object[]{userName}, rs -> {

            if(rs.next()){
            	UserInfo userInfo = new UserInfo();
            	
	            userInfo.setId(rs.getLong("id"));
	            userInfo.setFirstName(rs.getString("firstName"));
	            userInfo.setMiddleName(rs.getString("middleName"));
	            userInfo.setLastName(rs.getString("lastName"));
	            userInfo.setUserName(rs.getString("userName"));
	            userInfo.setPassword(rs.getString("password"));	

	            return userInfo;	       
            } else {
                return null;
            }
	    });
		
	}
	
}

