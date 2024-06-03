package com.example.routefiltering.models;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentifiedCoordinate {
    private List<Double> coordinate;
    private List<String> identifiers;

    @JsonCreator
    public IdentifiedCoordinate(
        @JsonProperty("coordinate") List<Double> coordinate, 
        @JsonProperty("identifiers") List<String> identifiers) {
        this.coordinate = coordinate;
        this.identifiers = identifiers != null ? identifiers : new ArrayList<>();
    }

    public List<Double> getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(List<Double> coordinate) {
        this.coordinate = coordinate;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    public void addIdentifier(String identifier) {
        if (identifiers == null) {
            identifiers = new ArrayList<>();
        }
        identifiers.add(identifier);
    }
}
