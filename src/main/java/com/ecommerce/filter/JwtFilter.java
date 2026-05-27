package com.ecommerce.filter;

import java.io.IOException;
import java.util.List;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ecommerce.serviceImpl.CustomUserDetailsService;
import com.ecommerce.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter{

	private final JwtUtil jwtUtil;

	private final CustomUserDetailsService userDetailsService;
	
//	private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getServletPath();

		if(path.startsWith("/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String header = request.getHeader("Authorization");
		if(header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			try {
				String username = jwtUtil.extractUsername(token);
				log.info("Username = {}",username);
				if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					log.info("User details instance = {} ",userDetails.getClass().getSimpleName());
					
					if(jwtUtil.validateToken(token)) {

                         Claims claims = jwtUtil.extractClaims(token);

						 List<String> roles = claims.get("roles", List.class);

						 List<SimpleGrantedAuthority> authorities = roles.stream()
								 .map(role -> new SimpleGrantedAuthority(role))
								 .toList();

						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,authorities);
						auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						
						SecurityContextHolder.getContext().setAuthentication(auth);
						log.info("Authentication successful for user = {} ", username);
					}
				}
			}catch(Exception ex) {
				log.error("Jwt filter exception occurred: ",ex);
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(request, response);
	}
}
