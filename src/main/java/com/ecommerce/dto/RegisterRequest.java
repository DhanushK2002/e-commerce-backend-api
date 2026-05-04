package com.ecommerce.dto;

import com.ecommerce.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class RegisterRequest {
	@NotNull(message = "Username cannot be null")
	private String username;
	
	@NotEmpty(message = "Password cannot be empty")
	private String password;
	
	@NotNull(message = "Email should be provided")
	private String email;
	
	@NotBlank(message = "Fill address")
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
