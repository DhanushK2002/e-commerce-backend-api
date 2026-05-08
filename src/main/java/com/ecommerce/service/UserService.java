package com.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.ecommerce.dto.RegisterResponse;

public interface UserService {
	public ResponseEntity<?> register(RegisterResponse request);
}
