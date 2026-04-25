# Product Catalogue API (Lab 2)

![Build Status](https://github.com/AntnheD/Lab2/actions/workflows/ci.yml/badge.svg)

Welcome to the Product Catalogue API! This project serves as the core product-service component of our Enterprise Application architecture, built robustly using Spring Boot.

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing.

### Prerequisites
- **Java 17** must be installed and configured in your `JAVA_HOME`.
- Maven (included via the `mvnw` wrapper).

### Installation & Execution

1. **Clone the repository:**
   ```bash
   git clone git@github.com:AntnheD/Lab2.git
   cd Lab2/product-catalogue-api
   ```

2. **Start the application server:**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Execute the test suite:**
   To verify that all unit and integration tests are passing successfully:
   ```bash
   ./mvnw test
   ```

## 🛠️ Tech Stack & Architecture Features

This application implements several modern enterprise development patterns:
- **Core Framework:** Spring Boot 3.5 with Java 17
- **Database:** In-Memory H2 Database for rapid development
- **Data Transfer:** Strict DTO Pattern separating `ProductRequest` (inputs) from `ProductResponse` (outputs)
- **Exception Handling:** Global `@ControllerAdvice` implementing RFC 9457 (`ProblemDetail` standard)
- **Live Documentation:** Automated OpenAPI (Swagger) integration
- **CI/CD Pipeline:** Automated testing workflows via GitHub Actions

## 📖 API Reference

Once the application is running (defaulting to port `8080`), you can explore, test, and interact with the endpoints directly through your browser using Swagger UI.

- **Interactive Swagger Console:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Raw OpenAPI JSON Spec:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Available Endpoints

| Operation | HTTP Method | Route | Description |
| :--- | :---: | :--- | :--- |
| **Fetch All** | `GET` | `/api/v1/products` | Retrieves a list of all products in the catalogue. |
| **Fetch One** | `GET` | `/api/v1/products/{id}` | Retrieves details of a specific product by its ID. |
| **Create** | `POST` | `/api/v1/products` | Adds a new product (requires valid JSON body). |
| **Update** | `PUT` | `/api/v1/products/{id}` | Modifies an existing product (requires valid JSON body). |
| **Delete** | `DELETE` | `/api/v1/products/{id}` | Removes a product from the database by its ID. |

## 🧪 Postman Integration

For manual API testing outside of Swagger, a pre-configured Postman collection is provided. Simply import the `postman/product-service-lab2.json` file found within the project directory into your Postman client to test the full CRUD lifecycle.
