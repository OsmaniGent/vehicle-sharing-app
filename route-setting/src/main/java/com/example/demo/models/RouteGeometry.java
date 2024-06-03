package com.example.demo.models;

import java.util.List;

public class RouteGeometry {
    private String type = "LineString";
    private List<List<Double>> coordinates;

    public RouteGeometry() {
    }

    public RouteGeometry(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<Double>> getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

}

