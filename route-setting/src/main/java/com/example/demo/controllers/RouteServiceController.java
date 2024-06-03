package com.example.demo.controllers;

import com.example.demo.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/route")
public class RouteServiceController {

    private final RouteService routeService;

    @Autowired
    public RouteServiceController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generateRoute(@RequestParam String type,
                                                @RequestParam String start, 
                                                @RequestParam String end,
                                                @RequestParam(required = false) String stops) throws URISyntaxException {
        return routeService.generateRoute(type, start, end, stops);
    }

    @GetMapping("/translate")
    public ResponseEntity<String> generateTranslation(@RequestParam String place) throws URISyntaxException {
        return routeService.generateTranslation(place);
    }

    @PostMapping("/send-filter")
    public ResponseEntity<String> sendRouteRequest(@RequestParam String initialRoute,
                                                   @RequestParam String updatedRoute) {
        routeService.sendRouteRequest(initialRoute, updatedRoute);
        return ResponseEntity.ok("Route request sent to Kafka");
    }
}
