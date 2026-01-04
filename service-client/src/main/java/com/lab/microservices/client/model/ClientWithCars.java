package com.lab.microservices.client.model;

import java.util.List;

/**
 * ClientWithCars DTO combining client info with their cars.
 */
public record ClientWithCars(
        Long clientId,
        String clientName,
        String method,
        List<CarResponse> cars) {
    /**
     * Factory method for creating response with method info.
     */
    public static ClientWithCars of(Long clientId, String method, List<CarResponse> cars) {
        return new ClientWithCars(clientId, "Client-" + clientId, method, cars);
    }
}
