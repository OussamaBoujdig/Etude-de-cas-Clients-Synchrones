package com.lab.microservices.client.controller;

import com.lab.microservices.client.feign.CarServiceFeignClient;
import com.lab.microservices.client.model.CarResponse;
import com.lab.microservices.client.model.ClientWithCars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Client Controller demonstrating three HTTP client approaches:
 * 1. RestTemplate (synchronous, load-balanced)
 * 2. OpenFeign (declarative, load-balanced)
 * 3. WebClient (reactive, used in blocking mode with .block())
 * 
 * All endpoints fetch car data from service-voiture for comparison.
 * Use JMeter to load test and compare performance characteristics.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private static final String CAR_SERVICE_URL = "http://service-voiture/api/cars/byClient/";

    private final RestTemplate restTemplate;
    private final CarServiceFeignClient feignClient;
    private final WebClient webClient;

    public ClientController(RestTemplate restTemplate,
            CarServiceFeignClient feignClient,
            WebClient webClient) {
        this.restTemplate = restTemplate;
        this.feignClient = feignClient;
        this.webClient = webClient;
    }

    // =========================================================================
    // ENDPOINT 1: RestTemplate (Load-Balanced)
    // =========================================================================

    /**
     * Fetches cars using RestTemplate.
     * 
     * Characteristics:
     * - Synchronous blocking calls
     * - Simple and widely used
     * - Load-balanced via @LoadBalanced annotation
     * - Good for traditional Spring MVC applications
     * 
     * @param id Client ID
     * @return Client with their cars
     */
    @GetMapping("/{id}/car/rest")
    public ResponseEntity<ClientWithCars> getCarsByRestTemplate(@PathVariable Long id) {
        log.info("[RestTemplate] Fetching cars for client: {}", id);
        long startTime = System.currentTimeMillis();

        ResponseEntity<List<CarResponse>> response = restTemplate.exchange(
                CAR_SERVICE_URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarResponse>>() {
                });

        List<CarResponse> cars = response.getBody();
        long duration = System.currentTimeMillis() - startTime;
        log.info("[RestTemplate] Completed in {}ms, found {} cars", duration,
                cars != null ? cars.size() : 0);

        return ResponseEntity.ok(ClientWithCars.of(id, "RestTemplate", cars));
    }

    // =========================================================================
    // ENDPOINT 2: OpenFeign (Declarative)
    // =========================================================================

    /**
     * Fetches cars using OpenFeign.
     * 
     * Characteristics:
     * - Declarative interface-based client
     * - Automatic serialization/deserialization
     * - Built-in load balancing and circuit breaker support
     * - Clean, readable code
     * - Best for microservices communication
     * 
     * @param id Client ID
     * @return Client with their cars
     */
    @GetMapping("/{id}/car/feign")
    public ResponseEntity<ClientWithCars> getCarsByFeign(@PathVariable Long id) {
        log.info("[Feign] Fetching cars for client: {}", id);
        long startTime = System.currentTimeMillis();

        List<CarResponse> cars = feignClient.getCarsByClient(id);

        long duration = System.currentTimeMillis() - startTime;
        log.info("[Feign] Completed in {}ms, found {} cars", duration, cars.size());

        return ResponseEntity.ok(ClientWithCars.of(id, "OpenFeign", cars));
    }

    // =========================================================================
    // ENDPOINT 3: WebClient (Reactive, blocking mode)
    // =========================================================================

    /**
     * Fetches cars using WebClient in blocking mode.
     * 
     * Characteristics:
     * - Reactive/non-blocking by design (using blocking .block() here for
     * comparison)
     * - Supports streaming and backpressure
     * - Modern replacement for RestTemplate
     * - Better resource utilization under high load
     * - Can be used reactively with Mono/Flux or blocking with .block()
     * 
     * Note: Using .block() for fair comparison with sync clients.
     * In production, prefer reactive chain for better performance.
     * 
     * @param id Client ID
     * @return Client with their cars
     */
    @GetMapping("/{id}/car/webclient")
    public ResponseEntity<ClientWithCars> getCarsByWebClient(@PathVariable Long id) {
        log.info("[WebClient] Fetching cars for client: {}", id);
        long startTime = System.currentTimeMillis();

        List<CarResponse> cars = webClient.get()
                .uri("/api/cars/byClient/{clientId}", id)
                .retrieve()
                .bodyToFlux(CarResponse.class)
                .collectList()
                .block(); // Blocking for fair comparison

        long duration = System.currentTimeMillis() - startTime;
        log.info("[WebClient] Completed in {}ms, found {} cars", duration,
                cars != null ? cars.size() : 0);

        return ResponseEntity.ok(ClientWithCars.of(id, "WebClient", cars));
    }

    // =========================================================================
    // Health & Info Endpoints
    // =========================================================================

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service Client is running!");
    }

    /**
     * Info endpoint showing available methods.
     */
    @GetMapping("/info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok("""
                Client Service - HTTP Client Comparison
                ========================================
                Available endpoints:
                - GET /api/clients/{id}/car/rest      -> RestTemplate
                - GET /api/clients/{id}/car/feign     -> OpenFeign
                - GET /api/clients/{id}/car/webclient -> WebClient

                Use JMeter to compare performance!
                """);
    }
}
