package com.example.routefiltering.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.example.routefiltering.models.Route;
import com.example.routefiltering.services.RouteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PubSubMessageProcessor {

    private final RouteService routeService;

    @Autowired
    public PubSubMessageProcessor(RouteService routeService) {
        this.routeService = routeService;
    }

    @ServiceActivator(inputChannel = "updateRouteInputChannel")
    public void processMessage(String messagePayload) {
        System.out.println("Message received: " + messagePayload);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Route route = objectMapper.readValue(messagePayload, Route.class);
            routeService.updateRoute(route.getInitialRouteId(), route);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}