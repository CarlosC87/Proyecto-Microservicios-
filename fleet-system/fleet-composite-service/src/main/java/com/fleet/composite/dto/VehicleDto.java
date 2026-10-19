package com.fleet.composite.dto;

/** Lo que el composite necesita del vehicle-service (campos extra del JSON se ignoran). */
public record VehicleDto(String id, String plate, String model, double batteryStatusPercentage) {
}
