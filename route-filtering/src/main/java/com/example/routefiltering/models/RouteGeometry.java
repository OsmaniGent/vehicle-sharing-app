package com.example.routefiltering.models;

import java.util.List;
import java.util.Objects;

public class RouteGeometry {
    private String type = "LineString";
    private List<List<Double>> coordinates;

    // private List<RouteSegment> segments;



    public RouteGeometry() {
    }

    public RouteGeometry(String type, List<List<Double>> coordinates) {
        this.type = type;
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

    public RouteGeometry type(String type) {
        setType(type);
        return this;
    }

    public RouteGeometry coordinates(List<List<Double>> coordinates) {
        setCoordinates(coordinates);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RouteGeometry)) {
            return false;
        }
        RouteGeometry routeGeometry = (RouteGeometry) o;
        return Objects.equals(type, routeGeometry.type) && Objects.equals(coordinates, routeGeometry.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, coordinates);
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", coordinates='" + getCoordinates() + "'" +
            "}";
    }
   

}
