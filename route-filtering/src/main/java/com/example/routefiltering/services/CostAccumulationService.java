package com.example.routefiltering.services;

import org.springframework.stereotype.Service;

import com.example.routefiltering.models.SegmentCostDTO;

import java.util.HashMap;
import java.util.Map;

@Service
public class CostAccumulationService {
    private final Map<String, Double> accumulatedCosts = new HashMap<>();

    public synchronized Map<String, Double> accumulateAndReturnCosts(SegmentCostDTO segmentCost) {
        double individualCost = segmentCost.getCost() / (segmentCost.getIdentifiers().size()+1);
        segmentCost.getIdentifiers().forEach(identifier -> {
            accumulatedCosts.merge(identifier, individualCost, Double::sum);
        });
        return new HashMap<>(accumulatedCosts);
    }


}