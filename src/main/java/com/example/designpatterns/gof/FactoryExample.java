package com.example.designpatterns.gof;

/**
 * Factory Pattern Example
 * Defines an interface for creating an object, but lets subclasses decide which class to instantiate.
 * Interview Insight: Decouples object creation from business logic.
 */
/**
 * Factory Pattern Example
 *
 * Definition: Defines an interface for creating an object, but lets subclasses decide which class to instantiate.
 *
 * Real-life analogy: Notification system - you want to send notifications via Email, SMS, or Push, but the client code shouldn't care about the details.
 *
 * Interview explanation:
 * - Factory decouples object creation from business logic.
 * - "Suppose you want to send different types of notifications. The Factory pattern lets you choose the right sender at runtime."
 *
 * Real-life Example: NotificationFactory
 */
public class FactoryExample {
    // Product interface
    public interface NotificationSender {
        String send(String to, String message);
    }

    // Concrete products
    public static class EmailSender implements NotificationSender {
        public String send(String to, String message) {
            return "Email sent to " + to + ": " + message;
        }
    }
    public static class SmsSender implements NotificationSender {
        public String send(String to, String message) {
            return "SMS sent to " + to + ": " + message;
        }
    }
    public static class PushSender implements NotificationSender {
        public String send(String to, String message) {
            return "Push notification sent to " + to + ": " + message;
        }
    }

    // Factory
    public static class NotificationFactory {
        public static NotificationSender createSender(String type) {
            return switch (type.toLowerCase()) {
                case "email" -> new EmailSender();
                case "sms" -> new SmsSender();
                case "push" -> new PushSender();
                default -> throw new IllegalArgumentException("Unknown notification type: " + type);
            };
        }
    }
}

