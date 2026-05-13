package com.ecommerce.dto;

public class LoginResponse {
	String accessToken;

	public LoginResponse() {
		super();
	}

	public LoginResponse(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
