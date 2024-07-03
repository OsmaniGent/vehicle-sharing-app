package com.example.user_management.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_management.models.Car;
import com.example.user_management.models.User;
import com.example.user_management.services.CarService;
import com.example.user_management.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    private final UserService userService;

    @Autowired
    public CarController(CarService carService, UserService userService){
        this.carService = carService;
        this.userService = userService;
    }
    
    @PostMapping("/addCar")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car savedCar = carService.addCar(car);
        userService.updateUserRole(savedCar.getDriverId());
        
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

    @GetMapping("/getCarByDriverId/{driverId}")
    public ResponseEntity<Car> findCarByDriverId(@PathVariable String driverId) {
        Car car = carService.findCarByDriverId(driverId);
        return ResponseEntity.ok(car);
    }

}
