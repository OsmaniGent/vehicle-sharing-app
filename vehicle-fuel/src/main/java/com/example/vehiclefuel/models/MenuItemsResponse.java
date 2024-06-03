package com.example.vehiclefuel.models;

import java.util.List;

public class MenuItemsResponse {
    private List<VehicleOption> menuItem;

    public List<VehicleOption> getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(List<VehicleOption> menuItem) {
        this.menuItem = menuItem;
    }
}
