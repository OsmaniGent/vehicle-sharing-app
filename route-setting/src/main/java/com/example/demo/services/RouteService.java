package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RouteService {

    private final String ACCESS_TOKEN = "pk.eyJ1IjoiZ2VudG9zbWFuaSIsImEiOiJjbHJ6ZDB6NmQxaWtuMmpteDVodnd0NzRwIn0.Ol6l6RCXj1_pKA6-JcYbkg";
    private final RestTemplate restTemplate = new RestTemplate();
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public RouteService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ResponseEntity<String> generateRoute(String type, String start, String end, String stops) throws URISyntaxException {
        String baseUrl = "https://api.mapbox.com/directions/v5/mapbox/";
        String coordinates = start + ";" + (stops != null ? stops + ";" : "") + end;
        String urlTemplate = baseUrl + type + "/" + coordinates + "?alternatives=false&geometries=geojson&language=en&overview=full&steps=true&access_token=" + ACCESS_TOKEN;
        URI uri = new URI(urlTemplate);

        try {
            return restTemplate.getForEntity(uri, String.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating route: " + e.getMessage());
        }
    }

    public ResponseEntity<String> generateTranslation(String place) throws URISyntaxException {
        String baseUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
        String urlTemplate = baseUrl + place + "?access_token=" + ACCESS_TOKEN;
        URI uri = new URI(urlTemplate);

        try {
            return restTemplate.getForEntity(uri, String.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating route: " + e.getMessage());
        }
    }

    public void sendRouteRequest(String initialRoute, String updatedRoute) {
        String combinedMessage = String.format("{\"initialRoute\": %s, \"updatedRoute\": %s}", initialRoute, updatedRoute);
        kafkaTemplate.send("filterRoutes", combinedMessage);
    }
}
