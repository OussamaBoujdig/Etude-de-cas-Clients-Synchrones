# Microservices Lab - HTTP Clients & Service Discovery Comparison

A multi-module Maven project for comparing synchronous HTTP clients (RestTemplate, OpenFeign, WebClient) and Service Discovery (Eureka vs Consul).

## 📁 Project Structure

```
microservices-lab/
├── pom.xml                          # Parent POM
├── discovery-service/               # Eureka Server (port 8761)
├── service-voiture/                 # Car Service (port 8081)
└── service-client/                  # Client Service (port 8082)
```

## 🛠️ Tech Stack

- **Java**: 17
- **Spring Boot**: 3.2.1
- **Spring Cloud**: 2023.0.0
- **Service Discovery**: Eureka (default) / Consul (alternative)

## 🚀 Quick Start

### 1. Build the Project

```bash
cd microservices-lab
mvn clean install -DskipTests
```

### 2. Start Services (in order)

**Terminal 1 - Discovery Service (Eureka):**
```bash
cd discovery-service
mvn spring-boot:run
```
Wait until you see "EUREKA SERVER STARTED", then access dashboard: http://localhost:8761

**Terminal 2 - Car Service:**
```bash
cd service-voiture
mvn spring-boot:run
```

**Terminal 3 - Client Service:**
```bash
cd service-client
mvn spring-boot:run
```

### 3. Test Endpoints

```bash
# RestTemplate
curl http://localhost:8082/api/clients/1/car/rest

# OpenFeign
curl http://localhost:8082/api/clients/1/car/feign

# WebClient
curl http://localhost:8082/api/clients/1/car/webclient
```

## 📊 JMeter Load Testing

### Test Plan Configuration

1. **Thread Group**:
   - Number of Threads: 100
   - Ramp-up Period: 10 seconds
   - Loop Count: 10

2. **HTTP Requests** (create one for each client type):
   - Server: localhost
   - Port: 8082
   - Paths:
     - `/api/clients/1/car/rest`
     - `/api/clients/1/car/feign`
     - `/api/clients/1/car/webclient`

3. **Listeners**:
   - Summary Report
   - Response Time Graph
   - Aggregate Report

### Expected Observations

| Client Type | Characteristics |
|-------------|-----------------|
| RestTemplate | Traditional, synchronous blocking |
| OpenFeign | Declarative, clean interface |
| WebClient | Modern, better resource utilization |

## 🔄 Switching to Consul

### 1. Update `pom.xml` files

In `service-voiture/pom.xml` and `service-client/pom.xml`:

```xml
<!-- Comment out Eureka -->
<!--
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
-->

<!-- Uncomment Consul -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```

### 2. Update `application.yml` files

Comment out Eureka configuration and uncomment Consul configuration sections.

### 3. Start Consul

```bash
# Using Docker
docker run -d --name=consul -p 8500:8500 consul

# Or download from https://www.consul.io/downloads
consul agent -dev
```

### 4. Restart services

Access Consul UI: http://localhost:8500

## 📋 API Reference

### Car Service (port 8081)

| Endpoint | Description |
|----------|-------------|
| `GET /api/cars/byClient/{clientId}` | Get cars by client ID |
| `GET /api/cars/health` | Health check |

### Client Service (port 8082)

| Endpoint | Description |
|----------|-------------|
| `GET /api/clients/{id}/car/rest` | Fetch using RestTemplate |
| `GET /api/clients/{id}/car/feign` | Fetch using OpenFeign |
| `GET /api/clients/{id}/car/webclient` | Fetch using WebClient |
| `GET /api/clients/health` | Health check |
| `GET /api/clients/info` | Service info |

## ⏱️ Performance Notes

- The Car Service includes a 50ms `Thread.sleep()` to simulate database latency
- This is crucial for accurate JMeter performance comparisons
- Observe how each client handles concurrent requests under load

## 📝 Sample Response

```json
{
  "clientId": 1,
  "clientName": "Client-1",
  "method": "RestTemplate",
  "cars": [
    {"id": 1, "brand": "Toyota", "model": "Camry", "clientId": 1},
    {"id": 2, "brand": "Honda", "model": "Accord", "clientId": 1},
    {"id": 3, "brand": "BMW", "model": "Serie 3", "clientId": 1}
  ]
}
```

## 🔧 Troubleshooting

1. **Services not discovering each other**: Ensure Eureka is fully started before other services
2. **Port conflicts**: Check if ports 8761, 8081, 8082 are available
3. **Build failures**: Run `mvn clean install -DskipTests` from parent directory
#
