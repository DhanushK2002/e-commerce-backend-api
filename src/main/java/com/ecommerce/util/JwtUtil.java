package com.ecommerce.util;

import java.security.Key;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expirationMs}")
	private Long expirationMs;
	
//	public JwtUtil() {
//		// Generates own key
//		 try {
//			KeyGenerator key = KeyGenerator.getInstance("HmacSHA256");
//			SecretKey sk = key.generateKey();
//			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//			String secretKeyDecoded = Base64.getDecoder().decode(secretKey).toString();
//			System.out.println("Secret Key :" +secretKey);
//			System.out.println("Decoded Secret Key :"+secretKeyDecoded);
//			
//		 } catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		 }
//	}
	
	public String generateToken(UserDetails userDetails) {

		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority/*authority -> authority.getAuthority()*/)
				.toList();


		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.claim("roles", roles)
				.setIssuedAt(new Date())
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
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}
	
}
