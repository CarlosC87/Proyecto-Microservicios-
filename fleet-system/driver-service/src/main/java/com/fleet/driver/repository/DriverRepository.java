package com.fleet.driver.repository;

import com.fleet.driver.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {
    Optional<Driver> findFirstByAssignedVehicleId(String assignedVehicleId);
}
