package com.fleet.composite.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String vehicleId) {
        super("Vehicle not found: " + vehicleId);
    }
}
