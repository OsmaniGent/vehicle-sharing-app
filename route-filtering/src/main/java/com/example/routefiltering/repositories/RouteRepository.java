package com.example.routefiltering.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.routefiltering.models.Route;


public interface RouteRepository extends MongoRepository<Route, String> {

    @Query("{ 'geometry': { $near: { $geometry: { type: 'LineString', coordinates: [ ?0, ?1 ] } } } }")
    List<Route> findNear(double longitude, double latitude);

    @Query("{ 'expiry': { $lte: ?0 }, 'isOpen': true }")
    List<Route> findExpiredRoutes(Date currentTime);
    
}
