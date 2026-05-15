package com.ecommerce.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
	@Column(nullable = true)
	private String email;
	
	@NotEmpty(message = "Password cannot be empty")
	private String password;
	
	@NotNull(message = "Username cannot be null or empty")
	private String username;
}
