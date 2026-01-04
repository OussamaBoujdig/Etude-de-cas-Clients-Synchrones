package com.lab.microservices.client.feign;

import com.lab.microservices.client.model.CarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Feign Client interface for calling Car Service (service-voiture).
 * 
 * OpenFeign provides declarative REST client:
 * - Automatic service discovery via Eureka/Consul
 * - Built-in load balancing
 * - Clean interface-based API
 * 
 * The "name" attribute matches the service name registered in discovery server.
 */
@FeignClient(name = "service-voiture")
public interface CarServiceFeignClient {

    /**
     * Get cars by client ID.
     * Maps to: GET http://service-voiture/api/cars/byClient/{clientId}
     * 
     * @param clientId The client ID to filter cars
     * @return List of cars belonging to the client
     */
    @GetMapping("/api/cars/byClient/{clientId}")
    List<CarResponse> getCarsByClient(@PathVariable("clientId") Long clientId);
}
