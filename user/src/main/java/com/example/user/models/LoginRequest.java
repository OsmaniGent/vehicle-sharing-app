package com.example.user.models;

import java.util.Objects;

public class LoginRequest {
    private String username;
    private String hashedPassword;


    public LoginRequest() {
    }

    public LoginRequest(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public LoginRequest username(String username) {
        setUsername(username);
        return this;
    }

    public LoginRequest hashedPassword(String hashedPassword) {
        setHashedPassword(hashedPassword);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LoginRequest)) {
            return false;
        }
        LoginRequest loginRequest = (LoginRequest) o;
        return Objects.equals(username, loginRequest.username) && Objects.equals(hashedPassword, loginRequest.hashedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, hashedPassword);
    }

    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", hashedPassword='" + getHashedPassword() + "'" +
            "}";
    }
   
}