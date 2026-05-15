package com.ecommerce.dto;

import com.ecommerce.model.Role;
import lombok.Data;

@Data
public class RegisterResponse {
	private String username;
	private String password;
	private String emailId;
	private String address;
	private Role role;
}
