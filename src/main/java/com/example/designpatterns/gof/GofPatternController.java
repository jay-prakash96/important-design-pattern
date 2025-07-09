package com.example.designpatterns.gof;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gof")
public class GofPatternController {

    /**
     * Singleton Pattern Demo (LoggerService)
     *
     * Real-life scenario: Imagine you want a single, centralized logger for your entire application.
     * This endpoint logs a message and returns the full log history, demonstrating the singleton's global access.
     *
     * How to explain in interview:
     * - "This endpoint simulates a LoggerService singleton. No matter how many times you call it, all logs go to the same instance."
     * - "Singleton is ideal for logging, configuration, or other shared resources."
     */
    @PostMapping("/singleton/log")
    public String singletonLogDemo(@RequestParam String message) {
        SingletonExample logger = SingletonExample.getInstance();
        logger.log(message);
        return "Logged message: '" + message + "'\n\nCurrent Log History:\n" + logger.getLogHistory();
    }

    /**
     * Factory Pattern Demo (Notification System)
     *
     * Real-life scenario: You want to send notifications via Email, SMS, or Push, but the client code shouldn't care about the details.
     * This endpoint uses the Factory pattern to choose the right notification sender at runtime.
     *
     * How to explain in interview:
     * - "This endpoint simulates a notification system using the Factory pattern. The factory decides which sender to use based on the input type."
     * - "Factory is ideal for decoupling object creation from business logic."
     */
    @PostMapping("/factory/send")
    public String factoryNotificationDemo(@RequestParam String type,
                                          @RequestParam String to,
                                          @RequestParam String message) {
        try {
            FactoryExample.NotificationSender sender = FactoryExample.NotificationFactory.createSender(type);
            String result = sender.send(to, message);
            return "Notification sent using " + type + ":\n" + result;
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Strategy Pattern Demo (Payment Processing)
     *
     * Real-life scenario: Let users choose payment method at runtime (Credit Card, PayPal, UPI).
     * This endpoint uses the Strategy pattern to select the payment algorithm dynamically.
     *
     * How to explain in interview:
     * - "This endpoint lets you choose a payment method at runtime. The Strategy pattern enables this flexibility."
     * - "Strategy is ideal for swapping business rules or algorithms without changing the client code."
     */
    @PostMapping("/strategy/pay")
    public String strategyPaymentDemo(@RequestParam String method,
                                      @RequestParam int amount) {
        StrategyExample.PaymentStrategy strategy;
        switch (method.toLowerCase()) {
            case "credit" -> strategy = new StrategyExample.CreditCardPayment();
            case "paypal" -> strategy = new StrategyExample.PaypalPayment();
            case "upi" -> strategy = new StrategyExample.UpiPayment();
            default -> {
                return "Unknown payment method. Use 'credit', 'paypal', or 'upi'.";
            }
        }
        StrategyExample.PaymentContext context = new StrategyExample.PaymentContext(strategy);
        String result = context.executePayment(amount);
        return "Payment executed using " + method + ":\n" + result;
    }

    /**
     * Observer Pattern Demo (Stock Price Notification)
     *
     * Real-life scenario: Users subscribe to stock price updates and are notified when the price changes.
     * This endpoint demonstrates the Observer pattern by simulating a stock and two user subscribers.
     *
     * How to explain in interview:
     * - "This endpoint simulates a stock price notification system. When the stock price changes, all subscribed users are notified."
     * - "Observer is ideal for pub/sub, event-driven, or notification systems."
     */
    @PostMapping("/observer/stock")
    public String observerStockDemo(@RequestParam String stock,
                                    @RequestParam double oldPrice,
                                    @RequestParam double newPrice) {
        ObserverExample.Stock stockObj = new ObserverExample.Stock(stock, oldPrice);
        ObserverExample.UserSubscriber user1 = new ObserverExample.UserSubscriber("Alice");
        ObserverExample.UserSubscriber user2 = new ObserverExample.UserSubscriber("Bob");
        stockObj.subscribe(user1);
        stockObj.subscribe(user2);
        stockObj.setPrice(newPrice);
        StringBuilder result = new StringBuilder();
        for (ObserverExample.Subscriber s : stockObj.getSubscribers()) {
            result.append(s.getName()).append(" notifications:\n");
            for (String note : s.getNotifications()) {
                result.append(note).append("\n");
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Builder Pattern Demo (User Profile)
     *
     * Real-life scenario: Build an immutable user profile with optional fields (email, phone, address).
     * This endpoint demonstrates the Builder pattern by allowing you to create a user profile with only the fields you want.
     *
     * How to explain in interview:
     * - "This endpoint lets you build a user profile step-by-step, only setting the fields you need. The Builder pattern makes this readable and safe."
     * - "Builder is ideal for constructing complex objects with many optional parameters."
     */
    @PostMapping("/builder/profile")
    public String builderProfileDemo(@RequestParam String firstName,
                                     @RequestParam String lastName,
                                     @RequestParam(required = false) Integer age,
                                     @RequestParam(required = false) String email,
                                     @RequestParam(required = false) String phone,
                                     @RequestParam(required = false) String address) {
        BuilderExample.UserProfile.Builder builder = new BuilderExample.UserProfile.Builder()
                .firstName(firstName)
                .lastName(lastName);
        if (age != null) builder.age(age);
        if (email != null) builder.email(email);
        if (phone != null) builder.phone(phone);
        if (address != null) builder.address(address);
        BuilderExample.UserProfile profile = builder.build();
        return "Built user profile: " + profile;
    }

    /**
     * Adapter Pattern Demo (Document Viewer)
     *
     * Real-life scenario: View PDF and Word documents through a common interface.
     * This endpoint demonstrates the Adapter pattern by allowing you to view different document types using a universal viewer.
     *
     * How to explain in interview:
     * - "This endpoint lets you view both PDF and Word documents using the same interface, even though the underlying readers are different. Adapter makes this possible."
     * - "Adapter is ideal for integrating legacy code or third-party APIs."
     */
    @PostMapping("/adapter/view")
    public String adapterViewDemo(@RequestParam String fileType,
                                  @RequestParam String fileName) {
        AdapterExample.UniversalViewer viewer = new AdapterExample.UniversalViewer();
        String result = viewer.viewDocument(fileType, fileName);
        return result;
    }

    /**
     * Proxy Pattern Demo (Internet Access)
     *
     * Real-life scenario: Restrict access to certain websites based on user role.
     * This endpoint demonstrates the Proxy pattern by using an InternetProxy to check permissions before allowing access.
     *
     * How to explain in interview:
     * - "This endpoint simulates an internet access proxy. The proxy checks if the user is allowed to access the requested site."
     * - "Proxy is ideal for access control, logging, or lazy loading."
     */
    @PostMapping("/proxy/internet")
    public String proxyInternetDemo(@RequestParam String site, @RequestParam String userRole) {
        ProxyExample.Internet internet = new ProxyExample.InternetProxy();
        String result = internet.connectTo(site, userRole);
        return result;
    }

    /**
     * Command Pattern Demo (Text Editor)
     *
     * Real-life scenario: Typing and undoing text in a simple text editor.
     * This endpoint demonstrates the Command pattern by letting you type text and undo the last action.
     *
     * How to explain in interview:
     * - "This endpoint lets you type text and undo the last typed string, simulating undo/redo in a text editor using the Command pattern."
     * - "Command is ideal for undo/redo, macro recording, and task scheduling."
     */
    @PostMapping("/command/editor/type")
    public String commandEditorType(@RequestParam String text) {
        if (commandInvoker == null) commandInvoker = new CommandExample.EditorInvoker();
        CommandExample.TypeCommand cmd = new CommandExample.TypeCommand(commandInvoker.getEditor(), text);
        String result = commandInvoker.executeCommand(cmd);
        return result + "\nCurrent content: " + commandInvoker.getContent();
    }

    @PostMapping("/command/editor/undo")
    public String commandEditorUndo() {
        if (commandInvoker == null) return "Nothing to undo.";
        String result = commandInvoker.undoLast();
        return result + "\nCurrent content: " + commandInvoker.getContent();
    }

    // Store the invoker as a field for session-like behavior (demo only)
    private CommandExample.EditorInvoker commandInvoker;

}
