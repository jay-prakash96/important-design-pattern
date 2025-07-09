package com.example.designpatterns.advanced;

/**
 * Circuit Breaker Pattern Structure
 * 
 * Prevents a network or service failure from cascading to other services. Used for resilience and fault tolerance in microservices.
 * This code provides a structure for implementing Circuit Breaker in a Spring Boot application (e.g., using Resilience4j).
 */
public class CircuitBreakerStructure {
    // Example with Resilience4j
    // @Service
    // public class ExternalService {
    //     @CircuitBreaker(name = "externalService", fallbackMethod = "fallback")
    //     public String callExternalApi() { ... }
    //     public String fallback(Throwable t) { ... }
    // }
}
