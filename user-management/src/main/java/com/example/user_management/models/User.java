package com.example.user_management.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.Objects;

@Document
@Builder
public class User implements UserDetails{

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String username;

    private String hashedPassword;
    private String ethereumAddress;
    private String ethereumPrivateKey;
    private int accountIndex;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Car car;
    private List<Review> reviews = new ArrayList<>();
    private double avgRating;
    private double sumOfRatings;

    @Field("googleAccessToken")
    private String googleAccessToken = "";

    @Field("googleRefreshToken")
    private String googleRefreshToken = "";

    public User() {
        this.reviews = new ArrayList<>();
        this.sumOfRatings = 0;
    }

    public User(String id, String email, String username, String hashedPassword, String ethereumAddress,
            String ethereumPrivateKey, int accountIndex, Role role, Car car, List<Review> reviews, double avgRating,
            double sumOfRatings, String googleAccessToken, String googleRefreshToken) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.ethereumAddress = ethereumAddress;
        this.ethereumPrivateKey = ethereumPrivateKey;
        this.accountIndex = accountIndex;
        this.role = role;
        this.car = car;
        this.reviews = new ArrayList<>();
        this.avgRating = avgRating;
        this.sumOfRatings = 0;
        this.googleAccessToken = googleAccessToken;
        this.googleRefreshToken = googleRefreshToken;
    }
    
    // private User(String username, String password, String email, Role role) {
    //     this.username = username;
    //     this.hashedPassword = password;
    //     this.email = email;
    //     this.role = role;
    // }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getEthereumAddress() {
        return this.ethereumAddress;
    }

    public void setEthereumAddress(String ethereumAddress) {
        this.ethereumAddress = ethereumAddress;
    }

    public String getEthereumPrivateKey() {
        return this.ethereumPrivateKey;
    }

    public void setEthereumPrivateKey(String ethereumPrivateKey) {
        this.ethereumPrivateKey = ethereumPrivateKey;
    }

    public int getAccountIndex() {
        return this.accountIndex;
    }

    public void setAccountIndex(int accountIndex) {
        this.accountIndex = accountIndex;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<Review> getReviews() {
        return this.reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public double getAvgRating() {
        return this.avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public double getSumOfRatings() {
        return this.sumOfRatings;
    }

    public void setSumOfRatings(double sumOfRatings) {
        this.sumOfRatings = sumOfRatings;
    }

    public void addReview(Review newReview) {
        reviews.add(newReview);
    }

    public double calculateAvgRating() {

        sumOfRatings = 0;

        for (Review review : reviews) {
            sumOfRatings = sumOfRatings + review.getRating();
        }
        avgRating = sumOfRatings / reviews.size();

        return avgRating;
    }

    public User id(String id) {
        setId(id);
        return this;
    }

    public User email(String email) {
        setEmail(email);
        return this;
    }

    public User username(String username) {
        setUsername(username);
        return this;
    }

    public User hashedPassword(String hashedPassword) {
        setHashedPassword(hashedPassword);
        return this;
    }

    public User ethereumAddress(String ethereumAddress) {
        setEthereumAddress(ethereumAddress);
        return this;
    }

    public User ethereumPrivateKey(String ethereumPrivateKey) {
        setEthereumPrivateKey(ethereumPrivateKey);
        return this;
    }

    public User accountIndex(int accountIndex) {
        setAccountIndex(accountIndex);
        return this;
    }

    public User role(Role role) {
        setRole(role);
        return this;
    }

    public User car(Car car) {
        setCar(car);
        return this;
    }

    public User reviews(List<Review> reviews) {
        setReviews(reviews);
        return this;
    }

    public User avgRating(double avgRating) {
        setAvgRating(avgRating);
        return this;
    }

    public User sumOfRatings(double sumOfRatings) {
        setSumOfRatings(sumOfRatings);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email)
                && Objects.equals(username, user.username) && Objects.equals(hashedPassword, user.hashedPassword)
                && Objects.equals(ethereumAddress, user.ethereumAddress)
                && Objects.equals(ethereumPrivateKey, user.ethereumPrivateKey) && accountIndex == user.accountIndex
                && Objects.equals(role, user.role) && Objects.equals(car, user.car)
                && Objects.equals(reviews, user.reviews) && avgRating == user.avgRating
                && sumOfRatings == user.sumOfRatings;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, hashedPassword, ethereumAddress, ethereumPrivateKey, accountIndex,
                role, car,
                reviews, avgRating, sumOfRatings);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", email='" + getEmail() + "'" +
                ", username='" + getUsername() + "'" +
                ", hashedPassword='" + getHashedPassword() + "'" +
                ", ethereumAddress='" + getEthereumAddress() + "'" +
                ", ethereumPrivateKey='" + getEthereumPrivateKey() + "'" +
                ", accountIndex='" + getAccountIndex() + "'" +
                ", role='" + getRole() + "'" +
                ", car='" + getCar() + "'" +
                ", reviews='" + getReviews() + "'" +
                ", avgRating='" + getAvgRating() + "'" +
                ", sumOfRatings='" + getSumOfRatings() + "'" +
                "}";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.name()));

    }

    @Override
    public String getPassword() {
        
        return hashedPassword;

    }

    @Override
    public boolean isAccountNonExpired() {
        
        return true;

    }

    @Override
    public boolean isAccountNonLocked() {
        
        return true;

    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return true;

    }

    @Override
    public boolean isEnabled() {
        
        return true;

    }

    public String getGoogleAccessToken() {
        return googleAccessToken;
    }

    public void setGoogleAccessToken(String googleAccessToken) {
        this.googleAccessToken = googleAccessToken;
    }

    public String getGoogleRefreshToken() {
        return googleRefreshToken;
    }

    public void setGoogleRefreshToken(String googleRefreshToken) {
        this.googleRefreshToken = googleRefreshToken;
    }

}
