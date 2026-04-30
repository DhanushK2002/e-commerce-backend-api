package com.ecommerce.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

//@Component
public class JwtUtil {
	
//	@Value("${jwt.secret}")
	private String secretKey = "";
	
	@Value("${jwt.expirationMs}")
	private long expirationMs;
	
	
	public JwtUtil() {
		// Generates own key
		 try {
			KeyGenerator key = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = key.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		 } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		 }
	}
	public String generateToken(String username, String role) {
		System.out.println("Inside JWT UTIL");
		return Jwts.builder()
				.setSubject(username)
				.claim("role", role)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationMs))
				.signWith(getKey())
				.compact();		
	}
	
	private Key getKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}
	
}
