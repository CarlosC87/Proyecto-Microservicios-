package com.fleet.composite.dto;

/** Lo que el composite necesita del driver-service (campos extra del JSON se ignoran). */
public record DriverDto(String id, String fullName, String licenseCategory, double safetyScore) {
}
