package com.example.demo.models;
import java.util.Objects;

public class UpdatedRouteDTO {

    private String id;
    private String driverId;
    private RouteGeometry geometry;
    private int duration;
    private int distance;
    private int seatsAvailable;
    private long maxStopTime;


    public UpdatedRouteDTO() {
    }

    public UpdatedRouteDTO(String id, String driverId, RouteGeometry geometry, int duration, int distance, int seatsAvailable, long maxStopTime) {
        this.id = id;
        this.driverId = driverId;
        this.geometry = geometry;
        this.duration = duration;
        this.distance = distance;
        this.seatsAvailable = seatsAvailable;
        this.maxStopTime = maxStopTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverId() {
        return this.driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public RouteGeometry getGeometry() {
        return this.geometry;
    }

    public void setGeometry(RouteGeometry geometry) {
        this.geometry = geometry;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getSeatsAvailable() {
        return this.seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public long getMaxStopTime() {
        return this.maxStopTime;
    }

    public void setMaxStopTime(long maxStopTime) {
        this.maxStopTime = maxStopTime;
    }

    public UpdatedRouteDTO id(String id) {
        setId(id);
        return this;
    }

    public UpdatedRouteDTO driverId(String driverId) {
        setDriverId(driverId);
        return this;
    }

    public UpdatedRouteDTO geometry(RouteGeometry geometry) {
        setGeometry(geometry);
        return this;
    }

    public UpdatedRouteDTO duration(int duration) {
        setDuration(duration);
        return this;
    }

    public UpdatedRouteDTO distance(int distance) {
        setDistance(distance);
        return this;
    }

    public UpdatedRouteDTO seatsAvailable(int seatsAvailable) {
        setSeatsAvailable(seatsAvailable);
        return this;
    }

    public UpdatedRouteDTO maxStopTime(long maxStopTime) {
        setMaxStopTime(maxStopTime);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UpdatedRouteDTO)) {
            return false;
        }
        UpdatedRouteDTO updatedRouteDTO = (UpdatedRouteDTO) o;
        return Objects.equals(id, updatedRouteDTO.id) && Objects.equals(driverId, updatedRouteDTO.driverId) && Objects.equals(geometry, updatedRouteDTO.geometry) && duration == updatedRouteDTO.duration && distance == updatedRouteDTO.distance && seatsAvailable == updatedRouteDTO.seatsAvailable && maxStopTime == updatedRouteDTO.maxStopTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, driverId, geometry, duration, distance, seatsAvailable, maxStopTime);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", driverId='" + getDriverId() + "'" +
            ", geometry='" + getGeometry() + "'" +
            ", duration='" + getDuration() + "'" +
            ", distance='" + getDistance() + "'" +
            ", seatsAvailable='" + getSeatsAvailable() + "'" +
            ", maxStopTime='" + getMaxStopTime() + "'" +
            "}";
    }

    
}

