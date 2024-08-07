package com.example.user_management.daos;

import com.example.user_management.models.Car;
import com.example.user_management.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarDAO {

    private final CarRepository carRepository;

    @Autowired
    public CarDAO(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> findById(String carId) {
        return carRepository.findById(carId);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public void delete(Car car) {
        carRepository.delete(car);
    }

    public Car findByDriverId(String driverId) {
        return carRepository.findByDriverId(driverId);
    }
}
