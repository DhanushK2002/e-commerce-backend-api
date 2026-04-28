package com.ecommerce.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
//		System.out.println("User role from db " + user.getRole().name());
//		
//		System.out.println("User Email: "+ user.getEmail());
		
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail()) 
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();
	}
}
