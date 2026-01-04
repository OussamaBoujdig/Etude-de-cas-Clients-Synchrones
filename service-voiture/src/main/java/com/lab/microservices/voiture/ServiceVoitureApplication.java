package com.lab.microservices.voiture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Car Service (Service Voiture) - Microservice for car data.
 * Registers with Eureka (or Consul when configured).
 * 
 * Endpoints:
 * - GET /api/cars/byClient/{clientId} - Get cars by client ID
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceVoitureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVoitureApplication.class, args);
        System.out.println("===========================================");
        System.out.println("  SERVICE VOITURE (CAR) STARTED");
        System.out.println("  Port: 8081");
        System.out.println("  Endpoint: GET /api/cars/byClient/{clientId}");
        System.out.println("===========================================");
    }
}
