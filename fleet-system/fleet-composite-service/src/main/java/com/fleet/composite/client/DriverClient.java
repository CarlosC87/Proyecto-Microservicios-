package com.fleet.composite.client;

import com.fleet.composite.dto.DriverDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class DriverClient {

    private final WebClient webClient;

    public DriverClient(@Qualifier("driverWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /** 404 del servicio -> Mono vacío (vehículo sin conductor). Fallo de infraestructura -> DownstreamUnavailableException. */
    public Mono<DriverDto> findByVehicleId(String vehicleId) {
        Mono<DriverDto> call = webClient.get()
                .uri("/drivers/by-vehicle/{vehicleId}", vehicleId)
                .retrieve()
                .bodyToMono(DriverDto.class)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> Mono.empty());
        return DownstreamResilience.apply(call, "driver-service");
    }
}
