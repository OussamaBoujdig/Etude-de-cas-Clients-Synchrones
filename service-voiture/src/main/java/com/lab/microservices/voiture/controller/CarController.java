package com.lab.microservices.voiture.controller;

import com.lab.microservices.voiture.model.CarResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Car operations.
 * Simulates database access with Thread.sleep for latency testing.
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);

    /**
     * Get cars by client ID.
     * Simulates database latency with Thread.sleep(50ms).
     * 
     * @param clientId The client ID to filter cars
     * @return List of cars belonging to the specified client
     */
    @GetMapping("/byClient/{clientId}")
    public ResponseEntity<List<CarResponse>> getCarsByClient(@PathVariable Long clientId) {
        log.debug("Received request for cars of client: {}", clientId);

        // Simulate database latency (IMPORTANT for JMeter performance testing)
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Thread interrupted during simulated latency");
        }

        // Mock database response - hardcoded cars for the given client
        List<CarResponse> cars = List.of(
                new CarResponse(1L, "Toyota", "Camry", clientId),
                new CarResponse(2L, "Honda", "Accord", clientId),
                new CarResponse(3L, "BMW", "Serie 3", clientId));

        log.debug("Returning {} cars for client: {}", cars.size(), clientId);
        return ResponseEntity.ok(cars);
    }

    /**
     * Health check endpoint for testing.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service Voiture is running!");
    }
}
