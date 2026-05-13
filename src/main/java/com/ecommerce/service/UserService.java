package com.ecommerce.service;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.LoginResponse;
import com.ecommerce.dto.RegisterRequest;

public interface UserService {
	public ApiResponse<?> register(RegisterRequest request);
	
	public ApiResponse<LoginResponse> login(LoginRequest request);
}
