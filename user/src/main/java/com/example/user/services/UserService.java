package com.example.user.services;

import com.example.user.daos.UserDAO;
import com.example.user.models.PendingRoute;
import com.example.user.models.Review;
import com.example.user.models.Stop;
import com.example.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User addUser(User user) {
        return userDAO.save(user);
    }

    public Optional<User> findUser(String userId) {
        return userDAO.findById(userId);
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    public User deleteUser(String userId) {
        Optional<User> optionalUser = findUser(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        User user = optionalUser.get();
        userDAO.delete(user);
        return user;
    }

    public void addReview(String reviewedUserId, Review review) {
        Optional<User> optionalReviewedUser = findUser(reviewedUserId);
        if (!optionalReviewedUser.isPresent()) {
            throw new RuntimeException("User not found with ID: " + reviewedUserId);
        }
        User reviewedUser = optionalReviewedUser.get();
        reviewedUser.addReview(review);
        double avgRating = reviewedUser.calculateAvgRating();
        reviewedUser.setAvgRating(avgRating);
        userDAO.save(reviewedUser);
    }

    public List<User> findUsersByRoute(PendingRoute route) {
        List<User> routeUsers = new ArrayList<>();
    
        Optional<User> driverOptional = findUser(route.getDriverId());
        driverOptional.ifPresent(routeUsers::add);
    
        for (Stop stop : route.getStops()) {
            Optional<User> passengerOptional = findUser(stop.getPassengerId());
            passengerOptional.ifPresent(routeUsers::add);
        }
    
        return routeUsers;
    }
    

    public Optional<User> authenticateUser(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && password.equals(user.getHashedPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
