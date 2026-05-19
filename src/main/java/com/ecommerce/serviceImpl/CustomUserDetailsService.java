package com.ecommerce.serviceImpl;

import java.util.Set;
import java.util.stream.Collectors;

import com.ecommerce.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

//	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {

		log.info("Inside Custom User Details class");

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());

		log.info("Authorities = "+authorities);

		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPasswordDetails().getPassword(), authorities);
	}
}
