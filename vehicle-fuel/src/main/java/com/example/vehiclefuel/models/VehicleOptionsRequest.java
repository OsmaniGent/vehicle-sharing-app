package com.example.vehiclefuel.models;
import java.util.Objects;

public class VehicleOptionsRequest {
    private int year;
    private String make;
    private String model;


    public VehicleOptionsRequest() {
    }

    public VehicleOptionsRequest(int year, String make, String model) {
        this.year = year;
        this.make = make;
        this.model = model;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleOptionsRequest year(int year) {
        setYear(year);
        return this;
    }

    public VehicleOptionsRequest make(String make) {
        setMake(make);
        return this;
    }

    public VehicleOptionsRequest model(String model) {
        setModel(model);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof VehicleOptionsRequest)) {
            return false;
        }
        VehicleOptionsRequest vehicleOptionsRequest = (VehicleOptionsRequest) o;
        return year == vehicleOptionsRequest.year && Objects.equals(make, vehicleOptionsRequest.make) && Objects.equals(model, vehicleOptionsRequest.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, make, model);
    }

    @Override
    public String toString() {
        return "{" +
            " year='" + getYear() + "'" +
            ", make='" + getMake() + "'" +
            ", model='" + getModel() + "'" +
            "}";
    }
    

}