# Camunda BPMN Account Creation System

A comprehensive Spring Boot application that implements an account creation workflow using BPMN-like processes and GoRules decision engine.

## 🚀 Features

- **Spring Boot REST API** with comprehensive validation and error handling
- **GoRules Integration** for intelligent decision making
- **BPMN-like Workflow** with asynchronous process execution  
- **Account Lifecycle Management** from application to approval/rejection
- **Multiple Decision Paths**: Auto-approval, auto-rejection, and manual review
- **Real-time Status Updates** with notification simulation
- **Comprehensive Testing** with multiple business rule scenarios

## 🏗️ Architecture

### System Components

1. **REST Controller** - API endpoints for account management
2. **GoRules Service** - Business rules engine for decision making
3. **Simplified Account Service** - BPMN-like workflow orchestration
4. **JPA Repository** - Data persistence with H2 database
5. **Background Processing** - Asynchronous workflow execution

### Process Flow

```
Account Request → Validation → GoRules Decision → Status Update → Notification
```

## 📋 GoRules Decision Logic

| Condition | Decision | Reason |
|-----------|----------|---------|
| Age < 18 | **AUTO_REJECT** | Below minimum age |
| Age ≥ 65 | **AUTO_APPROVE** | Senior citizen auto-approval |
| High-risk zip code | **AUTO_REJECT** | Risk assessment |
| Premium zip code | **AUTO_APPROVE** | Premium area |
| Invalid phone pattern | **AUTO_REJECT** | Phone validation failed |
| Suspicious name | **MANUAL_REVIEW** | Requires human review |
| Age 18-25 | **MANUAL_REVIEW** | Young adult review |
| Age 26-64 | **AUTO_APPROVE** | Standard approval |

## 🛠️ Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd account-creation-system
   ```

2. **Build and run**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application**
   - API: http://localhost:8080/api/accounts
   - H2 Console: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:testdb`)

### Running Tests

```bash
mvn test
```

## 📖 API Documentation

### Create Account Request
```bash
POST /api/accounts
Content-Type: application/json

{
  "name": "John Smith",
  "zipCode": "12345", 
  "age": 30,
  "phoneNumber": "5551234567"
}
```

### Get Account by ID
```bash
GET /api/accounts/{id}
```

### Get All Accounts
```bash
GET /api/accounts
```

### Get Accounts by Status
```bash
GET /api/accounts/status/{status}
# Status values: PENDING, AUTO_APPROVED, AUTO_REJECTED, MANUAL_REVIEW, MANUALLY_APPROVED, MANUALLY_REJECTED
```

### Get Pending Manual Reviews
```bash
GET /api/accounts/pending-reviews
```

## 🧪 Testing Examples

### Auto Approval Example
```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "zipCode": "12345",
    "age": 30,
    "phoneNumber": "5551234567"
  }'
```

### Manual Review Example  
```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Young Person", 
    "zipCode": "12345",
    "age": 22,
    "phoneNumber": "5551234567"
  }'
```

### Auto Rejection Example
```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Doe",
    "zipCode": "90210",
    "age": 30, 
    "phoneNumber": "5551234567"
  }'
```

## 📊 Account Status Flow

```
PENDING → [GoRules Evaluation] → AUTO_APPROVED
                               → AUTO_REJECTED  
                               → MANUAL_REVIEW → MANUALLY_APPROVED
                                              → MANUALLY_REJECTED
```

## 🔧 Configuration

### Application Properties
- Database: H2 in-memory database
- Port: 8080 (configurable in `application.yml`)
- Logging: DEBUG level for application packages

### GoRules Configuration
- High-risk zip codes: 90210, 10001, 60601
- Premium zip codes: 94102, 90210, 10021  
- Invalid phone patterns: Sequential numbers, all same digits

## 🏗️ BPMN Process Design

The system includes a complete BPMN process diagram (`src/main/resources/bpmn/account-creation-process.bpmn`) that defines:

- Start Event: Account Request Received
- Service Tasks: Validation, Rules Application, Status Updates
- Gateways: Decision routing based on GoRules output
- User Tasks: Manual review forms
- End Event: Process completion with notifications

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/accountcreation/
│   │   ├── controller/     # REST controllers
│   │   ├── service/        # Business logic & GoRules
│   │   ├── model/          # JPA entities
│   │   ├── dto/            # Data transfer objects
│   │   ├── repository/     # Data access layer
│   │   └── config/         # Configuration classes
│   └── resources/
│       ├── bpmn/           # BPMN process definitions
│       ├── static/forms/   # Manual review forms
│       └── application.yml # Application configuration
└── test/
    └── java/               # Unit tests
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.