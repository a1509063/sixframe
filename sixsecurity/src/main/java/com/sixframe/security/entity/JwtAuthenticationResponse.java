package com.sixframe.security.entity;

import org.springframework.http.HttpStatus;

public class JwtAuthenticationResponse {
	private String status;
	private String token;
	private String refreshToken;
	
	public JwtAuthenticationResponse(){}
	
	public JwtAuthenticationResponse(String token,String refreshToken){
		this.status = HttpStatus.OK.toString();
		this.token = token;
		this.refreshToken = refreshToken;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
}
