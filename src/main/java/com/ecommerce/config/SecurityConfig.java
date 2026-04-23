package com.ecommerce.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
@Data
public class SecurityConfig {
	
	private static Logger log = LoggerFactory.getLogger(SecurityConfig.class);  
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("Security configuration");
		http.csrf(csrf -> csrf.disable());
//		.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").authenticated())
//		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
}
