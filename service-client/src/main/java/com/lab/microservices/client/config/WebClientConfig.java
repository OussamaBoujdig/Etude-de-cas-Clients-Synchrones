package com.lab.microservices.client.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration for WebClient with load balancing support.
 * Uses @LoadBalanced WebClient.Builder to enable service discovery.
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a load-balanced WebClient.Builder bean.
     * The @LoadBalanced annotation integrates with service discovery,
     * allowing reactive calls using service names.
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    /**
     * Creates a WebClient instance configured for service-voiture.
     * Pre-configured with the base URL using the service name from Eureka/Consul.
     */
    @Bean
    public WebClient webClient(@LoadBalanced WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://service-voiture")
                .build();
    }
}
