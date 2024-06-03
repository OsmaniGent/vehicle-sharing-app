package com.example.user.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.user.models.PendingRoute;

public interface PendingRouteRepository extends MongoRepository<PendingRoute, String> {

    @Query("{ 'geometry': { $near: { $geometry: { type: 'LineString', coordinates: [ ?0, ?1 ] } } } }")
    List<PendingRoute> findNear(double longitude, double latitude);

}
