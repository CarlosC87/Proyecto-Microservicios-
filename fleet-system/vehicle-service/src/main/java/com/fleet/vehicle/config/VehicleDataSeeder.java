package com.fleet.vehicle.config;

import com.fleet.vehicle.dto.VehicleRequest;
import com.fleet.vehicle.service.VehicleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/** Datos de ejemplo para poder probar el flujo completo sin cargar nada a mano. */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class VehicleDataSeeder implements CommandLineRunner {

    private final VehicleService service;

    public VehicleDataSeeder(VehicleService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        try {
            service.getById("veh-001");
        } catch (RuntimeException notFound) {
            service.create(new VehicleRequest(
                    "veh-001", "ABC-1234", "EcoTruck EV", 84.5, "OK", 28.6353, -106.0889));
        }
    }
}
