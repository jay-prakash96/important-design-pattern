# Important GoF Design Patterns in Spring Boot

This project demonstrates the most important Gang of Four (GoF) design patterns with real-life, interview-ready explanations and runnable REST endpoints using Java Spring Boot. Each pattern includes:
- A real-world analogy for interview discussion
- A practical, runnable REST API demo
- Example curl commands for testing

---

## Table of Contents
- [Setup & Run](#setup--run)
- [Patterns Covered](#patterns-covered)
  - [Singleton](#singleton-pattern)
  - [Factory](#factory-pattern)
  - [Strategy](#strategy-pattern)
  - [Observer](#observer-pattern)
  - [Builder](#builder-pattern)
  - [Adapter](#adapter-pattern)
  - [Proxy](#proxy-pattern)
  - [Command](#command-pattern)

---

## Setup & Run

1. **Build and Run:**
   ```sh
   mvn spring-boot:run
   ```
   The app runs at `http://localhost:8080` by default.

2. **Log File:**
   - Server logs are also written to `application.log` in the project root.

---

## Patterns Covered

### Singleton Pattern
**Definition:** Ensures only one instance of a class exists and provides a global point of access.

**Real-life analogy:** A LoggerService used throughout the application.

**Interview Tip:**
- "Singleton is ideal for shared resources like loggers or config managers."

**Demo Endpoint:**
```sh
curl -X POST "http://localhost:8080/api/gof/singleton/log?message=Hello+Singleton"
curl -X GET  "http://localhost:8080/api/gof/singleton/history"
```

---

### Factory Pattern
**Definition:** Defines an interface for creating an object, but lets subclasses alter the type of objects that will be created.

**Real-life analogy:** Notification system (Email, SMS, Push) where the factory chooses the correct sender.

**Interview Tip:**
- "Factory decouples object creation from business logic."

**Demo Endpoint:**
```sh
curl -X POST "http://localhost:8080/api/gof/factory/send?type=email&to=alice@example.com&message=Hello+Alice"
curl -X POST "http://localhost:8080/api/gof/factory/send?type=sms&to=9999999999&message=Hello+SMS"
curl -X POST "http://localhost:8080/api/gof/factory/send?type=push&to=aliceDevice&message=Hello+Push"
```

---

### Strategy Pattern
**Definition:** Defines a family of algorithms, encapsulates each one, and makes them interchangeable.

**Real-life analogy:** Payment processing system where the user selects Credit Card, PayPal, or UPI at runtime.

**Interview Tip:**
- "Strategy is ideal for swapping business rules or algorithms at runtime."

**Demo Endpoint:**
```sh
curl -X POST "http://localhost:8080/api/gof/strategy/pay?method=credit&amount=100"
curl -X POST "http://localhost:8080/api/gof/strategy/pay?method=paypal&amount=200"
curl -X POST "http://localhost:8080/api/gof/strategy/pay?method=upi&amount=300"
```

---

### Observer Pattern
**Definition:** Defines a one-to-many dependency so that when one object changes state, all its dependents are notified.

**Real-life analogy:** Stock price notification system where users subscribe to updates.

**Interview Tip:**
- "Observer is ideal for event-driven, pub/sub, or notification systems."

**Demo Endpoint:**
```sh
curl -X POST "http://localhost:8080/api/gof/observer/stock?stock=TCS&oldPrice=3500&newPrice=3550"
```

---

### Builder Pattern
**Definition:** Separates the construction of a complex object from its representation.

**Real-life analogy:** Building a user profile with optional fields (email, phone, address).

**Interview Tip:**
- "Builder is ideal for constructing complex objects with many optional parameters."

**Demo Endpoint:**
```sh
curl -X POST "http://localhost:8080/api/gof/builder/profile" \
  -d "firstName=John" \
  -d "lastName=Doe" \
  -d "age=30" \
  -d "email=john.doe@example.com" \
  -d "phone=9999999999" \
  -d "address=123 Main St"
```

---

### Adapter Pattern
**Definition:** Allows incompatible interfaces to work together.

**Real-life analogy:** Universal document viewer for PDF and Word files.

**Interview Tip:**
- "Adapter is ideal for integrating legacy code or third-party APIs."

**Demo Endpoint:**
```sh
curl -X POST "http://localhost:8080/api/gof/adapter/view" \
  -d "fileType=pdf" \
  -d "fileName=resume.pdf"
curl -X POST "http://localhost:8080/api/gof/adapter/view" \
  -d "fileType=word" \
  -d "fileName=offer.docx"
```

---

### Proxy Pattern
**Definition:** Provides a surrogate or placeholder for another object to control access to it.

**Real-life analogy:** Internet access proxy that checks user permissions before allowing access.

**Interview Tip:**
- "Proxy is ideal for access control, logging, or lazy loading."

**Demo Endpoint:**
```sh
curl -X POST "http://localhost:8080/api/gof/proxy/internet" \
  -d "site=facebook.com" \
  -d "userRole=USER"
curl -X POST "http://localhost:8080/api/gof/proxy/internet" \
  -d "site=facebook.com" \
  -d "userRole=ADMIN"
curl -X POST "http://localhost:8080/api/gof/proxy/internet" \
  -d "site=google.com" \
  -d "userRole=USER"
```

---

### Command Pattern
**Definition:** Encapsulates a request as an object, allowing parameterization and queuing of requests.

**Real-life analogy:** Typing and undoing text in a simple text editor.

**Interview Tip:**
- "Command is ideal for undo/redo, macro recording, and task scheduling."

**Demo Endpoints:**
Type text:
```sh
curl -X POST "http://localhost:8080/api/gof/command/editor/type" -d "text=Hello "
curl -X POST "http://localhost:8080/api/gof/command/editor/type" -d "text=World!"
```
Undo last typed text:
```sh
curl -X POST "http://localhost:8080/api/gof/command/editor/undo"
```

---

## Interview Preparation Tips
- For each pattern, explain the real-life analogy, the technical definition, and the benefit.
- Use the provided curl commands to demonstrate live examples during interviews.
- Be ready to discuss why you would choose one pattern over another in real-world scenarios.

---

## License
MIT
