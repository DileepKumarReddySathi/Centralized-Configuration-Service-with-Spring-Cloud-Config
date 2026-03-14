# Centralized Configuration Service with Spring Cloud Config

This project demonstrates a centralized configuration management system using Spring Cloud Config, with a Git-backed Config Server and a client microservice (Inventory Service).

## Project Structure

- `config-server/`: Spring Boot application acting as the Config Server.
- `inventory-service/`: Spring Boot microservice client (Inventory Service).
- `config-repo/`: Local Git repository storing service configurations.
- `docker-compose.yml`: Orchestrates both services and volume mounts.
- `.env.example`: Documents required environment variables.

## Getting Started

### Prerequisites

- Docker and Docker Compose
- Java 17+ (for local development)
- Git

### Running the System

1. Initialize the project and ensure `config-repo` is ready (done during setup).
2. Start the services using Docker Compose:
   ```bash
   docker-compose up --build
   ```

### Verification Endpoints

- **Config Server**: `GET http://localhost:8888/inventory-service/dev`
- **Inventory Service Config**: `GET http://localhost:8081/api/inventory/config`
- **Inventory Service Health**: `GET http://localhost:8081/api/inventory/health`

### Refreshing Configuration

1. Modify `config-repo/inventory-service-dev.yml` (e.g., change `inventory.maxStock` to 250).
2. Commit the change:
   ```bash
   cd config-repo
   git add .
   git commit -m "Update maxStock"
   ```
3. Trigger the refresh on the inventory service:
   ```bash
   curl -X POST http://localhost:8081/actuator/refresh
   ```
4. Verify the change:
   ```bash
   curl http://localhost:8081/api/inventory/config
   ```
