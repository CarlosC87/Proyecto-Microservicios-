package com.fleet.composite.dto;

import java.time.Instant;

/** Contrato público consolidado que recibe el cliente web. */
public record FleetInsightResponse(
        VehicleSummary vehicle,
        DriverSummary driver,
        Instant compositeTimestamp) {

    public record VehicleSummary(String id, String plate, String model, double batteryStatusPercentage) {
    }

    public record DriverSummary(String id, String fullName, String licenseCategory, double safetyScore) {
    }
}
