package com.fleet.composite.client;

import com.fleet.composite.dto.VehicleDto;
import com.fleet.composite.exception.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class VehicleClient {

    private final WebClient webClient;

    public VehicleClient(@Qualifier("vehicleWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /** 404 del servicio -> VehicleNotFoundException. Fallo de infraestructura -> DownstreamUnavailableException. */
    public Mono<VehicleDto> findById(String vehicleId) {
        Mono<VehicleDto> call = webClient.get()
                .uri("/vehicles/{id}", vehicleId)
                .retrieve()
                .bodyToMono(VehicleDto.class)
                .onErrorResume(WebClientResponseException.NotFound.class,
                        e -> Mono.error(new VehicleNotFoundException(vehicleId)));
        return DownstreamResilience.apply(call, "vehicle-service");
    }
}
