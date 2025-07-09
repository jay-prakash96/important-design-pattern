package com.example.designpatterns.gof;

/**
 * Strategy Pattern Example
 * Enables selecting an algorithm's behavior at runtime.
 * Interview Insight: Swap business rules or algorithms without modifying client code.
 */
/**
 * Strategy Pattern Example
 *
 * Definition: Enables selecting an algorithm's behavior at runtime.
 *
 * Real-life analogy: Payment processing - you might want to pay using Credit Card, PayPal, or UPI, but the client code shouldn't care about the details.
 *
 * Interview explanation:
 * - Strategy lets you swap business rules or algorithms without modifying client code.
 * - "Suppose you want to let users choose their payment method at runtime. The Strategy pattern makes this easy."
 *
 * Real-life Example: Payment processing
 */
public class StrategyExample {
    // Strategy interface
    public interface PaymentStrategy {
        String pay(int amount);
    }

    // Concrete strategies
    public static class CreditCardPayment implements PaymentStrategy {
        public String pay(int amount) {
            return "Paid Rs." + amount + " using Credit Card.";
        }
    }
    public static class PaypalPayment implements PaymentStrategy {
        public String pay(int amount) {
            return "Paid Rs." + amount + " using PayPal.";
        }
    }
    public static class UpiPayment implements PaymentStrategy {
        public String pay(int amount) {
            return "Paid Rs." + amount + " using UPI.";
        }
    }

    // Context
    public static class PaymentContext {
        private PaymentStrategy strategy;
        public PaymentContext(PaymentStrategy strategy) {
            this.strategy = strategy;
        }
        public String executePayment(int amount) {
            return strategy.pay(amount);
        }
    }
}

