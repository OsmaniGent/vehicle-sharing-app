package com.example.user.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.models.Car;
import com.example.user.services.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService){
        this.carService = carService;
    }
    
    @PostMapping("/addCar")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car savedCar = carService.addCar(car);
        
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    @GetMapping("/allCars")
    public ResponseEntity<List<Car>> findAllCars() {
        List<Car> cars = carService.findAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/details/{carId}")
    public ResponseEntity<?>findCar(@PathVariable String carId) {
        return carService.findCar(carId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
