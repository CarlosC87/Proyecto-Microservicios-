package com.fleet.composite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FleetCompositeApplication {
    public static void main(String[] args) {
        SpringApplication.run(FleetCompositeApplication.class, args);
    }
}
