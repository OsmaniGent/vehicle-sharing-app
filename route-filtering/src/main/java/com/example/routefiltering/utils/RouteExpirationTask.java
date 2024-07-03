package com.example.routefiltering.utils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.routefiltering.models.Route;
import com.example.routefiltering.models.RouteEventDTO;
import com.example.routefiltering.repositories.RouteRepository;
import com.example.routefiltering.services.RouteService;

@Configuration
@EnableScheduling
public class RouteExpirationTask {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteService routeService;

    @Autowired
    private RestTemplate restTemplate;

    private final String googleOauthServiceUrl = "http://api-gateway:8765/USER/api/calendar/create-events";

    @Scheduled(fixedRate = 60000)
    public void checkForExpiredRoutes() {
        System.out.println("Checking for expired routes...");
        Date now = new Date();
        List<Route> expiredRoutes = routeRepository.findExpiredRoutes(now);
        for (Route route : expiredRoutes) {
            if (route.getExpiry().before(now)) {
                route.setIsOpen(false);
                Set<String> passengerIds = route.getStops().stream()
                                                .map(stop -> stop.getPassengerId())
                                                .collect(Collectors.toSet());

                RouteEventDTO eventDTO = new RouteEventDTO();
                eventDTO.setRouteId(route.getId());
                eventDTO.setPassengerIds(List.copyOf(passengerIds));
                eventDTO.setRouteDate(route.getRouteDate());
                eventDTO.setDriverId(route.getDriverId());

                try {
                    ResponseEntity<String> response = restTemplate.postForEntity(googleOauthServiceUrl, eventDTO, String.class);
                    System.out.println("Response from Google OAuth service: " + response.getBody());
                } catch (HttpClientErrorException e) {
                    System.err.println("HTTP error occurred: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                routeRepository.save(route);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void checkAllRoutes() {

        System.out.println("Checking for start date...");
        List<Route> allRoutes = routeRepository.findAll();
        Date now = new Date();
        for (Route route : allRoutes) {
            if(route.getRouteDate().before(now)){

                // route.setIsOpen(false);
                // routeRepository.save(route);
                System.out.println(route);

            }
        }
    }

    
    // @Scheduled(fixedRate = 60000)
    // public void checkAllRoutes() {

    //     System.out.println("Checking for seats available...");
    //     List<Route> allRoutes = routeRepository.findAll();

    //     for (Route route : allRoutes) {

    //         if(route.getSeatsAvailable() < 1){

    //             route.setIsOpen(false);
    //             routeRepository.save(route);

    //         }
    //     }
    // }

}

