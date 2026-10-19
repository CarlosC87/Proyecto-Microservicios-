package com.fleet.vehicle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    private String id;

    @Column(nullable = false, unique = true, length = 20)
    private String plate;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private double batteryStatusPercentage;

    private String engineStatus;
    private Double lastLatitude;
    private Double lastLongitude;
    private Instant lastTelemetryAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public double getBatteryStatusPercentage() { return batteryStatusPercentage; }
    public void setBatteryStatusPercentage(double v) { this.batteryStatusPercentage = v; }
    public String getEngineStatus() { return engineStatus; }
    public void setEngineStatus(String engineStatus) { this.engineStatus = engineStatus; }
    public Double getLastLatitude() { return lastLatitude; }
    public void setLastLatitude(Double lastLatitude) { this.lastLatitude = lastLatitude; }
    public Double getLastLongitude() { return lastLongitude; }
    public void setLastLongitude(Double lastLongitude) { this.lastLongitude = lastLongitude; }
    public Instant getLastTelemetryAt() { return lastTelemetryAt; }
    public void setLastTelemetryAt(Instant lastTelemetryAt) { this.lastTelemetryAt = lastTelemetryAt; }
}
