package com.example.demo.models;

import java.util.List;

public class UpdateRouteStopsRequest {

    private String id;
    private List<List<Double>> stops;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<List<Double>> getStops() {
        return stops;
    }

    public void setStops(List<List<Double>> stops) {
        this.stops = stops;
    }
}