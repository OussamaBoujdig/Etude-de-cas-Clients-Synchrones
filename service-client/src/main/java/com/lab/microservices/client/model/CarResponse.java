package com.lab.microservices.client.model;

/**
 * CarResponse DTO matching the response from service-voiture.
 * Using Java record for immutability (Java 17+).
 */
public record CarResponse(
        Long id,
        String brand,
        String model,
        Long clientId) {
}
