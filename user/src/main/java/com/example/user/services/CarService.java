package com.example.user.services;

import com.example.user.daos.CarDAO;
import com.example.user.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarDAO carDAO;

    @Autowired
    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public Car addCar(Car car) {
        return carDAO.save(car);
    }

    public Optional<Car> findCar(String carId) {
        return carDAO.findById(carId);
    }

    public List<Car> findAllCars() {
        return carDAO.findAll();
    }

    public Car deleteCar(String carId) {
        Optional<Car> optionalCar = findCar(carId);
        if (!optionalCar.isPresent()) {
            throw new RuntimeException("Car not found with ID: " + carId);
        }
        Car car = optionalCar.get();
        carDAO.delete(car);
        return car;
    }
}
