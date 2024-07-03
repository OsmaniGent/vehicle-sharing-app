package com.example.user_management.services;

import com.example.user_management.models.User;
import com.example.user_management.repositories.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GoogleAuthService {

    @Value("${google.clientId}")
    private String googleClientId;

    @Value("${google.client-secret}")
    private String googleClientSecret;

    @Autowired
    private UserRepository userRepository;

    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";

    public Map<String, String> exchangeAuthCodeForTokens(String authCode) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", googleClientId);
        body.add("client_secret", googleClientSecret);
        body.add("code", authCode);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", "http://localhost:3002"); // Ensure this matches your OAuth2 configuration

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", (String) responseBody.get("access_token"));
            tokens.put("refresh_token", (String) responseBody.get("refresh_token"));
            return tokens;
        } else {
            throw new IOException("Failed to request tokens");
        }
    }

    public String verifyGoogleTokenAndGetEmail(String accessToken) throws GeneralSecurityException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v1/userinfo", 
                HttpMethod.GET, 
                entity, 
                Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> userInfo = response.getBody();
            return (String) userInfo.get("email");
        } else {
            throw new GeneralSecurityException("Failed to verify access token.");
        }
    }

    private Map<String, String> requestTokens(String idTokenString) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", googleClientId);
        body.add("client_secret", googleClientSecret);
        body.add("code", idTokenString); // This should be the authorization code if available
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", "http://localhost:3002"); // This should match the redirect URI used in your OAuth2 configuration

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", (String) responseBody.get("access_token"));
            tokens.put("refresh_token", (String) responseBody.get("refresh_token"));
            return tokens;
        } else {
            throw new IOException("Failed to request tokens");
        }
    }
    

    public String refreshAccessToken(String refreshToken) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", googleClientId);
        body.add("client_secret", googleClientSecret);
        body.add("refresh_token", refreshToken);
        body.add("grant_type", "refresh_token");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            return (String) responseBody.get("access_token");
        } else {
            throw new IOException("Failed to refresh access token");
        }
    }
}
