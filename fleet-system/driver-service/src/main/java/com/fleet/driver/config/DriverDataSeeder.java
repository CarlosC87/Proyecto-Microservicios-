package com.fleet.driver.config;

import com.fleet.driver.dto.DriverRequest;
import com.fleet.driver.repository.DriverRepository;
import com.fleet.driver.service.DriverService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/** Datos de ejemplo: el conductor drv-552 asignado al vehículo veh-001. */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class DriverDataSeeder implements CommandLineRunner {

    private final DriverRepository repository;
    private final DriverService service;

    public DriverDataSeeder(DriverRepository repository, DriverService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    public void run(String... args) {
        if (!repository.existsById("drv-552")) {
            service.create(new DriverRequest(
                    "drv-552", "Elena Vance", "Commercial-A", LocalDate.of(2028, 6, 30), 98.2,
                    List.of("HAZMAT", "EV-Safety"), "veh-001",
                    Map.of("shiftPreference", "morning")));
        }
    }
}
