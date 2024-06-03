package com.example.routefiltering.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.routefiltering.models.IdentifiedCoordinate;
import com.example.routefiltering.models.Route;
import com.example.routefiltering.models.RouteFilterRequest;
import com.example.routefiltering.services.RouteService;
import com.example.routefiltering.utils.PubSubPublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.publisher.PubSubPublisherTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;


import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/filter")
public class RouteFilterController {

    private final RouteService routeService;

    @Autowired
    private PubSubPublisherService pubsub;
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    // private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PubSubPublisherTemplate pubSubTemplate;



    @Autowired
    public RouteFilterController(RouteService routeService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.routeService = routeService;
        this.kafkaTemplate = kafkaTemplate;
    }
    
    @PostMapping("/add")
    public ResponseEntity<Route> addRoute(@RequestBody Route route) {
        Route savedRoute = routeService.addRoute(route);
        return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
    }

    @GetMapping("/allRoutes")
    public ResponseEntity<List<Route>> findAllRoutes() {
        List<Route> routes = routeService.findAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/details/{routeId}")
    public ResponseEntity<?> findRoute(@PathVariable String routeId) {
        return routeService.findRoute(routeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/near")
    public ResponseEntity<List<Route>> findRoutesNear(@RequestParam double longitude,
                                                      @RequestParam double latitude) {
        List<Route> routes = routeService.findRoutesNear(longitude, latitude);
        return ResponseEntity.ok(routes);
    }

   @PostMapping("/duration")
    public String filterDuration(@RequestBody RouteFilterRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        Route iniRoute = request.getInitialRoute();
        Route upRoute = request.getUpdatedRoute();
        Route subRoute = request.getSubRoute();

        Route updatedRoute = routeService.calculateOverlap(upRoute, subRoute);

        try {
            String upRouteJson = objectMapper.writeValueAsString(updatedRoute);

            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(updatedRoute));
            
            if (updatedRoute.getDuration() - iniRoute.getDuration() <= (iniRoute.getMaxStopTime()*60))
                pubsub.publishMessage("pendingRoutes", upRouteJson);
            else
                return "not successful";
        } catch (Exception e) {
            e.printStackTrace();
            return "error serializing route";
        }

        return "successful";
    }

    // @PostMapping("/calendar")
    // public String insertCalendarEvent(@RequestBody String summary){

    //     String url = "https://www.googleapis.com/calendar/v3/calendars";



    //     return url;      

    // }

    @PostMapping("/test")
    public ResponseEntity<List<List<IdentifiedCoordinate>>> testSeg(@RequestBody List<IdentifiedCoordinate> identifiedCoordinates) {
        ObjectMapper objectMapper = new ObjectMapper();
        return ResponseEntity.ok(routeService.segmentIdentifiedCoordinates(identifiedCoordinates));
    }

    

    
}
