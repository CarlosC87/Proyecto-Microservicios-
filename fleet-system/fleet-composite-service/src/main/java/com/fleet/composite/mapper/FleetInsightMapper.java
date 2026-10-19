package com.fleet.composite.mapper;

import com.fleet.composite.dto.DriverDto;
import com.fleet.composite.dto.FleetInsightResponse;
import com.fleet.composite.dto.FleetInsightResponse.DriverSummary;
import com.fleet.composite.dto.FleetInsightResponse.VehicleSummary;
import com.fleet.composite.dto.VehicleDto;

import java.time.Instant;

/** Fusiona y limpia: desacopla el contrato público de los DTOs internos de cada servicio. */
public final class FleetInsightMapper {

    private FleetInsightMapper() {
    }

    /** {@code driver} puede ser null (vehículo sin conductor asignado). */
    public static FleetInsightResponse toResponse(VehicleDto vehicle, DriverDto driver, Instant timestamp) {
        VehicleSummary v = new VehicleSummary(
                vehicle.id(), vehicle.plate(), vehicle.model(), vehicle.batteryStatusPercentage());

        DriverSummary d = (driver == null) ? null : new DriverSummary(
                driver.id(), driver.fullName(), driver.licenseCategory(), driver.safetyScore());

        return new FleetInsightResponse(v, d, timestamp);
    }
}
