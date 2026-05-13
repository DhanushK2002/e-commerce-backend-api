package com.ecommerce.controller;

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
public class AuthController {

	private final UserServiceImplementation userService;
	
	public AuthController(UserServiceImplementation userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/register")
	public ApiResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
		return userService.register(request);
	}

	@PostMapping("/login")
	public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
		return userService.login(request);
	}
}
