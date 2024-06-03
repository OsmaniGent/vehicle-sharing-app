package com.example.routefiltering.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document
public class Route {

    @Id
    private String id;

    private String initialRouteId;

    private String driverId;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private RouteGeometry geometry;
    
    private int duration;
    private int distance;
    private int seatsAvailable;
    private long maxStopTime;
    private float cost;

    private Date expiry;

    private boolean isOpen;

    private Date routeDate;
    
    private List<Stop> stops;

    private List<IdentifiedCoordinate> identifiedCoordinates;

    

    public Route() {
    }

    public Route(String id, String initialRouteId, String driverId, RouteGeometry geometry, int duration, int distance, int seatsAvailable, long maxStopTime, float cost, Date expiry, boolean isOpen, Date routeDate, List<Stop> stops) {
        this.id = id;
        this.initialRouteId = initialRouteId;
        this.driverId = driverId;
        this.geometry = geometry;
        this.duration = duration;
        this.distance = distance;
        this.seatsAvailable = seatsAvailable;
        this.maxStopTime = maxStopTime;
        this.cost = cost;
        this.expiry = expiry;
        this.isOpen = isOpen;
        this.routeDate = routeDate;
        this.stops = stops;
        this.identifiedCoordinates = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitialRouteId() {
        return this.initialRouteId;
    }

    public void setInitialRouteId(String initialRouteId) {
        this.initialRouteId = initialRouteId;
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

    public float getCost() {
        return this.cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Date getExpiry() {
        return this.expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public boolean isIsOpen() {
        return this.isOpen;
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Date getRouteDate() {
        return this.routeDate;
    }

    public void setRouteDate(Date routeDate) {
        this.routeDate = routeDate;
    }

    public List<Stop> getStops() {
        return this.stops;
    }

    @JsonIgnore
    public String getPassengerId() {
        Stop stop = stops.get(0);
        return stop.getPassengerId();
    }
    

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    } 

    public List<IdentifiedCoordinate> getIdentifiedCoordinates() {
        return identifiedCoordinates;
    }

    public void setIdentifiedCoordinates(List<IdentifiedCoordinate> identifiedCoordinates) {
        this.identifiedCoordinates = identifiedCoordinates;
    }

    public void addIdentifiedCoordinate(IdentifiedCoordinate identifiedCoordinate) {
        if (this.identifiedCoordinates == null) {
            this.identifiedCoordinates = new ArrayList<>();
        }
        this.identifiedCoordinates.add(identifiedCoordinate);
    }

    public Route id(String id) {
        setId(id);
        return this;
    }

    public Route initialRouteId(String initialRouteId) {
        setInitialRouteId(initialRouteId);
        return this;
    }

    public Route driverId(String driverId) {
        setDriverId(driverId);
        return this;
    }

    public Route geometry(RouteGeometry geometry) {
        setGeometry(geometry);
        return this;
    }

    public Route duration(int duration) {
        setDuration(duration);
        return this;
    }

    public Route distance(int distance) {
        setDistance(distance);
        return this;
    }

    public Route seatsAvailable(int seatsAvailable) {
        setSeatsAvailable(seatsAvailable);
        return this;
    }

    public Route maxStopTime(long maxStopTime) {
        setMaxStopTime(maxStopTime);
        return this;
    }

    public Route cost(float cost) {
        setCost(cost);
        return this;
    }

    public Route expiry(Date expiry) {
        setExpiry(expiry);
        return this;
    }

    public Route isOpen(boolean isOpen) {
        setIsOpen(isOpen);
        return this;
    }

    public Route routeDate(Date routeDate) {
        setRouteDate(routeDate);
        return this;
    }

    public Route stops(List<Stop> stops) {
        setStops(stops);
        return this;
    }

    public void addStops(Stop stops){
        this.stops.add(stops);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Route)) {
            return false;
        }
        Route route = (Route) o;
        return Objects.equals(id, route.id) && Objects.equals(initialRouteId, route.initialRouteId) && Objects.equals(driverId, route.driverId) && Objects.equals(geometry, route.geometry) && duration == route.duration && distance == route.distance && seatsAvailable == route.seatsAvailable && maxStopTime == route.maxStopTime && cost == route.cost && Objects.equals(expiry, route.expiry) && isOpen == route.isOpen && Objects.equals(routeDate, route.routeDate) && Objects.equals(stops, route.stops);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, initialRouteId, driverId, geometry, duration, distance, seatsAvailable, maxStopTime, cost, expiry, isOpen, routeDate, stops);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", initialRouteId='" + getInitialRouteId() + "'" +
            ", driverId='" + getDriverId() + "'" +
            ", geometry='" + getGeometry() + "'" +
            ", duration='" + getDuration() + "'" +
            ", distance='" + getDistance() + "'" +
            ", seatsAvailable='" + getSeatsAvailable() + "'" +
            ", maxStopTime='" + getMaxStopTime() + "'" +
            ", cost='" + getCost() + "'" +
            ", expiry='" + getExpiry() + "'" +
            ", isOpen='" + isIsOpen() + "'" +
            ", routeDate='" + getRouteDate() + "'" +
            ", stops='" + getStops() + "'" +
            "}";
    }


}
