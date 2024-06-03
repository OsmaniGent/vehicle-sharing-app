package com.example.user.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.google.api.services.calendar.model.Event;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    // @Autowired
    // private GoogleCalendarService calendarService;

    // @GetMapping("/events")
    // public List<Event> getEvents() throws Exception {
    //     return calendarService.getUpcomingEvents();
    // }

    // @GetMapping("/test")
    // public String getTest() throws Exception {
    //     return "Test Connection";
    // }

    // @PostMapping("/addEvent")
    // public Event addNewEvent() throws IOException, GeneralSecurityException {
    //     String calendarId = "primary";
    //     return calendarService.createNewEvent(calendarId);
    // }
}

