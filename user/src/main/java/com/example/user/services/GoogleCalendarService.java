package com.example.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
// import com.google.api.services.calendar.Calendar;
// import com.google.api.services.calendar.model.Event;
// import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
// import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

@Service
public class GoogleCalendarService {

    // @Autowired
    // private OAuth2AuthorizedClientService authorizedClientService;

    // private static final String APPLICATION_NAME = "Vehicle-Sharing-Web";
    // private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    // public List<Event> getUpcomingEvents() throws IOException, GeneralSecurityException {
    //     final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     String principalName = authentication.getName();
    //     Calendar service = buildCalendarService(getAccessToken("google", principalName));

    //     DateTime now = new DateTime(System.currentTimeMillis());
    //     com.google.api.services.calendar.model.Events events = service.events().list("primary")
    //         .setMaxResults(10)
    //         .setTimeMin(now)
    //         .setOrderBy("startTime")
    //         .setSingleEvents(true)
    //         .execute();
    //     return events.getItems();
    // }

    // private String getAccessToken(String registrationId, String principalName) {
    //     OAuth2AuthorizedClient authorizedClient = this.authorizedClientService.loadAuthorizedClient(registrationId, principalName);
    //     return authorizedClient.getAccessToken().getTokenValue();
    // }

    // private Calendar buildCalendarService(final String accessToken) throws GeneralSecurityException, IOException {
    //     return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, request -> request.getHeaders().setAuthorization("Bearer " + accessToken))
    //         .setApplicationName(APPLICATION_NAME)
    //         .build();
    // }

    // public Event createNewEvent(String calendarId) throws IOException, GeneralSecurityException {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     String principalName = authentication.getName();

    //     OAuth2AuthorizedClient authorizedClient = this.authorizedClientService.loadAuthorizedClient("google", principalName);

    //     if (authorizedClient == null) {
    //         throw new IllegalStateException("Authorized client not found for the given principal name: " + principalName);
    //     }

    //     String accessToken = authorizedClient.getAccessToken().getTokenValue();
        
    //     Calendar service = buildCalendarService(accessToken);

    //     Event event1 = new Event()
    //         .setSummary("Google I/O 2024")
    //         .setLocation("800 Howard St., San Francisco, CA 94103")
    //         .setDescription("A chance to hear more about Google's developer products.");

    //     DateTime startDateTime = new DateTime("2024-05-28T09:00:00-07:00");
    //     EventDateTime start = new EventDateTime()
    //         .setDateTime(startDateTime)
    //         .setTimeZone("America/Los_Angeles");
    //     event1.setStart(start);

    //     DateTime endDateTime = new DateTime("2024-05-28T17:00:00-07:00");
    //     EventDateTime end = new EventDateTime()
    //         .setDateTime(endDateTime)
    //         .setTimeZone("America/Los_Angeles");
    //     event1.setEnd(end);

    //     System.out.println(event1);
    //     return service.events().insert(calendarId, event1).execute();
    // }


}
