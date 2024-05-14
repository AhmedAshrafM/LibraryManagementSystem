# Library Management System

This is a simple Library Management System built with Java and Spring Boot.

## Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Usage](#usage)
- [Features](#features)
- [How to Run](#how-to-run)
- [Database](#database)
- [API Endpoints](#api-endpoints)

## Getting Started

Follow those instructions to easily run this project on your local machine.

## Prerequisites

- IntelliJ IDEA
- Java 21
- Maven
- PostgreSQL

## How to Run

1. Clone the repository to your local machine.
2. Navigate to the project directory in your terminal.
3. If you have Maven installed, you can run the application using the following command:

```bash
mvn spring-boot:run
```

## Usage

Once the server is running, you can interact with the API at `http://localhost:8080/api/v1`.

## Features

### Authentication

The application uses Spring Security for authentication. It is configured in the `SecurityConfig` class. Here's a brief
description of how it works:

1. **UserDetailsService**: This serves as a core interface in Spring Security for fetching user-related data. The
   application utilizes an`InMemoryUserDetailsManager`, which is a simple in-memory UserDetailsService that comes with
   Spring Security. Two users are predefined, namely `user1` and `user2`, both with password `12345678` and the
   role `USER`.

2. **SecurityFilterChain**: This is where the security rules are defined. The application uses HTTP Basic
   authentication, which is a simple authentication scheme built into the HTTP protocol. The client sends the username
   and password as unencrypted base64 encoded text. It's generally recommended to use HTTPS in conjunction with Basic
   Authentication to ensure the credentials are not exposed in transit.

3. **PasswordEncoder**: This component is responsible for password encryption. The application
   adopts `BCryptPasswordEncoder`, a password encoder leveraging the BCrypt strong hashing function. Passwords, once
   encoded, are practically irretrievable to their original form. This practice enhances password storage security.

The application mandates authentication for all requests matching the `/api/**` pattern, while permitting other requests
without authentication. Disabling CSRF protection is discouraged for production applications, as it exposes the
application to cross-site request attacks.

In a typical scenario, when an unprotected endpoint is accessed, the server responds with a 401 Unauthorized status if
the request lacks valid authentication credentials. Subsequently, the client needs to resend the request with an
Authorization header containing the credentials of a valid user.

### Aspects

Implemented logging through Aspect-Oriented Programming (AOP) to log method invocations, and performance metrics of
specific operations such as book additions, updates, and patron transactions and basically every method in each service.

### Caching

Leveraged Spring's caching mechanisms to cache frequently accessed data, such as patron information or book details,
which leads to enhancing system performance.

### Transactions

Implemented declarative transaction management using Spring's `@Transactional` annotation to ensure data integrity
during critical operations.

### Testing

- Wrote unit tests to validate the functionality of API endpoints.
- Used testing frameworks like JUnit, Mockito for testing.

## Database

Apply the required configs of it that you can find in the `env.example` file.

## API Endpoints

- **`POST /api/v1/books`**: Create a new book.
- **`GET /api/v1/books`**: Retrieve all books.
- **`GET /api/v1/books/{bookId}`**: Retrieve a specific book by id.
- **`PUT /api/v1/books/{id}`**: Update a book.
- **`DELETE /api/v1/books/{id}`**: Delete a book.
- **`POST /api/v1/patrons`**: Create a new patron.
- **`GET /api/v1/patrons`**: Retrieve all patrons.
- **`GET /api/v1/patrons/{patronId}`**: Retrieve a specific patron by id.
- **`PUT /api/v1/patrons/{id}`**: Update a patron.
- **`DELETE /api/v1/patrons/{id}`**: Delete a patron.
- **`POST /api/v1/borrow`**: Create a new borrowing record.
- **`PUT /api/v1/return/{bookId}/patron/{patronId}`**: Update the borrowing record to return book.
