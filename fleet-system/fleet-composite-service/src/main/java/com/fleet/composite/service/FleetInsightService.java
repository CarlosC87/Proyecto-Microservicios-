package com.fleet.composite.service;

import com.fleet.composite.client.DriverClient;
import com.fleet.composite.client.VehicleClient;
import com.fleet.composite.dto.DriverDto;
import com.fleet.composite.dto.FleetInsightResponse;
import com.fleet.composite.dto.VehicleDto;
import com.fleet.composite.mapper.FleetInsightMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Optional;

@Service
public class FleetInsightService {

    private final VehicleClient vehicleClient;
    private final DriverClient driverClient;

    public FleetInsightService(VehicleClient vehicleClient, DriverClient driverClient) {
        this.vehicleClient = vehicleClient;
        this.driverClient = driverClient;
    }

    public Mono<FleetInsightResponse> getByVehicleId(String vehicleId) {
        Mono<VehicleDto> vehicle = vehicleClient.findById(vehicleId);

        // Un Mono vacío haría que zip() cancele todo; lo envolvemos en Optional para tolerar "sin conductor".
        Mono<Optional<DriverDto>> driver = driverClient.findByVehicleId(vehicleId)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty());

        // zip se suscribe a ambos a la vez -> las dos llamadas HTTP corren en paralelo.
        return Mono.zip(vehicle, driver)
                .map(tuple -> FleetInsightMapper.toResponse(
                        tuple.getT1(), tuple.getT2().orElse(null), Instant.now()));
    }
}
