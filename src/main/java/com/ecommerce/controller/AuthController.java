package com.ecommerce.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.LoginResponse;
import com.ecommerce.dto.RegisterResponse;
import com.ecommerce.serviceImpl.UserServiceImplementation;
import com.ecommerce.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserServiceImplementation userService;
	
	private final AuthenticationManager authManager;
	
	private final JwtUtil jwtUtil;
	
	public AuthController(UserServiceImplementation userService, AuthenticationManager authManager, JwtUtil jwtUtil) {
		super();
		this.userService = userService;
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterResponse request) {
		return userService.register(request);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginResponse request) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		String token = jwtUtil.generateToken(request.getUsername());

		return ResponseEntity.ok(Map.of("access Token", token));
	}
}
