package com.fleet.driver.dto;

import com.fleet.driver.model.Driver;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record DriverResponse(
        String id,
        String fullName,
        String licenseCategory,
        LocalDate licenseExpiry,
        double safetyScore,
        List<String> certifications,
        String assignedVehicleId,
        Map<String, Object> attributes) {

    public static DriverResponse from(Driver d) {
        return new DriverResponse(
                d.getId(), d.getFullName(), d.getLicenseCategory(), d.getLicenseExpiry(),
                d.getSafetyScore(), d.getCertifications(), d.getAssignedVehicleId(), d.getAttributes());
    }
}
