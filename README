# Shopping Cart Microservice

## Overview

This project is a **Shopping Cart Microservice** built using **Kotlin**, **Spring Boot**, and **Domain-Driven Design (DDD)** principles. It implements the **Command Query Responsibility Segregation (CQRS)** pattern to handle separate read and write operations effectively. The microservice is designed to handle shopping cart operations such as adding items, removing items, and querying cart details.

## Features

- **Domain-Driven Design (DDD):** Structured around domain models to represent core business logic.
- **CQRS Architecture:** Segregation of read and write operations for scalability and maintainability.
- **MySQL Database:** For persistent storage with **Flyway** for database migrations.
- **MongoDB:** For read operations and specific query optimizations.
- **OpenAPI Documentation:** Provided via **springdoc-openapi** for API exploration.
- **Asynchronous Communication:** Through **RabbitMQ** for event-driven processing.

## Tech Stack

- **Language:** Kotlin 1.9.25
- **Frameworks:**
  - Spring Boot 3.4.0
  - Spring Security
  - Spring Data JPA
  - Spring Data MongoDB
  - Spring Web & WebFlux
- **Message Broker:** RabbitMQ
- **Databases:**
  - MySQL (Relational Database)
  - MongoDB (NoSQL Database)
- **Migration Tool:** Flyway
- **Testing:** JUnit 5, Spring Boot Test, Spring Security Test
- **Documentation:** OpenAPI (springdoc-openapi)

## Prerequisites

- **JDK 21**
- **Gradle** (build tool)
- **Docker** (optional, for containerization)
- **RabbitMQ** (running instance)
- **MySQL Database** (running instance)
- **MongoDB** (running instance)

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/leopoldoromero/shopping_cart.git
   cd shopping_cart
   ```
2. Update application configurations:

    Configure the database, RabbitMQ, and other environment-specific properties in application.yml or application.properties.

3. Build the project:

    ```bash
    ./gradlew build
    ```

4. Run project:

    ```bash
    ./gradlew bootRun
    ```

## DB Migration
 Migrations are configured to be run automaticaly when runing the application but you can run them manually with:

```bash
./gradlew flywayMigrate
```

## Runing test
This project uses JUnit 5 for testing. To execute the tests, run::

```bash
./gradlew test
```

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch:
    ```bash
    git checkout -b feature-branch-name
    ```
3. Commit your changes:
    ```bash
    git commit -m 'Add some feature'.
    ```
4. Push to the branch:
    ```bash
   git push origin feature-branch-name.
    ```
5. Submit a pull request.

