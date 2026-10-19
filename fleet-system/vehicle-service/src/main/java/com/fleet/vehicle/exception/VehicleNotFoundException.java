package com.fleet.vehicle.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String id) {
        super("Vehicle not found: " + id);
    }
}
