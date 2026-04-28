package com.ecommerce.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.RegisterRequest;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<?> register(RegisterRequest request) {

		userRepo.findByUsername(request.getUsername()).ifPresent(u -> {
			throw new RuntimeException("User already exist");
		});

		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEmail(request.getEmail());
		user.setAddress(request.getAddress());

		if (request.getRole() == null)
			user.setRole(Role.CUSTOMER);
		else
			user.setRole(request.getRole());
		
		userRepo.save(user);
		return ResponseEntity.ok("User Register Successfully");
	}

	@Override
	public ResponseEntity<?> login(LoginRequest login) {
		User user = userRepo.findByUsername(login.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Password");
		}
		return ResponseEntity.ok("User login Successfull");
	}

}
