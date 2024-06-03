package com.example.routefiltering.models;
import java.util.Objects;

public class RouteFilterRequest {
    private Route initialRoute;
    private Route updatedRoute;
    private Route subRoute;


    public RouteFilterRequest() {
    }

    public RouteFilterRequest(Route initialRoute, Route updatedRoute, Route subRoute) {
        this.initialRoute = initialRoute;
        this.updatedRoute = updatedRoute;
        this.subRoute = subRoute;
    }

    public Route getInitialRoute() {
        return this.initialRoute;
    }

    public void setInitialRoute(Route initialRoute) {
        this.initialRoute = initialRoute;
    }

    public Route getUpdatedRoute() {
        return this.updatedRoute;
    }

    public void setUpdatedRoute(Route updatedRoute) {
        this.updatedRoute = updatedRoute;
    }

    public Route getSubRoute() {
        return this.subRoute;
    }

    public void setSubRoute(Route subRoute) {
        this.subRoute = subRoute;
    }

    public RouteFilterRequest initialRoute(Route initialRoute) {
        setInitialRoute(initialRoute);
        return this;
    }

    public RouteFilterRequest updatedRoute(Route updatedRoute) {
        setUpdatedRoute(updatedRoute);
        return this;
    }

    public RouteFilterRequest subRoute(Route subRoute) {
        setSubRoute(subRoute);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RouteFilterRequest)) {
            return false;
        }
        RouteFilterRequest routeFilterRequest = (RouteFilterRequest) o;
        return Objects.equals(initialRoute, routeFilterRequest.initialRoute) && Objects.equals(updatedRoute, routeFilterRequest.updatedRoute) && Objects.equals(subRoute, routeFilterRequest.subRoute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialRoute, updatedRoute, subRoute);
    }

    @Override
    public String toString() {
        return "{" +
            " initialRoute='" + getInitialRoute() + "'" +
            ", updatedRoute='" + getUpdatedRoute() + "'" +
            ", subRoute='" + getSubRoute() + "'" +
            "}";
    }
    
}