package com.example.routefiltering.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RouteEventDTO {
    private String routeId;
    private String driverId;
    private List<String> passengerIds;
    private Date routeDate;


    public RouteEventDTO() {
    }

    public RouteEventDTO(String routeId, String driverId, List<String> passengerIds, Date routeDate) {
        this.routeId = routeId;
        this.driverId = driverId;
        this.passengerIds = passengerIds;
        this.routeDate = routeDate;
    }

    public String getRouteId() {
        return this.routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getDriverId() {
        return this.driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public List<String> getPassengerIds() {
        return this.passengerIds;
    }

    public void setPassengerIds(List<String> passengerIds) {
        this.passengerIds = passengerIds;
    }

    public Date getRouteDate() {
        return this.routeDate;
    }

    public void setRouteDate(Date routeDate) {
        this.routeDate = routeDate;
    }

    public RouteEventDTO routeId(String routeId) {
        setRouteId(routeId);
        return this;
    }

    public RouteEventDTO driverId(String driverId) {
        setDriverId(driverId);
        return this;
    }

    public RouteEventDTO passengerIds(List<String> passengerIds) {
        setPassengerIds(passengerIds);
        return this;
    }

    public RouteEventDTO routeDate(Date routeDate) {
        setRouteDate(routeDate);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RouteEventDTO)) {
            return false;
        }
        RouteEventDTO routeEventDTO = (RouteEventDTO) o;
        return Objects.equals(routeId, routeEventDTO.routeId) && Objects.equals(driverId, routeEventDTO.driverId) && Objects.equals(passengerIds, routeEventDTO.passengerIds) && Objects.equals(routeDate, routeEventDTO.routeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, driverId, passengerIds, routeDate);
    }

    @Override
    public String toString() {
        return "{" +
            " routeId='" + getRouteId() + "'" +
            ", driverId='" + getDriverId() + "'" +
            ", passengerIds='" + getPassengerIds() + "'" +
            ", routeDate='" + getRouteDate() + "'" +
            "}";
    }
    
}
