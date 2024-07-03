package com.example.user_management.services;

import com.example.user_management.daos.UserDAO;
import com.example.user_management.models.RouteEventDTO;
import com.example.user_management.models.User;
import com.example.user_management.repositories.UserRepository;
import com.example.user_management.utils.GoogleCalendarConfig;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.TimeZone;

@Service
public class GoogleCalendarService {

    @Autowired
    private GoogleCalendarConfig googleCalendarConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleAuthService googleAuthService;

    public void createEventsForRoute(RouteEventDTO eventDTO) throws IOException, GeneralSecurityException {
        for (String passengerId : eventDTO.getPassengerIds()) {
            User user = userRepository.findById(passengerId).orElseThrow(() -> new IllegalStateException("User not found for ID: " + passengerId));
            String accessToken = user.getGoogleAccessToken();
            String refreshToken = user.getGoogleRefreshToken();

            Calendar googleCalendar = buildCalendarServiceWithValidToken(accessToken, refreshToken, user);

            Event event = new Event()
                .setSummary("Route Start Notification")
                .setDescription("Your route with ID " + eventDTO.getRouteId() + " is going to start soon. The driver is " + eventDTO.getDriverId() + ". The date and time of the route is " + eventDTO.getRouteDate() + ".");

            Date startDate = eventDTO.getRouteDate(); // Assuming this is a java.util.Date object
            Date endDate = new Date(startDate.getTime() + (60 * 60 * 1000)); // Set end time 1 hour after start time

            DateTime start = new DateTime(startDate, TimeZone.getDefault());
            DateTime end = new DateTime(endDate, TimeZone.getDefault());

            event.setStart(new EventDateTime().setDateTime(start).setTimeZone("UTC"));
            event.setEnd(new EventDateTime().setDateTime(end).setTimeZone("UTC"));

            googleCalendar.events().insert("primary", event).execute();
        }
    }

    private Calendar buildCalendarServiceWithValidToken(String accessToken, String refreshToken, User user) throws GeneralSecurityException, IOException {
        Calendar googleCalendar = googleCalendarConfig.googleCalendar(googleCalendarConfig.httpTransport(), googleCalendarConfig.jsonFactory(), accessToken);
        
        try {
            // Attempt to use the calendar service, if the token is expired it will throw an exception
            googleCalendar.calendarList().list().execute();
        } catch (Exception e) {
            // Token expired, refresh it
            accessToken = googleAuthService.refreshAccessToken(refreshToken);
            user.setGoogleAccessToken(accessToken);
            userRepository.save(user);
            
            googleCalendar = googleCalendarConfig.googleCalendar(googleCalendarConfig.httpTransport(), googleCalendarConfig.jsonFactory(), accessToken);
        }
        
        return googleCalendar;
    }
}
