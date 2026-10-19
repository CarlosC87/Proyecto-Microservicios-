package com.fleet.driver.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "drivers")
public class Driver {

    @Id
    private String id;

    private String fullName;
    private String licenseCategory;
    private LocalDate licenseExpiry;
    private double safetyScore;
    private List<String> certifications = new ArrayList<>();

    /** Referencia por ID al vehículo (NO es FK: la tabla vive en otra base de datos). */
    @Indexed
    private String assignedVehicleId;

    /** Atributos dinámicos / bitácora: la razón de ser de usar MongoDB aquí. */
    private Map<String, Object> attributes = new HashMap<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getLicenseCategory() { return licenseCategory; }
    public void setLicenseCategory(String licenseCategory) { this.licenseCategory = licenseCategory; }
    public LocalDate getLicenseExpiry() { return licenseExpiry; }
    public void setLicenseExpiry(LocalDate licenseExpiry) { this.licenseExpiry = licenseExpiry; }
    public double getSafetyScore() { return safetyScore; }
    public void setSafetyScore(double safetyScore) { this.safetyScore = safetyScore; }
    public List<String> getCertifications() { return certifications; }
    public void setCertifications(List<String> certifications) { this.certifications = certifications; }
    public String getAssignedVehicleId() { return assignedVehicleId; }
    public void setAssignedVehicleId(String assignedVehicleId) { this.assignedVehicleId = assignedVehicleId; }
    public Map<String, Object> getAttributes() { return attributes; }
    public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }
}
