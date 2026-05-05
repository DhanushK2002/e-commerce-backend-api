package com.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.ecommerce.dto.RegisterDto;

public interface UserService {
	public ResponseEntity<?> register(RegisterDto request);
}
