package com.example.designpatterns.gof;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern Example
 * Defines a one-to-many dependency so that when one object changes state, all its dependents are notified.
 * Interview Insight: Used in event-driven architectures, messaging, or pub/sub systems.
 */
/**
 * Observer Pattern Example
 *
 * Definition: Defines a one-to-many dependency so that when one object changes state, all its dependents are notified.
 *
 * Real-life analogy: Stock price notifications - users subscribe to updates and are notified when the price changes.
 *
 * Interview explanation:
 * - Observer is used in event-driven architectures, messaging, or pub/sub systems.
 * - "Suppose you want users to subscribe to stock price changes. The Observer pattern makes this easy."
 *
 * Real-life Example: Stock price notification system
 */
public class ObserverExample {
    // Observer interface
    public interface Subscriber {
        void update(String stock, double price);
        String getName();
        List<String> getNotifications();
    }

    // Concrete observer
    public static class UserSubscriber implements Subscriber {
        private final String name;
        private final List<String> notifications = new ArrayList<>();
        public UserSubscriber(String name) { this.name = name; }
        public void update(String stock, double price) {
            String msg = name + " notified: " + stock + " price is now Rs." + price;
            notifications.add(msg);
        }
        public String getName() { return name; }
        public List<String> getNotifications() { return notifications; }
    }

    // Subject
    public static class Stock {
        private final String symbol;
        private double price;
        private final List<Subscriber> subscribers = new ArrayList<>();
        public Stock(String symbol, double price) {
            this.symbol = symbol;
            this.price = price;
        }
        public void subscribe(Subscriber subscriber) { subscribers.add(subscriber); }
        public void unsubscribe(Subscriber subscriber) { subscribers.remove(subscriber); }
        public void setPrice(double newPrice) {
            this.price = newPrice;
            notifySubscribers();
        }
        private void notifySubscribers() {
            for (Subscriber s : subscribers) {
                s.update(symbol, price);
            }
        }
        public List<Subscriber> getSubscribers() { return subscribers; }
    }
}

