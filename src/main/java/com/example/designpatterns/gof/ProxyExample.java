package com.example.designpatterns.gof;

/**
 * Proxy Pattern Example
 * Provides a surrogate or placeholder for another object to control access to it.
 * Interview Insight: Used for security, logging, caching, or lazy initialization (e.g., Spring AOP proxies).
 */
/**
 * Proxy Pattern Example
 *
 * Definition: Provides a surrogate or placeholder for another object to control access to it.
 *
 * Real-life analogy: Internet access proxy that checks user permissions before allowing access.
 *
 * Interview explanation:
 * - Proxy is used for access control, logging, or lazy loading.
 * - "Suppose you want to restrict internet access based on user roles. The proxy checks permissions before granting access."
 *
 * Real-life Example: Internet access proxy
 */
public class ProxyExample {
    // Subject interface
    public interface Internet {
        String connectTo(String site, String userRole);
    }

    // Real subject
    public static class RealInternet implements Internet {
        public String connectTo(String site, String userRole) {
            return "Connected to " + site;
        }
    }

    // Proxy
    public static class InternetProxy implements Internet {
        private final RealInternet realInternet = new RealInternet();
        private static final java.util.Set<String> blockedSites = java.util.Set.of("facebook.com", "youtube.com");
        public String connectTo(String site, String userRole) {
            if (!"ADMIN".equalsIgnoreCase(userRole)) {
                if (blockedSites.contains(site.toLowerCase())) {
                    return "Access denied to " + site + " for role: " + userRole;
                }
            }
            return realInternet.connectTo(site, userRole);
        }
    }

    public interface Service {
        void performOperation();
    }

    public static class RealService implements Service {
        public void performOperation() {
            System.out.println("Performing operation in RealService");
        }
    }

    public static class ServiceProxy implements Service {
        private final RealService realService;
        public ServiceProxy(RealService realService) {
            this.realService = realService;
        }
        public void performOperation() {
            System.out.println("Proxy: Checking access before calling RealService...");
            realService.performOperation();
            System.out.println("Proxy: Logging after calling RealService...");
        }
    }
}
