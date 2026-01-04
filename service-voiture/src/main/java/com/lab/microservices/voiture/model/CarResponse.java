package com.lab.microservices.voiture.model;

/**
 * CarResponse model representing a car entity.
 * Uses Java record for immutability and conciseness (Java 17+).
 */
public record CarResponse(
        Long id,
        String brand,
        String model,
        Long clientId) {
    /**
     * Factory method to create a CarResponse for a specific client.
     */
    public static CarResponse forClient(Long id, String brand, String model, Long clientId) {
        return new CarResponse(id, brand, model, clientId);
    }
}
