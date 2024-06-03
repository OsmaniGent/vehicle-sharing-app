package com.example.vehiclefuel.models;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;


public class VehicleOption {

    
    @JsonProperty("text")
    private String option;

    @JsonProperty("value")
    private String id;


    public VehicleOption() {
    }

    public VehicleOption(String option, String id) {
        this.option = option;
        this.id = id;
    }

    public String getOption() {
        return this.option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VehicleOption option(String option) {
        setOption(option);
        return this;
    }

    public VehicleOption id(String id) {
        setId(id);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof VehicleOption)) {
            return false;
        }
        VehicleOption vehicleOption = (VehicleOption) o;
        return Objects.equals(option, vehicleOption.option) && Objects.equals(id, vehicleOption.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(option, id);
    }

    @Override
    public String toString() {
        return "{" +
            " option='" + getOption() + "'" +
            ", id='" + getId() + "'" +
            "}";
    }
    
}
