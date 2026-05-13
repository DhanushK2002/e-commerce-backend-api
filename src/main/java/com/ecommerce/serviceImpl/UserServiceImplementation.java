package com.ecommerce.serviceImpl;


import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.LoginResponse;
import com.ecommerce.dto.RegisterRequest;
import com.ecommerce.dto.RegisterResponse;
import com.ecommerce.model.Password;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import com.ecommerce.util.JwtUtil;

@Service
public class UserServiceImplementation implements UserService {

	private final UserRepository userRepo;

	private final PasswordEncoder passwordEncoder;
	
//	@Autowired
//	private Password password;
	
	private final AuthenticationManager authManager;
	
	private final JwtUtil jwtUtil;
	
	private final RoleRepository roleRepo;

	
	
	public UserServiceImplementation(UserRepository userRepo, PasswordEncoder passwordEncoder,
			AuthenticationManager authManager, JwtUtil jwtUtil, RoleRepository roleRepo) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
		this.roleRepo = roleRepo;
	}

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
				.orElseThrow(() -> new RuntimeException("Role not found in the database"));
		
		user.getRoles().add(customerRole);
		
		userRepo.save(user);
		
		return new ApiResponse<RegisterResponse>(true,"User Register Successfully",LocalDateTime.now());
	}
	
	@Override
	public ApiResponse<LoginResponse> login(LoginRequest request){
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		String token = jwtUtil.generateToken(request.getUsername());
		
		LoginResponse response = new LoginResponse(token);
		
		return new ApiResponse<LoginResponse>(true, "Access token",response,LocalDateTime.now());
	}
}
