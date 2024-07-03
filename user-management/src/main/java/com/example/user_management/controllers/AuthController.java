package com.example.user_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.user_management.models.AuthenticationResponse;
import com.example.user_management.models.User;
import com.example.user_management.repositories.UserRepository;
import com.example.user_management.services.GoogleAuthService;
import com.example.user_management.services.JwtService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/google-login")
    public ResponseEntity<Map<String, String>> googleLogin(@RequestBody Map<String, String> request) {
        System.out.println("Received request for Google login");
        System.out.println("Request payload: " + request);

        String accessToken = request.get("accessToken");
        System.out.println("Access Token: " + accessToken);

        try {
            String email = googleAuthService.verifyGoogleTokenAndGetEmail(accessToken);

            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                System.out.println("User not found for email: " + email);
                return ResponseEntity.status(404).body(null);
            }

            User user = optionalUser.get();
            user.setGoogleAccessToken(accessToken);
            userRepository.save(user);

            // Authenticate the user
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Generate JWT token
            String jwtToken = jwtService.generateToken(user);

            Map<String, String> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("userId", user.getId());

            System.out.println("Login successful, returning token and userId");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(null);
        }
    }
}
