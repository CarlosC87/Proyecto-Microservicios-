package com.fleet.vehicle.dto;

import com.fleet.vehicle.model.Vehicle;

import java.time.Instant;

public record VehicleResponse(
        String id,
        String plate,
        String model,
        double batteryStatusPercentage,
        String engineStatus,
        Double lastLatitude,
        Double lastLongitude,
        Instant lastTelemetryAt) {

    public static VehicleResponse from(Vehicle v) {
        return new VehicleResponse(
                v.getId(), v.getPlate(), v.getModel(), v.getBatteryStatusPercentage(),
                v.getEngineStatus(), v.getLastLatitude(), v.getLastLongitude(), v.getLastTelemetryAt());
    }
}
