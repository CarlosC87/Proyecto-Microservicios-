package com.fleet.driver.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/** Si {@code id} viene vacío, el servicio genera uno ("drv-xxxxxxxx"). */
public record DriverRequest(
        String id,
        @NotBlank String fullName,
        @NotBlank String licenseCategory,
        LocalDate licenseExpiry,
        @DecimalMin("0.0") @DecimalMax("100.0") double safetyScore,
        List<String> certifications,
        String assignedVehicleId,
        Map<String, Object> attributes) {
}
