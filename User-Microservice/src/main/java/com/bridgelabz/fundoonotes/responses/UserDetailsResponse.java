package com.bridgelabz.fundoonotes.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsResponse {

	private String message;
	private int status;
	private String token;
	private String firstName;
	private String lastName;
	private String email;
	private String fileUrl;
	
	
	public UserDetailsResponse( int status, String message, String token, String firstName, String lastName, String email) {
		this.status = status;
		this.message = message;
		this.token = token;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public UserDetailsResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public UserDetailsResponse(int status, String message, String fileUrl ) {
		this.status = status;
		this.message = message;
		this.fileUrl = fileUrl;
	}

}
