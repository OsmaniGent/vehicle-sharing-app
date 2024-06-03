package com.example.routefiltering.models;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import java.util.Objects;

public class Stop {

    private float [] coordinates;
    private String passengerId;
    private String stopType;


    public Stop() {
    }

    public Stop(float[] coordinates, String passengerId, String stopType) {
        this.coordinates = coordinates;
        this.passengerId = passengerId;
        this.stopType = stopType;
    }

    public float[] getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getPassengerId() {
        return this.passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getStopType() {
        return this.stopType;
    }

    public void setStopType(String stopType) {
        this.stopType = stopType;
    }

    public Stop coordinates(float[] coordinates) {
        setCoordinates(coordinates);
        return this;
    }

    public Stop passengerId(String passengerId) {
        setPassengerId(passengerId);
        return this;
    }

    public Stop stopType(String stopType) {
        setStopType(stopType);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Stop)) {
            return false;
        }
        Stop stop = (Stop) o;
        return Objects.equals(coordinates, stop.coordinates) && Objects.equals(passengerId, stop.passengerId) && Objects.equals(stopType, stop.stopType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, passengerId, stopType);
    }

    @Override
    public String toString() {
        return "{" +
            " coordinates='" + getCoordinates() + "'" +
            ", passengerId='" + getPassengerId() + "'" +
            ", stopType='" + getStopType() + "'" +
            "}";
    }
    
    

}
