package com.example.routefiltering.controllers;

import com.example.routefiltering.models.SegmentCostDTO;
import com.example.routefiltering.services.CostAccumulationService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/segment")
public class RouteSegmentController {

    @Autowired
    private CostAccumulationService costService;

    @PostMapping("/cost")
    public ResponseEntity<Map<String, Double>> calculateIndividualCosts(@RequestBody SegmentCostDTO segmentCost) {
        return ResponseEntity.ok(costService.accumulateAndReturnCosts(segmentCost));
    }
}