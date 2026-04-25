# Enterprise Application Development
**Name:** Antnhe  
**Project:** Product Catalogue API  
**Lab Session:** Lab 2 – Advanced Architecture, DTOs, and Error Handling  
**Date:** April 2026  

---

## 1. Introduction and Objectives
The objective of Lab 2 was to transition the foundational `product-catalogue-api` project into a robust, production-ready enterprise application. The primary goals included decoupling the database layer from the client interface using the Data Transfer Object (DTO) pattern, implementing centralized global error handling compliant with RFC 9457, and integrating Swagger UI for interactive API documentation. 

By the end of this session, the API was required to safely handle user input through Bean Validation and return standardized error payloads when operations failed.

## 2. Implementation Steps and Methodology

### 2.1 Enhancing the Domain Model
To model a more realistic product catalog, the `Product` JPA entity was updated with new business fields: `stockQty` and `category`. Database-level constraints such as `@Column(nullable = false)` were applied to ensure data integrity at the persistence layer. The `ProductRepository` was also extended to support derived queries, such as searching for products by category.

### 2.2 Implementing the DTO Pattern
A major architectural shift in this lab was the introduction of the DTO pattern.
- **`ProductRequest`:** Created to handle incoming client payloads. It encapsulates all Bean Validation annotations (e.g., `@NotBlank`, `@Min`, `@DecimalMin`). This ensures that malicious or invalid data is rejected at the controller level before reaching the service layer.
- **`ProductResponse`:** Created to shape the data sent back to the client. It intentionally excludes internal system fields or sensitive data that might exist on the core Entity.
- The `ProductService` was refactored to act as an orchestrator, converting incoming `ProductRequest` DTOs into `Product` entities, saving them to the H2 database, and mapping the saved entities back into `ProductResponse` DTOs.

### 2.3 Global Exception Handling (RFC 9457)
To provide clients with consistent and readable error messages, a global exception handler was implemented:
- A custom `ResourceNotFoundException` was created to handle requests for non-existent product IDs.
- A `@RestControllerAdvice` class named `GlobalExceptionHandler` was implemented to intercept exceptions across the entire application.
- The handler converts exceptions into `ProblemDetail` JSON objects, complying with the **RFC 9457** specification. This guarantees that both `404 Not Found` (from missing resources) and `400 Bad Request` (from Bean Validation failures) share a predictable, standardized structure.

### 2.4 Interactive API Documentation (Swagger UI)
To make the API easily explorable for frontend developers and external consumers, `springdoc-openapi-starter-webmvc-ui` was integrated via Maven. 
- The `ProductController` was decorated with `@Tag` and `@Operation` annotations to provide human-readable descriptions for each endpoint.
- The interactive Swagger UI console was successfully exposed, allowing for live testing of all CRUD operations directly from the browser.

## 3. Testing and Verification

Following the architectural updates, the testing suite required significant refactoring. 
- The `ProductServiceTest` unit tests were updated to handle DTOs instead of raw Entities. The tests were configured to verify that `ResourceNotFoundException` is correctly thrown when querying invalid IDs.
- The integration tests in `ProductControllerTest` were successfully executed, utilizing `MockMvc` to mock HTTP requests and verifying that the API correctly returns `201 Created` with proper `Location` headers on successful POST requests, and `400 Bad Request` on invalid payloads.
- The Maven build pipeline (`./mvnw test`) executed flawlessly, passing all assertions.

## 4. Conclusion and Reflections

Lab 2 successfully demonstrated the importance of architectural boundaries in enterprise applications. 

By offloading validation to `ProductRequest` DTOs, the core database entities remain clean and focused strictly on persistence. Furthermore, utilizing `@RestControllerAdvice` combined with RFC 9457 standardizes the API's failure states, drastically reducing the parsing complexity for frontend clients. The integration of Swagger UI adds an essential layer of developer experience, making the API self-documenting. The project is now stable, well-tested, and ready for further containerization or database migration in subsequent labs.
