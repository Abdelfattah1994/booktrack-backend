# BookTrack Backend API

A robust and scalable Spring Boot REST API for managing a book catalog, user authentication, personal favorites, and real-time popularity tracking.

## Technical Stack

* **Language:** Java 17
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security with Stateless JWT Authentication
* **Database:** H2 (In-Memory)
* **Persistence:** Spring Data JPA (Hibernate)
* **Validation:** Jakarta Bean Validation
* **Documentation:** OpenAPI 3 / Swagger UI
* **Build Tool:** Maven

## Key Features

* **Secure Authentication**: User registration and login with BCrypt password encoding and JWT issuance.
* **Book Management**: Searchable book catalog with pagination support and detailed book views.
* **User Favorites**: Authenticated users can manage a personal list of favorite books.
* **Popularity Tracking**: Real-time tracking of book views to generate a "Top 10 Most Viewed" list.
* **Global Error Handling**: Standardized JSON error responses via `@ControllerAdvice` for a better API consumer experience.
* **Data Integrity**: Strict input validation on all DTOs.

## Architecture & Design Choices

* **Stateless Security**: Used JWT to ensure the API is stateless, allowing for horizontal scalability.
* **Java Records**: Implemented DTOs using Java Records for immutability and concise code.
* **Layered Architecture**: Clear separation of concerns between Controllers, Services, Repositories, and Entities.

## Getting Started

### Prerequisites
* JDK 17 or higher
* Maven 3.6+

### Installation & Run
1. Clone the repository.
2. Navigate to the project root.
3. Run the application:
   ```bash
   ./mvnw spring-boot:run

## Database Access
* The application uses an in-memory H2 database for testing. The console is pre-configured for easy access.

* **Console URL**: http://localhost:8080/h2-console

* **JDBC URL**: jdbc:h2:mem:testdb

* **User**: sa

* **Password**: (leave blank)

## API Documentation
* Explore and test the endpoints via Swagger UI:

* URL: http://localhost:8080/swagger-ui/index.html

## Testing Flow
1. **Register** : Create a new account via /api/auth/register.

2. **Login** : Authenticate via /api/auth/login to receive your JWT token.

3. **Authorize** : Click the Authorize button in Swagger and enter your token as Bearer <your_token>.

4. **Explore** : Test protected features like adding favorites or viewing the Top 10 trending books.