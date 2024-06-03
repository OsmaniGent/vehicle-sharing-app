package com.example.user.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.user.models.Car;

public interface CarRepository extends MongoRepository<Car, String> {

}