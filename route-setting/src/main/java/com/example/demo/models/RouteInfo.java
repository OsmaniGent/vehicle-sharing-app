package com.example.demo.models;

public class RouteInfo {
    private String start;
    private String end;

    public RouteInfo(String start, String end) {
        this.start = start;
        this.end = end;
    }

    // Getters and Setters
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
