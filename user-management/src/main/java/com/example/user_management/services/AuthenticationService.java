package com.example.user_management.services;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.user_management.models.Role;
import com.example.user_management.models.User;
import com.example.user_management.repositories.UserRepository;
import com.example.user_management.daos.UserDAO;
import com.example.user_management.models.AuthenticationResponse;
import com.example.user_management.models.LoginRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserDAO userDAO;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(User request) {
		  var user = User.builder()
			        .username(request.getUsername())
			        .email(request.getEmail())
			        .hashedPassword(passwordEncoder.encode(request.getPassword()))
			        .role(request.getRole())
			        .build();
		userDAO.save(user);
		var jwtToken = jwtService.generateToken(user);

		  return AuthenticationResponse.builder()
	                .token(jwtToken)
	                .userId(user.getId()) 
	                .build();	}

	   public AuthenticationResponse authenticate(LoginRequest request) {
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getHashedPassword()));
	        User user = userDAO.findByUsername(request.getUsername())
	                .orElseThrow(() -> new IllegalStateException("User not found"));
	        String jwtToken = jwtService.generateToken(user);

	        return AuthenticationResponse.builder()
	                .token(jwtToken)
	                .userId(user.getId()) 
	                .build();
	    }
}