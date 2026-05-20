package com.ecommerce.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.LoginResponse;
import com.ecommerce.dto.RegisterRequest;
import com.ecommerce.dto.RegisterResponse;
import com.ecommerce.serviceImpl.UserServiceImplementation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final UserServiceImplementation userService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest request) {
		ApiResponse<RegisterResponse> response = userService.register(request);
		return ResponseEntity.status(201).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
		ApiResponse<LoginResponse> response = userService.login(request);
		return ResponseEntity.status(200).body(response);
	}
}
