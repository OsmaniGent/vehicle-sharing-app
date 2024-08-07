package com.example.user_management.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.example.user_management.services.PendingRouteService;
import com.example.user_management.models.PendingRoute;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PubSubMessageProcessor {

    private final PendingRouteService routeService;

    @Autowired
    public PubSubMessageProcessor(PendingRouteService routeService) {
        this.routeService = routeService;
    }

    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public void processMessage(String messagePayload) {
        System.out.println("Message received: " + messagePayload);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            PendingRoute pRoute = objectMapper.readValue(messagePayload, PendingRoute.class);
            routeService.addRoute(pRoute);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
