package com.ecommerce.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.RegisterResponse;
import com.ecommerce.model.Password;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@Autowired
//	private Password password;
	
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public ResponseEntity<?> register(RegisterResponse request) {

		userRepo.findByUsername(request.getUsername()).ifPresent(u -> {
			throw new RuntimeException("User already exist");
		});
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmailId(request.getEmailId());
		user.setAddress(request.getAddress());
		
		Password passwordEntity = new Password();
		passwordEntity.setPassword(passwordEncoder.encode(request.getPassword()));
		
		passwordEntity.setUser(user);
		
		user.setPasswordDetails(passwordEntity);

		Role customerRole = roleRepo.findById(2L)
				.orElseThrow(() -> new RuntimeException("Role not found in the database"));
		
		user.getRoles().add(customerRole);
		
		userRepo.save(user);
		return ResponseEntity.ok("User Register Successfully");
	}
}
