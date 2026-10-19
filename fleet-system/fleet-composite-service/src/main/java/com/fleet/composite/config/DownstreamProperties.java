package com.fleet.composite.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "downstream")
public record DownstreamProperties(String vehicleUrl, String driverUrl, Duration timeout) {
}
