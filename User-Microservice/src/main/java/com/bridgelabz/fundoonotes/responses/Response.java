package com.bridgelabz.fundoonotes.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
	
	private int status;
	private String message;
	private Object data;
	private String token;
	
	public Response(int status,String message) {
		this.status = status;
		this.message = message;
	}
	
	public Response(String message,int status, Object data) {
		this.message = message;
		this.status = status;
		this.data = data;
	}
	
	public Response(int status, String message, String token) {
		this.status = status;
		this.message = message;
		this.token = token;
	}
	
}
