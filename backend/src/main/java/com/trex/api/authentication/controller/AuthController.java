package com.trex.api.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.trex.api.authentication.User;
import com.trex.api.authentication.service.AuthService;


import com.trex.api.authentication.AuthConstants;


@RestController("AuthController")
@RequestMapping("/rest/v1/json/authentication")
public class AuthController {
	AuthService _authService;
	
	public AuthController(AuthService authService) {
		this._authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestHeader(value = AuthConstants.USERNAME) String userName,
            @RequestHeader(value = AuthConstants.PASSWORD) String password) throws Exception {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody User user) throws Exception {
		_authService.createUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
