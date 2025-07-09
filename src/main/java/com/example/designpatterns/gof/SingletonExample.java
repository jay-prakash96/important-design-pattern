package com.example.designpatterns.gof;

/**
 * Singleton Pattern Example
 * Ensures only one instance exists and provides global access.
 * Interview Insight: Useful for shared resources (config, logging, etc.)
 */
/**
 * Singleton Pattern Example
 *
 * Definition: Ensures only one instance of a class exists and provides a global point of access to it.
 * Real-life analogy: Logger, Configuration, or Connection Pool in an application.
 *
 * Interview explanation:
 * - Useful when exactly one object is needed to coordinate actions across the system.
 * - In Spring Boot, many beans are singletons by default, but you may still need explicit singletons for things like logging, caching, or configuration.
 *
 * Real-life Example: LoggerService
 * - Let's say you want a single LoggerService for your whole application to ensure all logs go to the same place.
 * - This class demonstrates how to implement and use a singleton LoggerService.
 */
public class SingletonExample {
    // Eager initialization of the singleton instance
    private static final SingletonExample INSTANCE = new SingletonExample();

    // Example state: log history
    private final StringBuilder logHistory = new StringBuilder();

    // Private constructor prevents instantiation from other classes
    private SingletonExample() {}

    // Global access point to get the singleton instance
    public static SingletonExample getInstance() {
        return INSTANCE;
    }

    // Real-life method: log a message
    public void log(String message) {
        String entry = "[LOG] " + message + "\n";
        logHistory.append(entry);
        System.out.println(entry); // Simulate writing to a log file
    }

    // Get all logs (for demo/testing)
    public String getLogHistory() {
        return logHistory.toString();
    }
}

