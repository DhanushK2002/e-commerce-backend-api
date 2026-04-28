package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.RegisterRequest;
import com.ecommerce.serviceImpl.UserServiceImplementation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserServiceImplementation userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
			return userService.register(request);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody  LoginRequest request){
		return userService.login(request);
	}
}
