package com.example.user_management.daos;

import com.example.user_management.models.PendingRoute;
import com.example.user_management.repositories.PendingRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PendingRouteDAO {

    private final PendingRouteRepository routeRepository;

    @Autowired
    public PendingRouteDAO(PendingRouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<PendingRoute> findAll() {
        return routeRepository.findAll();
    }

    public List<PendingRoute> findAllByDriverId(String driverId) {
        return routeRepository.findByDriverId(driverId);
    }

    public Optional<PendingRoute> findById(String routeId) {
        return routeRepository.findById(routeId);
    }

    public PendingRoute save(PendingRoute route) {
        return routeRepository.save(route);
    }

    public List<PendingRoute> findNear(double longitude, double latitude) {
        return routeRepository.findNear(longitude, latitude);
    }

    public boolean existsById(String routeId) {
        return routeRepository.existsById(routeId);
    }

    public void deleteById(String routeId) {
        routeRepository.deleteById(routeId);
    }
}
