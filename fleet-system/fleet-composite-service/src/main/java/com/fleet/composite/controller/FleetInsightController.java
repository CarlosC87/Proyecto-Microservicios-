package com.fleet.composite.controller;

import com.fleet.composite.dto.FleetInsightResponse;
import com.fleet.composite.service.FleetInsightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/fleet-insights")
public class FleetInsightController {

    private final FleetInsightService service;

    public FleetInsightController(FleetInsightService service) {
        this.service = service;
    }

    @GetMapping("/vehicles/{vehicleId}")
    public Mono<FleetInsightResponse> getByVehicle(@PathVariable String vehicleId) {
        return service.getByVehicleId(vehicleId);
    }
}
