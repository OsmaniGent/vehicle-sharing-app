package com.example.routefiltering.daos;

import com.example.routefiltering.models.Route;
import com.example.routefiltering.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;
import java.util.Optional;

@Repository
public class RouteDAO {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteDAO(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> findAllRoutes() {
        return routeRepository.findAll();
    }

    public Optional<Route> findRouteById(String routeId) {
        return routeRepository.findById(routeId);
    }

    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> findRoutesNear(double longitude, double latitude) {
        return routeRepository.findNear(longitude, latitude);
    }

    public List<Route> findExpiredRoutes(Date currentTime) {
        return routeRepository.findExpiredRoutes(currentTime);
    }
}
