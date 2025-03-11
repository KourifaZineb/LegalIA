# Legal Chatbot Microservices

## Project Overview
An AI-powered legal assistance platform providing affordable legal guidance to Moroccan citizens through a multilingual chatbot (French and Arabic). The system helps with everyday legal matters such as rental disputes, labor contracts, traffic fines, and civil complaints.

## Key Features
- Multilingual support for French and Arabic
- Legal document analysis
- Automated document generation (contracts, complaints, resignation letters)
- Legal question answering
- Optional lawyer referral system

## Technology Stack

### Backend
- **Framework**: Spring Boot microservices
- **API**: RESTful architecture
- **Database**: 
  - PostgreSQL for structured data
  - MongoDB for document storage
- **Message Broker**: RabbitMQ for inter-service communication
- **Authentication**: JWT-based authentication

### AI/ML
- **NLP Model**: XLM-R fine-tuned on Moroccan legal corpus
- **Training Pipeline**: Custom pipeline optimized for CPU training
- **Document Processing**: Apache PDFBox and Apache POI

### Frontend (in separate repository)
- **Web**: Angular-based responsive web application
- **Mobile**: Flutter application (if time permits)

## Getting Started

### Prerequisites
- JDK 17+
- Maven 3.8+
- Docker and Docker Compose
- PostgreSQL 13+
- MongoDB 4.4+

### Setup and Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/KourifaZineb/LegalIA.git
   cd LagalIA
   ```

2. **Start infrastructure services**
   ```bash
   docker-compose up -d config-server api-gateway
   ```

3. **Build and run each microservice**
   ```bash
   cd user-service
   mvn clean install
   mvn spring-boot:run
   ```
   Repeat for each service.

4. **Alternative: Run everything with Docker Compose**
   ```bash
   docker-compose up -d
   ```

### Configuration
Each microservice has its own `application.yml` file with service-specific configurations. The Config Server provides centralized configuration management.

Example configuration for NLP service:
```yaml
spring:
  application:
    name: nlp-service
  data:
    mongodb:
      uri: mongodb://localhost:27017/legalchatbot

model:
  path: /models/xlm-r-legal-moroccan
  languages: ["fr", "ar"]
  max-sequence-length: 512
```

## Development Guidelines

### Branching Strategy
- `main`: Production-ready code
- `dev`: Development branch
- `feature/*`: New features
- `bugfix/*`: Bug fixes

### Coding Standards
- Follow Java coding conventions
- Use meaningful names for classes and methods
- Document public APIs with Javadoc
- Write unit tests for all services

### Commit Message Format
```
[SERVICE_NAME] Brief description of the change

Detailed explanation if necessary
```

## Testing
- Unit tests with JUnit 5 and Mockito
- Integration tests with Spring Boot Test
- API tests with REST Assured
- Performance tests with JMeter

## Deployment
The microservices are containerized using Docker and can be deployed on any cloud platform that supports containerization.

### Kubernetes Deployment
Basic Kubernetes deployment files are available in the `k8s/` directory.

## AI Model Training

### Data Sources
- Moroccan Constitution
- Labor Code (Code du Travail)
- Highway Code (Code de la Route)
- Rental Law (Loi 67-12)
- Family Code (Code de la Famille)
- News articles and court decisions

### Training Process
1. Initial training on official code texts
2. Fine-tuning on news and court decisions
3. Evaluation and testing with domain experts

The training pipeline is optimized for CPU-based training environments.

## API Documentation
API documentation is available through Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## Project Status
This project is currently under active development as part of a final year project (PFE) with an expected completion timeframe of 4-5 months.

## Contributors
- [Salwa Mounji](https://github.com/salso12)
- [Zined Kourifa](https://github.com/KourifaZineb)

## Acknowledgments
- [ENSET MOHAMMEDIA]
