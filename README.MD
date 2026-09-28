# HomeService API

A robust backend REST API built with **Spring Boot 3** and **Java 21**, designed to facilitate the management and booking of home services. This platform allows users to securely register, discover available home services, book appointments, update booking statuses, and leave reviews. 

## Tech Stack
- **Framework**: Spring Boot 3.3.4
- **Language**: Java 21
- **Database**: PostgreSQL (Spring Data JDBC)
- **Security**: JWT (JSON Web Tokens) via HTTP-only Cookies
- **Build Tool**: Maven

## Features
- **User Authentication**: Secure signup, login, and logout utilizing JSON Web Tokens stored securely in cookies.
- **User Profiles**: Fetch and update individual user profiles.
- **Service Management**: Providers can create, update, and manage home services.
- **Booking System**: Users can book services, track booking statuses, and leave ratings & reviews.
- **Role-based Actions**: Ensures specific actions (like updating/deleting services and profiles) are restricted to authorized owners.

## Project Architecture
- `controller/` - Exposes REST endpoints to clients.
- `service/` - Contains core business logic and operations.
- `repository/` - Handles database access and query execution.
- `model/` - Represents application data models and database entities.
- `dto/` - Data Transfer Objects used for API request/response payloads.
- `utils/` - Utility classes, including JWT generation, validation, and cookie management.
- `config/` - Spring Boot configuration settings.
- `exception/` - Centralized error handling.

## API Endpoints

### 🩺 Health Check
- `GET /api/health` - Check if the API is running

### 🔐 Authentication (`/v1/auth`)
- `POST /v1/auth/signup` - Register a new user
- `POST /v1/auth/login` - Authenticate a user and set a JWT cookie
- `GET /v1/auth/logout` - Log out and clear the JWT cookie

### 👤 User Management (`/api/v1/user`)
- `GET /api/v1/user/profile/{id}` - Fetch a user's profile details
- `PATCH /api/v1/user/update/{id}` - Update a user's profile

### 🛠️ Service Management (`/api/v1/service`)
- `POST /api/v1/service/create` - Create a new home service
- `GET /api/v1/service/all` - Get a list of all available services
- `GET /api/v1/service/{id}` - Get details of a specific service
- `PATCH /api/v1/service/update/{id}` - Update details of a service
- `DELETE /api/v1/service/delete/{id}` - Delete a service

### 📅 Booking Management (`/api/v1/booking`)
- `POST /api/v1/booking/create` - Book a home service
- `GET /api/v1/booking/my` - View logged-in user's personal bookings
- `GET /api/v1/booking/{id}` - Get details of a specific booking
- `GET /api/v1/booking/service/{serviceId}` - Get all bookings for a specific service
- `PATCH /api/v1/booking/status/{id}` - Update the status of a booking
- `PATCH /api/v1/booking/review/{id}` - Add a rating and review to a completed booking
- `DELETE /api/v1/booking/delete/{id}` - Cancel and delete a booking

## Setup and Installation

### Prerequisites
- **Java 21** or higher
- **Maven**
- **PostgreSQL** instance running locally or remotely

### Environment Variables
Configure your database connection credentials in `src/main/resources/application.properties` (or `.yml`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/homeservice
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Build & Run
1. Clone the repository.
2. Navigate to the project root directory.
3. Build the project using Maven wrapper:
   ```bash
   ./mvnw clean install
   ```
4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
The server will start and typically listen on port `8080`.
