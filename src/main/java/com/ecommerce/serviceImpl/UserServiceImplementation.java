package com.ecommerce.serviceImpl;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.LoginResponse;
import com.ecommerce.dto.RegisterRequest;
import com.ecommerce.dto.RegisterResponse;
import com.ecommerce.exception.CustomException;
import com.ecommerce.model.Password;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import com.ecommerce.util.JwtUtil;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	private final JwtUtil jwtUtil;
	private final RoleRepository roleRepo;

	@Override
	public ApiResponse<RegisterResponse> register(RegisterRequest request) {

		userRepo.findByUsername(request.getUsername()).ifPresent(u -> {
			throw new RuntimeException("User already exist");
		});
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmailId(request.getEmailId());
		user.setAddress(request.getAddress());
		
		Password password = new Password();
		password.setPassword(passwordEncoder.encode(request.getPassword()));
		password.setUser(user);
		
		user.setPasswordDetails(password);

		Role customerRole = roleRepo.findById(2L)
				.orElseThrow(() -> new CustomException("Role not found in the database", HttpStatus.NOT_FOUND));
		
		user.getRoles().add(customerRole);
		
		userRepo.save(user);
		
		return new ApiResponse<RegisterResponse>(true,"User Register Successfully",LocalDateTime.now(), ResponseEntity.status(HttpStatus.CREATED));
	}
	
	@Override
	public ApiResponse<LoginResponse> login(LoginRequest request){
		try {
			Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			String token = "";
			if(authenticate.isAuthenticated())
				token = jwtUtil.generateToken(request.getUsername());
			
			LoginResponse response = new LoginResponse(token);
			
			return new ApiResponse<LoginResponse>(true, "Access token",response,LocalDateTime.now(), ResponseEntity.status(HttpStatus.OK));
		} catch (BadCredentialsException e) {
			throw new CustomException("Invalid Credentials", HttpStatus.UNAUTHORIZED);
		}
	}
}
