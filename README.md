# Job Portal Backend

A multi-module Spring Boot microservices project for managing users, authentication, profiles, and admin controls.

## Project Structure

* **`common-lib`**: Shared response classes (`ApiResponse`, `ApiErrorResponse`) and global exception handling used across services.
* **`user-service`**: Handles user sign-up, login with JWT tokens, user profiles, and admin user management.

## Tech Stack

* Java & Spring Boot
* Spring Security & JWT (JSON Web Tokens)
* Spring Data JPA & PostgreSQL
* Maven

## Setup Steps

### 1. Create the Database
Open PostgreSQL (pgAdmin or `psql`) and create a database for the user service:
```
CREATE DATABASE jobportal_user_db;
```

### 2. Add Your Configuration
In `services/user-service/src/main/resources/application.yml` (or `application.properties`), configure your database credentials and JWT secret key:

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jobportal_user_db
    username: postgres
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update

jwt:
  secret: your-secret-key-at-least-32-characters-long
  expiration-ms: 86400000
```

```application.properties
server.port=8081

spring.application.name=<app-name>
spring.datasource.url=jdbc:postgresql://localhost:5432/<db_name>
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

jwt.secret=you secret-key
jwt.expiration-ms=86400000

```

### 3. Build and Run
From the root project folder, build all modules:
```bash
mvn clean install
```

Then run `UserServiceApplication.java` from your IDE, or start it using Maven:
```bash
mvn -pl services/user-service spring-boot:run
```

## API Endpoints

### Public Endpoints (No Token Needed)
* **`POST /api/user`** — Sign up as a new `CANDIDATE` or `EMPLOYER`.
* **`POST /api/auth/login`** — Log in with email and password to receive a JWT token.

### User Endpoints (Requires `Bearer <token>`)
* **`GET /api/user/me`** — View your own profile details.
* **`PUT /api/user/me`** — Update your name, phone number, and profile image.

### Admin Endpoints (Requires `ADMIN` Role Token)
* **`GET /api/admin/users`** — View all users page by page (`?page=0&size=10`).
* **`DELETE /api/admin/users/{id}`** — Delete a user by ID.

> **Note:** Users cannot sign up directly as an `ADMIN`. To create an admin for testing, sign up as a normal user first and update their `role` to `'ADMIN'` in the `users` table in PostgreSQL.