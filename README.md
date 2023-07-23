# InstaBackend Basic

InstaBackend Basic is a Spring Boot project that provides basic functionality for user and post management. It includes DTOs for sign-in and sign-up operations, two controllers for handling user and post requests, and corresponding services for each.

## Prerequisites

To run this project, ensure that you have the following installed:

- Java Development Kit (JDK)
- MySQL
- Maven

## Technologies Used

- Spring Boot
- MySQL
- Java Persistence API (JPA)
- Swagger UI

## Getting Started

1. Clone this repository to your local machine.
2. Set up your MySQL database and configure the connection details in the `application.properties` file.
3. Build the project using Maven: `mvn clean install`.
4. Run the application: `mvn spring-boot:run`.
5. Access Swagger UI in your browser at: `http://localhost:8080/swagger-ui.html`.

## Endpoints

The project includes the following endpoints:

### User Controller

- `POST /users/signup` - Create a new user account.
- `POST /users/signin` - Authenticate and obtain a token for the user.


## Security

Authentication is implemented using an Authentication Token class. This token is generated upon successful sign-in and must be included in the headers of subsequent requests to authorized endpoints.

## Encryption

Sensitive user data, such as passwords, is encrypted using the MD5 hashing algorithm.

## Documentation

API documentation is available via Swagger UI. Once the project is running, access `http://localhost:8080/swagger-ui.html` in your browser to explore the available endpoints and test them using the provided interface.


