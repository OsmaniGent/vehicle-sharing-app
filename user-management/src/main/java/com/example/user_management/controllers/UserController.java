package com.example.user_management.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_management.models.LoginRequest;
import com.example.user_management.models.PendingRoute;
import com.example.user_management.models.Review;
import com.example.user_management.models.User;
import com.example.user_management.services.PendingRouteService;
import com.example.user_management.utils.PubSubPublisherService;
import com.example.user_management.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.JwtProvider;
import com.google.cloud.spring.pubsub.core.publisher.PubSubPublisherTemplate;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/user")
public class UserController {

    private final PendingRouteService routeService;
    private final UserService userService;

    @Autowired
    private PubSubPublisherService pubsub;

    @Autowired
    public UserController(PendingRouteService routeService, UserService userService) {
        this.routeService = routeService;
        this.userService = userService;
    }

    // @RolesAllowed("DRIVER")
    @PostMapping("/add")
    public ResponseEntity<PendingRoute> addRoute(@RequestBody PendingRoute route) {
        PendingRoute savedRoute = routeService.addRoute(route);
        return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
    }

    @GetMapping("/allRoutes/{driverId}")
    public ResponseEntity<List<PendingRoute>> findAllRoutes(@PathVariable String driverId) {
        List<PendingRoute> routes = routeService.findRoutesByDriverId(driverId);
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/details/{routeId}")
    public ResponseEntity<?> findRoute(@PathVariable String routeId) {
        return routeService.findRoute(routeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/near")
    public ResponseEntity<List<PendingRoute>> findRoutesNear(@RequestParam double longitude,
            @RequestParam double latitude) {
        List<PendingRoute> routes = routeService.findRoutesNear(longitude, latitude);
        return ResponseEntity.ok(routes);
    }

    @DeleteMapping("/delete/{routeId}")
    public ResponseEntity<Optional<PendingRoute>> deleteRoute(@PathVariable String routeId) {
        Optional<PendingRoute> route = routeService.findRoute(routeId);
        routeService.deleteRoute(routeId);
        return ResponseEntity.ok(route);
    }

    @PostMapping("/update/{routeId}")
    public ResponseEntity<Optional<PendingRoute>> sendUpdate(@PathVariable String routeId) {
        Optional<PendingRoute> routeOptional = routeService.findRoute(routeId);

        if (!routeOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        PendingRoute route = routeOptional.get();
        ObjectMapper objectMapper = new ObjectMapper();
        String upRouteJson;

        try {
            upRouteJson = objectMapper.writeValueAsString(route);
            pubsub.publishMessage("updateRoute", upRouteJson);
            routeService.deleteRoute(routeId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(routeOptional);
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = userService.addUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user-details/{userId}")
    public ResponseEntity<?> findUser(@PathVariable String userId) {
        return userService.findUser(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user-details-username/{username}")
    public ResponseEntity<?> findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user-details-email/{email}")
    public ResponseEntity<?> findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addReview")
    public void addReview(@RequestBody Review review) {
        userService.addReview(review.getReviewedUserId(), review);
    }

    @GetMapping("/usersByRoute/{routeId}")
    public ResponseEntity<List<User>> findUsersByRoute(@PathVariable String routeId) {
        PendingRoute route = routeService.findRoute(routeId).get();
        List<User> users = userService.findUsersByRoute(route);
        return ResponseEntity.ok(users);
    }

    // @DeleteMapping("removeReview")
    // public void removeReview(String reviewerId){
    // userService.re
    // }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest.getUsername(), loginRequest.getHashedPassword())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/changeRole/{userId}")
    public void changeRole(@PathVariable String userId) {
        userService.updateUserRole(userId);
    }

    @GetMapping("/allRoutes")
    public ResponseEntity<List<PendingRoute>> findAllRoutes() {
        List<PendingRoute> routes = routeService.findAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/allReviews/{userId}")
    public ResponseEntity<List<Review>> findAllReviews(@PathVariable String userId) {
        List<Review> reviews = userService.findReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Optional<User>> deleteUser(@PathVariable String userId) {
        Optional<User> user = userService.findUser(userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok(user);
    }

}
