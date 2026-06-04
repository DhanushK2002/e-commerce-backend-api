package com.ecommerce.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
	@NotBlank(message = "Username cannot be blank")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50")
	@Column(unique = true, nullable = false)
	private String username;
	
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;
	
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid email format")
	@Column(unique = true, nullable = false)
	private String emailId;
	
	@NotBlank(message = "Address is required")
	private String address;
	 
	private String role;
}
