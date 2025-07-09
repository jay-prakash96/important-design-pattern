# Spring Boot GoF Design Patterns: End-to-End CI/CD on AWS EKS

This guide documents all the steps required to develop, containerize, and deploy this Spring Boot project (with GoF patterns) to AWS EKS using Jenkins, Docker, and Kubernetes.

---

## 1. Project Setup & Development

### 1.1. Scaffold the Spring Boot Project
- Create a new directory:
  ```sh
  mkdir importnat-design-pattern && cd importnat-design-pattern
  ```
- Scaffold a Spring Boot project manually or via [Spring Initializr](https://start.spring.io/).
- Add required dependencies in `pom.xml` (Spring Web, logging, etc).

### 1.2. Implement GoF Design Patterns
- Implement patterns (Singleton, Factory, Observer, etc.) as Java classes.
- Each pattern:
  - Has a real-life, interview-ready explanation in comments
  - Is exposed as a REST endpoint
- Enable file logging in `src/main/resources/application.properties`:
  ```properties
  logging.file.name=logs/spring-boot-app.log
  logging.level.root=INFO
  ```

### 1.3. Version Control
- Initialize git:
  ```sh
  git init
  git remote add origin https://github.com/<your-username>/important-design-pattern.git
  git add .
  git commit -m "Initial commit"
  git push -u origin main
  ```

---

## 2. Dockerization

### 2.1. Create a Dockerfile
```dockerfile
FROM eclipse-temurin:17-jre-alpine
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### 2.2. Build and Run Locally
```sh
mvn clean package -DskipTests
docker build -t important-design-pattern:latest .
docker run -p 8080:8080 important-design-pattern:latest
```

---

## 3. Kubernetes Manifests
- Create `k8s/deployment.yaml`, `k8s/service.yaml`, `k8s/ingress.yaml`.
- Use placeholders for image tag (to be replaced in CI/CD).
- Ingress allows access via external IP (no custom domain required).

---

## 4. AWS Setup

### 4.1. EKS Cluster
```sh
eksctl create cluster --name design-pattern-cluster --region us-east-1
```

### 4.2. ECR Repository
```sh
aws ecr create-repository --repository-name important-design-pattern --region us-east-1
```

---

## 5. Jenkins CI/CD Pipeline

### 5.1. Jenkins Installation
- Install Jenkins on your host (recommended for Mac):
  ```sh
  brew install jenkins-lts
  brew services start jenkins-lts
  ```
- Ensure Jenkins can access Maven, Docker, AWS CLI, and kubectl by adding their paths in the Jenkinsfile:
  ```groovy
  environment {
      PATH = "/usr/local/bin:/opt/homebrew/bin:${env.PATH}"
      ...
  }
  ```

### 5.2. Jenkinsfile Pipeline
- Checkout code from GitHub
- Build JAR with Maven
- Build and tag Docker image
- Authenticate to ECR and push image
- Substitute image tag in `deployment.yaml` (macOS: `sed -i '' ...`)
- Update kubeconfig and deploy manifests to EKS

**Sample image substitution:**
```groovy
sh """
  sed -i '' 's|image: .*|image: $ECR_REPO:$IMAGE_TAG|' k8s/deployment.yaml
"""
```

### 5.3. Jenkins Credentials
- Add AWS credentials in Jenkins (ID: `aws-jenkins-creds`).

---

## 6. Deployment & Verification

### 6.1. Run Jenkins Pipeline
- Trigger the Jenkins job.
- Watch logs for errors and fix as needed (PATH, permissions, etc).

### 6.2. Get Ingress Endpoint
```sh
kubectl get ingress
```
Wait until the `ADDRESS` column is populated.

### 6.3. Access the Application
- Open `http://<EXTERNAL-IP>` in your browser or use:
  ```sh
  curl http://<EXTERNAL-IP>
  ```

---

## 7. Troubleshooting

- **Tool not found (`mvn`, `docker`, `aws`):** Add their install paths to the Jenkinsfile `PATH`.
- **ECR repo does not exist:** Create it with `aws ecr create-repository ...`
- **Ingress ADDRESS is empty:** Wait for LoadBalancer provisioning; check Ingress controller logs if it takes too long.
- **Kubernetes errors:** Use `kubectl get pods,svc,ingress` and `kubectl describe` for debugging.

---

## 8. Best Practices & Notes

- Use `main` as your default branch unless legacy systems require `master`.
- Document endpoints and design patterns in your README.
- Use AWS IAM roles for secure EKS and ECR access in production.
- For production, consider SSL/TLS for Ingress and restrict public access.

---

## Example: Test a REST Endpoint

Once deployed, test any endpoint (e.g., Singleton):
```sh
curl http://<EXTERNAL-IP>/singleton
```

---

## References
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Docker](https://www.docker.com/)
- [Kubernetes](https://kubernetes.io/)
- [AWS EKS](https://docs.aws.amazon.com/eks/)
- [AWS ECR](https://docs.aws.amazon.com/AmazonECR/latest/userguide/)
- [Jenkins](https://www.jenkins.io/)


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
