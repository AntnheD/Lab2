# Lab 2: Concept Reflections & Analysis

### 1. DTO Validation vs. Entity Validation
**Question:** *Why should the ProductRequest DTO carry the @Valid annotations instead of the Product entity itself?*

**Answer:** Applying `@Valid` to the Data Transfer Object (DTO) creates a firm boundary between our HTTP API contract and our internal database schema. This separation of concerns ensures that we are catching bad or malicious data at the very edge of the application (the controller layer) before it ever touches our business logic. It also allows our API payload shape to evolve independently of our database tables without breaking validation rules.

---

### 2. The Location Header
**Question:** *What is the purpose of the Location header returned on a POST 201 Created response, and which HTTP specification mandates it?*

**Answer:** Under the HTTP/1.1 specification (specifically RFC 9110), a `201 Created` response should include a `Location` header containing the exact URI of the newly created resource. This provides a direct path for the client to fetch the new record, eliminating the need for the client to manually guess the new resource ID or parse it from the response body.

---

### 3. Global Exception Handling
**Question:** *Explain the difference between @ControllerAdvice and @ExceptionHandler. When would you use each?*

**Answer:** 
* `@ExceptionHandler` is applied at the method level inside a specific controller. It is perfect for handling unique, localized errors that only pertain to that single endpoint.
* `@ControllerAdvice` (and `@RestControllerAdvice`) is applied at the class level and intercepts exceptions globally across all controllers. It is ideal for enforcing application-wide error standards (like catching all 404s) and keeping your codebase DRY.

---

### 4. Database Transaction Rollbacks in Testing
**Question:** *In your MockMvc tests you used @Transactional on the test class. What would happen to the database state between tests if you removed this annotation?*

**Answer:** Without `@Transactional`, any mock data inserted or modified during a test would permanently persist in the test database. This causes "test pollution", meaning subsequent tests might fail because the database state is unpredictable. `@Transactional` ensures that Spring rolls back the transaction at the end of every test method, providing a clean slate every time.

---

### 5. RFC 9457: Problem Details
**Question:** *What does RFC 9457 define, and why does following it produce better APIs than returning a generic { error: 'something went wrong' } message?*

**Answer:** RFC 9457 establishes a universal JSON structure ("Problem Details") for HTTP API errors, utilizing standard keys like `type`, `title`, `status`, and `detail`. By adopting this standard rather than a generic custom object, we ensure that frontend applications and third-party consumers can predictably parse and gracefully handle errors without needing custom logic for our specific backend.

---

### 6. Integration vs. Unit Testing
**Question:** *What is the difference between an integration test (MockMvc) and a unit test (Mockito)? When is each approach preferable?*

**Answer:** 
* **Unit Tests (Mockito):** These tests isolate a single class (like a Service) by mocking its dependencies. They are incredibly fast because they don't boot the Spring server. Use them to thoroughly test complex business logic and edge cases.
* **Integration Tests (MockMvc):** These tests validate the entire flow across multiple layers (Controller -> Service -> Repository). They load the Spring context, serialize JSON, and execute actual database queries. Use them to ensure that real HTTP requests produce the correct responses and side-effects.