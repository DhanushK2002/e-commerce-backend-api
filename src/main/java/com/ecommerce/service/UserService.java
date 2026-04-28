package com.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.RegisterRequest;

public interface UserService {
	public ResponseEntity<?> register(RegisterRequest request);
	
	public ResponseEntity<?> login(LoginRequest login);
}
