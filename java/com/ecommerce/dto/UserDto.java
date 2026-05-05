package com.ecommerce.dto;

import java.util.List;

public class UserDto {
	private Long userId;
	
	private String username;
	
	private String emailId;
	
	private String address;
	
	private List<String> roles;

	public UserDto() {
		super();
	}

	public UserDto(Long userId, String username, String emailId, String address, List<String> roles) {
		super();
		this.userId = userId;
		this.username = username;
		this.emailId = emailId;
		this.address = address;
		this.roles = roles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
