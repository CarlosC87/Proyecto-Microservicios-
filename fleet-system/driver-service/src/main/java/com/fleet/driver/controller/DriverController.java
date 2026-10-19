package com.fleet.driver.controller;

import com.fleet.driver.dto.DriverRequest;
import com.fleet.driver.dto.DriverResponse;
import com.fleet.driver.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService service;

    public DriverController(DriverService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public DriverResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

    /** Endpoint que consume el composite. 404 si el vehículo no tiene conductor asignado. */
    @GetMapping("/by-vehicle/{vehicleId}")
    public DriverResponse getByVehicle(@PathVariable String vehicleId) {
        return service.getByVehicleId(vehicleId);
    }

    @PostMapping
    public ResponseEntity<DriverResponse> create(@Valid @RequestBody DriverRequest request) {
        DriverResponse created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }
}
