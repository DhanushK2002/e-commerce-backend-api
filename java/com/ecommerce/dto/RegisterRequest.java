package com.ecommerce.dto;

import com.ecommerce.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class RegisterRequest {
	@NotBlank(message = "Username is required")
	@Size(min = 8, max = 50, message = "Username must be between 3 and 50")
	private String username;
	
	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be 8 characters long")
	private String password;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email must be correct format")
	private String emailId;
	
	@NotBlank(message = "Address is required")
	private String address;
	
	private Role role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
