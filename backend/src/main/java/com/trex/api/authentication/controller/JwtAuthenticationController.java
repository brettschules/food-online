package com.trex.api.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.trex.api.authentication.UserInfo;
import com.trex.api.authentication.service.UserService;
import com.trex.api.base.BaseController;
import com.trex.api.security.JwtRequest;
import com.trex.api.security.JwtResponse;
import com.trex.api.security.JwtTokenUtil;
import com.trex.api.authentication.AuthConstants;
import com.trex.api.authentication.JwtUserDetailsService;


@RestController("AuthController")
@CrossOrigin
public class JwtAuthenticationController extends BaseController {
	UserService _authService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;

	
	public JwtAuthenticationController(UserService authService) {
		this._authService = authService;
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception {
//		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		

		final UserDetails userDetails = userDetailsService
		.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
		
	}
	
	@GetMapping("/hi")
	public ResponseEntity<?> hi() throws Exception {

		
		return ResponseEntity.ok("hi");
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
			} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserInfo user) throws Exception {
		_authService.createUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
