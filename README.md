# Nexel Social AI

Nexel Social AI is a Spring Boot backend for generating social media content with AI assistance.

## Prerequisites

- Java 25
- Docker and Docker Compose
- Maven wrapper (`./mvnw`)

## Local environment

1. Copy `.env.example` to `.env` and adjust values.
2. Start infrastructure services:
   ```bash
   docker compose up -d
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Swagger / OpenAPI

After the application starts, access:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Profiles

- `local`: default profile for local development
- `dev`: development profile with alternate server port
- `prod`: production profile, with Swagger disabled by default

## Build and tests

```bash
./mvnw clean test
```
