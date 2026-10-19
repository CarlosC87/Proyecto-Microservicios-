package com.fleet.vehicle.service;

import com.fleet.vehicle.dto.VehicleRequest;
import com.fleet.vehicle.dto.VehicleResponse;
import com.fleet.vehicle.exception.DuplicateVehicleException;
import com.fleet.vehicle.exception.VehicleNotFoundException;
import com.fleet.vehicle.model.Vehicle;
import com.fleet.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public VehicleResponse getById(String id) {
        return repository.findById(id)
                .map(VehicleResponse::from)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }

    @Transactional
    public VehicleResponse create(VehicleRequest request) {
        String id = (request.id() == null || request.id().isBlank())
                ? "veh-" + UUID.randomUUID().toString().substring(0, 8)
                : request.id();

        if (repository.existsById(id)) {
            throw new DuplicateVehicleException("Vehicle id already exists: " + id);
        }
        if (repository.existsByPlate(request.plate())) {
            throw new DuplicateVehicleException("Plate already registered: " + request.plate());
        }

        Vehicle v = new Vehicle();
        v.setId(id);
        v.setPlate(request.plate());
        v.setModel(request.model());
        v.setBatteryStatusPercentage(request.batteryStatusPercentage());
        v.setEngineStatus(request.engineStatus());
        v.setLastLatitude(request.lastLatitude());
        v.setLastLongitude(request.lastLongitude());
        v.setLastTelemetryAt(Instant.now());

        return VehicleResponse.from(repository.save(v));
    }
}
