package com.ecommerce.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.ecommerce.serviceImpl.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("Security configuration");

		http.csrf(csrf -> csrf.disable()).userDetailsService(customUserDetailsService)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/products/**").permitAll()
						.requestMatchers("/orders/all").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET,"/orders/**").hasRole("CUSTOMER").anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
}
