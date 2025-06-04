# Loan Processing

A Spring Boot application to demonstrate Temporal usage in application development

## Tech Stack

### Backend
- **Language**: Kotlin 1.9.25 with JVM 21
- **Framework**: Spring Boot 3.4.4
- **Build Tool**: Gradle with Kotlin DSL
- **API**: RESTful API using Spring Web

### Monitoring & Observability
- Spring Boot Actuator
- Micrometer with Prometheus for metrics
- Distributed tracing with Micrometer Tracing Bridge (Brave)

### Testing
- JUnit 5
- Spring Boot Test

## Functional Information

The Loan Processor is designed to provide a comprehensive solution for loan management operations.

### Planned Features

#### 1. Loan Disbursement
1. Loan Application
2. Loan Underwriting
3. Loan Approval & Contract Generation
4. Loan Disbursement
5. Post-Disbursement Activities

#### 2. Loan Repayment
1. Normal Repayment
2. Early Termination
3. Partial Repayment
4. Late Payment Processing
5. Restructuring

## How to Run This Repository

### Prerequisites
- JDK 21 or later
- Temporal Server

### Configuration
N/A

### Building the Application
```bash
./gradlew build
```

### Running the Application
```bash
./gradlew bootRun
```
Or after building:
```bash
java -jar build/libs/loan-processor-0.0.1-SNAPSHOT.jar
```

### Running Tests
```bash
./gradlew test
```

### Accessing the Application
Once running, the application will be available at:
- Main application: http://localhost:8080
- Actuator endpoints: http://localhost:8080/actuator
- Prometheus metrics: http://localhost:8080/actuator/prometheus
