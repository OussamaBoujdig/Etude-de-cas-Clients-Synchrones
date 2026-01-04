package com.lab.microservices.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Client Service - Demonstrates three HTTP client approaches:
 * 1. RestTemplate (load-balanced)
 * 2. OpenFeign (declarative)
 * 3. WebClient (reactive, used in blocking mode)
 * 
 * Endpoints:
 * - GET /api/clients/{id}/car/rest -> RestTemplate
 * - GET /api/clients/{id}/car/feign -> OpenFeign
 * - GET /api/clients/{id}/car/webclient -> WebClient
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceClientApplication.class, args);
        System.out.println("===========================================");
        System.out.println("  SERVICE CLIENT STARTED");
        System.out.println("  Port: 8082");
        System.out.println("  Endpoints:");
        System.out.println("    GET /api/clients/{id}/car/rest");
        System.out.println("    GET /api/clients/{id}/car/feign");
        System.out.println("    GET /api/clients/{id}/car/webclient");
        System.out.println("===========================================");
    }
}
