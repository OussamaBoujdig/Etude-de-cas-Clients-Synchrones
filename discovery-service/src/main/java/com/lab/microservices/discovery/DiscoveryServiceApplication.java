package com.lab.microservices.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server for Service Discovery.
 * Access the dashboard at: http://localhost:8761
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
        System.out.println("===========================================");
        System.out.println("  EUREKA SERVER STARTED");
        System.out.println("  Dashboard: http://localhost:8761");
        System.out.println("===========================================");
    }
}
