package com.fleet.composite.service;

import com.fleet.composite.client.DriverClient;
import com.fleet.composite.client.VehicleClient;
import com.fleet.composite.dto.DriverDto;
import com.fleet.composite.dto.VehicleDto;
import com.fleet.composite.exception.DownstreamUnavailableException;
import com.fleet.composite.exception.VehicleNotFoundException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FleetInsightServiceTest {

    private final VehicleClient vehicleClient = mock(VehicleClient.class);
    private final DriverClient driverClient = mock(DriverClient.class);
    private final FleetInsightService service = new FleetInsightService(vehicleClient, driverClient);

    private static final VehicleDto VEHICLE = new VehicleDto("veh-001", "ABC-1234", "EcoTruck EV", 84.5);
    private static final DriverDto DRIVER = new DriverDto("drv-552", "Elena Vance", "Commercial-A", 98.2);

    @Test
    void mergesVehicleAndDriver() {
        when(vehicleClient.findById("veh-001")).thenReturn(Mono.just(VEHICLE));
        when(driverClient.findByVehicleId("veh-001")).thenReturn(Mono.just(DRIVER));

        StepVerifier.create(service.getByVehicleId("veh-001"))
                .assertNext(r -> {
                    assertThat(r.vehicle().plate()).isEqualTo("ABC-1234");
                    assertThat(r.driver().fullName()).isEqualTo("Elena Vance");
                    assertThat(r.compositeTimestamp()).isNotNull();
                })
                .verifyComplete();
    }

    @Test
    void returnsNullDriverWhenVehicleHasNoDriver() {
        when(vehicleClient.findById("veh-001")).thenReturn(Mono.just(VEHICLE));
        when(driverClient.findByVehicleId("veh-001")).thenReturn(Mono.empty());

        StepVerifier.create(service.getByVehicleId("veh-001"))
                .assertNext(r -> {
                    assertThat(r.vehicle().id()).isEqualTo("veh-001");
                    assertThat(r.driver()).isNull();
                })
                .verifyComplete();
    }

    @Test
    void propagatesVehicleNotFound() {
        when(vehicleClient.findById("nope")).thenReturn(Mono.error(new VehicleNotFoundException("nope")));
        when(driverClient.findByVehicleId("nope")).thenReturn(Mono.empty());

        StepVerifier.create(service.getByVehicleId("nope"))
                .expectError(VehicleNotFoundException.class)
                .verify();
    }

    @Test
    void propagatesDownstreamUnavailable() {
        when(vehicleClient.findById("veh-001")).thenReturn(Mono.just(VEHICLE));
        when(driverClient.findByVehicleId("veh-001"))
                .thenReturn(Mono.error(new DownstreamUnavailableException("driver-service", new RuntimeException())));

        StepVerifier.create(service.getByVehicleId("veh-001"))
                .expectError(DownstreamUnavailableException.class)
                .verify();
    }
}
