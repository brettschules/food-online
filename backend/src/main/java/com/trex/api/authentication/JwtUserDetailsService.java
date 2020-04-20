package com.trex.api.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trex.api.authentication.service.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired UserService userService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userService.getUserInfoByUserName(username);
		
		if (user != null && user.getUserName().equals(username)) {
			//TODO: return user roles from DB. Hard coded in for now
			List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>(Arrays.asList(
					new SimpleGrantedAuthority("ROLE_ADMIN"), 
					new SimpleGrantedAuthority("ROLE_USER"))
			);
			
			return new User(user.getUserName(), user.getPassword(),
					roles);
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
	//Creates UsernamePasswordAuthenticationToken which is used to authentication users credientials
	public void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw e;
		} catch (BadCredentialsException e) {
//			TODO: handle logging
			System.out.println(e.getMessage());
			System.out.println(e);
			throw new InvalidCredentialsException("Invalid username/password");
		} catch (Exception e) {
			throw e;
		}
	}

}
