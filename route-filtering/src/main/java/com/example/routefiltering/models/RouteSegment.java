package com.example.routefiltering.models;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RouteSegment {
    
    private List<Double> coordinates;
    private Set<String> passengerIds;


    public RouteSegment() {
    }

    public RouteSegment(List<Double> coordinates, Set<String> passengerIds) {
        this.coordinates = coordinates;
        this.passengerIds = passengerIds;
    }

    public List<Double> getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public Set<String> getPassengerIds() {
        return this.passengerIds;
    }

    public void setPassengerIds(Set<String> passengerIds) {
        this.passengerIds = passengerIds;
    }

    public RouteSegment coordinates(List<Double> coordinates) {
        setCoordinates(coordinates);
        return this;
    }

    public RouteSegment passengerIds(Set<String> passengerIds) {
        setPassengerIds(passengerIds);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RouteSegment)) {
            return false;
        }
        RouteSegment routeSegment = (RouteSegment) o;
        return Objects.equals(coordinates, routeSegment.coordinates) && Objects.equals(passengerIds, routeSegment.passengerIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, passengerIds);
    }

    @Override
    public String toString() {
        return "{" +
            " coordinates='" + getCoordinates() + "'" +
            ", passengerIds='" + getPassengerIds() + "'" +
            "}";
    }
    

}
