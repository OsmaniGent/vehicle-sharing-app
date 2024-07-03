package com.example.user_management.controllers;

import com.example.user_management.models.RouteEventDTO;
import com.example.user_management.services.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/calendar")
public class GoogleCalendarController {

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @PostMapping("/create-events")
    public ResponseEntity<String> createEventsForRoute(@RequestBody RouteEventDTO eventDTO) {
        try {
            googleCalendarService.createEventsForRoute(eventDTO);
            return ResponseEntity.ok("Events created successfully");
        } catch (IOException | GeneralSecurityException e) {
            return ResponseEntity.status(500).body("Failed to create events: " + e.getMessage());
        }
    }
}
