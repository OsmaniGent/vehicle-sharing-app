package com.example.user_management.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Car {
    
    @Id
    private String id;

    private String driverId;   

    private String carYear;

    private String carModel;

    private String carMake;

    private int seatsAvailable;


    public Car() {
    }

    public Car(String id, String driverId, String carYear, String carModel, String carMake, int seatsAvailable) {
        this.id = id;
        this.driverId = driverId;
        this.carYear = carYear;
        this.carModel = carModel;
        this.carMake = carMake;
        this.seatsAvailable = seatsAvailable;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverId() {
        return this.driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCarYear() {
        return this.carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getCarModel() {
        return this.carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarMake() {
        return this.carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public int getSeatsAvailable() {
        return this.seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public Car id(String id) {
        setId(id);
        return this;
    }

    public Car driverId(String driverId) {
        setDriverId(driverId);
        return this;
    }

    public Car carYear(String carYear) {
        setCarYear(carYear);
        return this;
    }

    public Car carModel(String carModel) {
        setCarModel(carModel);
        return this;
    }

    public Car carMake(String carMake) {
        setCarMake(carMake);
        return this;
    }

    public Car seatsAvailable(int seatsAvailable) {
        setSeatsAvailable(seatsAvailable);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Car)) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(driverId, car.driverId) && Objects.equals(carYear, car.carYear) && Objects.equals(carModel, car.carModel) && Objects.equals(carMake, car.carMake) && seatsAvailable == car.seatsAvailable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, driverId, carYear, carModel, carMake, seatsAvailable);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", driverId='" + getDriverId() + "'" +
            ", carYear='" + getCarYear() + "'" +
            ", carModel='" + getCarModel() + "'" +
            ", carMake='" + getCarMake() + "'" +
            ", seatsAvailable='" + getSeatsAvailable() + "'" +
            "}";
    }
   
}
