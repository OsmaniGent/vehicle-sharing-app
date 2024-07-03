package com.example.user_management.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.user_management.models.Car;

public interface CarRepository extends MongoRepository<Car, String> {

    Car findByDriverId(String driverId);

}