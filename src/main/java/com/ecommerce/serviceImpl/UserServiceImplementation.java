package com.ecommerce.serviceImpl;


import java.time.LocalDateTime;
import java.util.HashSet;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.LoginResponse;
import com.ecommerce.dto.RegisterRequest;
import com.ecommerce.dto.RegisterResponse;
import com.ecommerce.exception.CustomException;
import com.ecommerce.model.Password;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import com.ecommerce.util.JwtUtil;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepo;

    @Override
    public ApiResponse<RegisterResponse> register(RegisterRequest request) {

        userRepo.findByEmailId(request.getEmailId()).ifPresent(u -> {
            throw new CustomException("Email already exist", HttpStatus.BAD_REQUEST);
        });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmailId(request.getEmailId());
        user.setAddress(request.getAddress());

        Password password = new Password();
        password.setPassword(passwordEncoder.encode(request.getPassword()));
        password.setUser(user);

        user.setPasswordDetails(password);

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        Role customerRole = roleRepo.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new CustomException("Customer role not found", HttpStatus.NOT_FOUND));

        user.getRoles().add(customerRole);

        userRepo.save(user);

        return new ApiResponse<RegisterResponse>(
                true,
                "User Register Successfully",
                null,
                LocalDateTime.now(),
                201
        );
    }

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        try {
            Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String token = "";

            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            if (authenticate.isAuthenticated())
                token = jwtUtil.generateToken(userDetails);

            LoginResponse response = new LoginResponse(token);

            return new ApiResponse<LoginResponse>(
                    true,
                    "Access token",
                    response,
                    LocalDateTime.now(),
                    200
            );
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}
