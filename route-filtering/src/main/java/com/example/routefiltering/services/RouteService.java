package com.example.routefiltering.services;

import com.example.routefiltering.daos.RouteDAO;
import com.example.routefiltering.models.IdentifiedCoordinate;
import com.example.routefiltering.models.Route;
import com.example.routefiltering.models.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final RouteDAO routeDAO;

    @Autowired
    public RouteService(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    public List<Route> findAllRoutes() {
        return routeDAO.findAllRoutes();
    }

    public Optional<Route> findRoute(String routeId) {
        return routeDAO.findRouteById(routeId);
    }

    public Route addRoute(Route route) {
        return routeDAO.saveRoute(route);
    }

    public List<Route> findRoutesNear(double longitude, double latitude) {
        return routeDAO.findRoutesNear(longitude, latitude);
    }

    public Route updateRoute(String routeId, Route updatedRoute) {
        Optional<Route> optionalRoute = routeDAO.findRouteById(routeId);
        if (!optionalRoute.isPresent()) {
            throw new RuntimeException("Route not found with ID: " + routeId);
        }
        Route route = optionalRoute.get();

        if (route.getStops() != null) {
            List<Stop> existingStops = route.getStops();
            Set<String> existingPassengerIds = existingStops.stream()
                    .map(Stop::getPassengerId)
                    .collect(Collectors.toSet());

            List<Stop> newStops = updatedRoute.getStops().stream()
                    .filter(stop -> !existingPassengerIds.contains(stop.getPassengerId()))
                    .collect(Collectors.toList());

            route.getStops().addAll(newStops);
        } else {
            route.setStops(updatedRoute.getStops());
        }

        route.setSeatsAvailable(updatedRoute.getSeatsAvailable());
        route.setCost(updatedRoute.getCost());
        route.setExpiry(updatedRoute.getExpiry());
        route.setIsOpen(updatedRoute.isIsOpen());

        mergeIdentifiedCoordinates(route, updatedRoute);

        if (route.getSeatsAvailable() == 0) {
            route.setIsOpen(false);
        }

        return routeDAO.saveRoute(route);
    }

    public Route calculateOverlap(Route upRoute, Route subRoute) {
        List<List<Double>> upRouteCoords = upRoute.getGeometry().getCoordinates();
        List<List<Double>> subRouteCoords = subRoute.getGeometry().getCoordinates();
        List<IdentifiedCoordinate> identifiedCoordinates = upRoute.getIdentifiedCoordinates();

        if (identifiedCoordinates == null || identifiedCoordinates.isEmpty()) {
            identifiedCoordinates = new ArrayList<>();
            for (List<Double> coord : upRouteCoords) {
                identifiedCoordinates.add(new IdentifiedCoordinate(coord, new ArrayList<>()));
            }
            upRoute.setIdentifiedCoordinates(identifiedCoordinates);
        }

        String identifier = upRoute.getPassengerId();
        if (identifier == null || identifier.trim().isEmpty()) {
            System.out.println("Warning: Identifier is null or empty.");
            identifier = "defaultIdentifier";
        }

        for (List<Double> coordUp : upRouteCoords) {
            for (List<Double> coordSub : subRouteCoords) {
                if (coordUp.equals(coordSub)) {
                    IdentifiedCoordinate identifiedCoord = findIdentifiedCoordinateByIdentifiedCoords(identifiedCoordinates, coordUp);
                    if (identifiedCoord != null) {
                        identifiedCoord.addIdentifier(identifier);
                        System.out.println("Adding identifier '" + identifier + "' to coordinate " + coordUp);
                    } else {
                        System.out.println("No matching identified coordinate found for " + coordUp);
                    }
                }
            }
        }

        return upRoute;
    }

    private IdentifiedCoordinate findIdentifiedCoordinateByIdentifiedCoords(List<IdentifiedCoordinate> identifiedCoordinates, List<Double> coord) {
        for (IdentifiedCoordinate identifiedCoord : identifiedCoordinates) {
            if (identifiedCoord.getCoordinate().equals(coord)) {
                return identifiedCoord;
            }
        }
        return null;
    }

    private void mergeIdentifiedCoordinates(Route existingRoute, Route updatedRoute) {
        List<IdentifiedCoordinate> existingCoords = existingRoute.getIdentifiedCoordinates();
        List<IdentifiedCoordinate> updatedCoords = updatedRoute.getIdentifiedCoordinates();

        if (existingCoords == null) {
            existingRoute.setIdentifiedCoordinates(new ArrayList<>(updatedCoords));
        } else if (updatedCoords != null) {
            Map<List<Double>, IdentifiedCoordinate> map = new LinkedHashMap<>();
            for (IdentifiedCoordinate coord : existingCoords) {
                map.put(coord.getCoordinate(), coord);
            }

            for (IdentifiedCoordinate updatedCoord : updatedCoords) {
                IdentifiedCoordinate existingCoord = map.get(updatedCoord.getCoordinate());
                if (existingCoord != null) {
                    existingCoord.getIdentifiers().addAll(updatedCoord.getIdentifiers());
                } else {
                    map.put(updatedCoord.getCoordinate(), updatedCoord);
                }
            }
            existingRoute.setIdentifiedCoordinates(new ArrayList<>(map.values()));
        }
    }

    public List<List<IdentifiedCoordinate>> segmentIdentifiedCoordinates(List<IdentifiedCoordinate> identifiedCoordinates) {
        List<List<IdentifiedCoordinate>> segmentedCoordinates = new ArrayList<>();
        if (identifiedCoordinates == null || identifiedCoordinates.isEmpty()) {
            return segmentedCoordinates;
        }

        List<IdentifiedCoordinate> currentSegment = new ArrayList<>();
        int currentIdentifierCount = -1;

        for (IdentifiedCoordinate coord : identifiedCoordinates) {
            if (currentIdentifierCount != coord.getIdentifiers().size()) {
                if (!currentSegment.isEmpty()) {
                    segmentedCoordinates.add(new ArrayList<>(currentSegment));
                    currentSegment.clear();
                }
                currentIdentifierCount = coord.getIdentifiers().size();
            }
            currentSegment.add(coord);
        }

        if (!currentSegment.isEmpty()) {
            segmentedCoordinates.add(currentSegment);
        }

        return segmentedCoordinates;
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
