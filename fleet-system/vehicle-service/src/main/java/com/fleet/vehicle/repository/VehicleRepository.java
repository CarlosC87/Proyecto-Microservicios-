package com.fleet.vehicle.repository;

import com.fleet.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    boolean existsByPlate(String plate);
}
