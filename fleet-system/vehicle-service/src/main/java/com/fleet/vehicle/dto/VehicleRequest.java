package com.fleet.vehicle.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

/** Si {@code id} viene vacío, el servicio genera uno ("veh-xxxxxxxx"). */
public record VehicleRequest(
        String id,
        @NotBlank String plate,
        @NotBlank String model,
        @DecimalMin("0.0") @DecimalMax("100.0") double batteryStatusPercentage,
        String engineStatus,
        Double lastLatitude,
        Double lastLongitude) {
}
