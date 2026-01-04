package com.lab.microservices.client.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for RestTemplate with load balancing support.
 * The @LoadBalanced annotation enables service discovery integration,
 * allowing us to use service names (e.g., "service-voiture") instead of URLs.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates a load-balanced RestTemplate bean.
     * This enables using service names registered in Eureka/Consul.
     * 
     * Example usage:
     * restTemplate.getForObject("http://service-voiture/api/cars/...", ...)
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
