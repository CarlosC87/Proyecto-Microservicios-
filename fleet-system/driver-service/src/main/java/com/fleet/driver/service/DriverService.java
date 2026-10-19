package com.fleet.driver.service;

import com.fleet.driver.dto.DriverRequest;
import com.fleet.driver.dto.DriverResponse;
import com.fleet.driver.exception.DriverNotFoundException;
import com.fleet.driver.exception.DuplicateDriverException;
import com.fleet.driver.model.Driver;
import com.fleet.driver.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class DriverService {

    private final DriverRepository repository;

    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    public DriverResponse getById(String id) {
        return repository.findById(id)
                .map(DriverResponse::from)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found: " + id));
    }

    public DriverResponse getByVehicleId(String vehicleId) {
        return repository.findFirstByAssignedVehicleId(vehicleId)
                .map(DriverResponse::from)
                .orElseThrow(() -> new DriverNotFoundException("No driver assigned to vehicle: " + vehicleId));
    }

    public DriverResponse create(DriverRequest request) {
        String id = (request.id() == null || request.id().isBlank())
                ? "drv-" + UUID.randomUUID().toString().substring(0, 8)
                : request.id();

        if (repository.existsById(id)) {
            throw new DuplicateDriverException("Driver id already exists: " + id);
        }

        Driver d = new Driver();
        d.setId(id);
        d.setFullName(request.fullName());
        d.setLicenseCategory(request.licenseCategory());
        d.setLicenseExpiry(request.licenseExpiry());
        d.setSafetyScore(request.safetyScore());
        d.setCertifications(request.certifications() != null ? request.certifications() : new ArrayList<>());
        d.setAssignedVehicleId(request.assignedVehicleId());
        d.setAttributes(request.attributes() != null ? request.attributes() : new HashMap<>());

        return DriverResponse.from(repository.save(d));
    }
}
