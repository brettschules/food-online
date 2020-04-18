package com.trex.api.authentication;

public class InvalidCredentialsException extends Exception {
	public InvalidCredentialsException(String errorMessage) {
		super(errorMessage);
	}
}
