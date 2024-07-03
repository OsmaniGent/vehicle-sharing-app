package com.example.user_management.services;

import com.example.user_management.daos.PendingRouteDAO;
import com.example.user_management.models.PendingRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PendingRouteService {

    private final PendingRouteDAO routeDAO;

    @Autowired
    public PendingRouteService(PendingRouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    public List<PendingRoute> findAllRoutes() {
        return routeDAO.findAll();
    }
    
    public List<PendingRoute> findRoutesByDriverId(String driverId) {
        return routeDAO.findAllByDriverId(driverId);
    }

    public Optional<PendingRoute> findRoute(String routeId) {
        return routeDAO.findById(routeId);
    }

    public PendingRoute addRoute(PendingRoute route) {
        return routeDAO.save(route);
    }

    public List<PendingRoute> findRoutesNear(double longitude, double latitude) {
        return routeDAO.findNear(longitude, latitude);
    }

    public boolean deleteRoute(String routeId) {
        if (routeDAO.existsById(routeId)) {
            routeDAO.deleteById(routeId);
            return true;
        } else {
            return false;
        }
    }
}
