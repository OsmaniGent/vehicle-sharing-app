package com.example.vehiclefuel.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vehiclefuel.models.MpgData;
import com.example.vehiclefuel.models.VehicleOption;
import com.example.vehiclefuel.models.VehicleOptionsRequest;
import com.example.vehiclefuel.services.FuelEconomyService;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final FuelEconomyService fuelEconomyClient;

    public VehicleController(FuelEconomyService fuelEconomyClient) {
        this.fuelEconomyClient = fuelEconomyClient;
    }

    @GetMapping("/options")
    public List<VehicleOption> getVehicleOptions(@RequestParam int year, @RequestParam String make, @RequestParam String model) {
        return fuelEconomyClient.getVehicleOptions(new VehicleOptionsRequest(year, make, model));
    }

    @GetMapping("/years")
    public List<VehicleOption> getYears() {
        return fuelEconomyClient.getYears();
    }

    @GetMapping("/makes")
    public List<VehicleOption> getMakes(@RequestParam int year) {
        return fuelEconomyClient.getMakes(year);
    }

    @GetMapping("/models")
    public List<VehicleOption> getModels(@RequestParam int year, @RequestParam String make) {
        return fuelEconomyClient.getModels(year, make);
    }

    @GetMapping("/mpg")
    public ResponseEntity<?> getVehicleMpg(@RequestParam String year, @RequestParam String make, @RequestParam String model) {
        try {
            String vehicleId = fuelEconomyClient.getVehicleId(year, make, model);
            if (vehicleId == null) {
                return ResponseEntity.notFound().build();
            }
            MpgData mpgData = fuelEconomyClient.getVehicleMpg(vehicleId);
            return ResponseEntity.ok(mpgData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching MPG data");
        }
    }
}
