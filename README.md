# Job Opportunity Management

This project is the backend system for managing job opportunities. It allows candidates to register their professional profiles and apply for job vacancies, while companies can register their profiles and open positions. The project was developed during the Java Spring Boot classes of the Rocketseat course.

## Features

- **Candidate Registration**: Candidates can sign up, create and update their professional profiles.
- **Job Applications**: Candidates can browse and apply for job vacancies.
- **Company Registration**: Companies can sign up and create profiles to represent their business.
- **Job Vacancy Management**: Companies can create, update, and manage open job positions.

## Technologies Used

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database** (In-memory for development)
- **PostgreSQL** (Running in Docker)
- **Spring Security** (for authentication and authorization)
- **Maven** (Build tool)
- **JUnit** (Testing framework)
- **SonarQube** (Code quality analysis)
- **JaCoCo** (Code coverage reporting)
- **Spring Boot Actuator** (Application health monitoring)
- **Prometheus** (Metrics collection)
- **Grafana** (Metrics visualization)

Monitoring and Code Quality:
- SonarQube: The project integrates with SonarQube for code quality analysis, ensuring that the codebase follows best practices.
- JaCoCo: Code coverage is measured with JaCoCo, helping to maintain test coverage quality.
- Spring Boot Actuator: Provides endpoints to monitor application health and metrics.
- Prometheus & Grafana: The project includes Prometheus for scraping application metrics, which are visualized in Grafana for monitoring the health and performance of the application.


Deployment:

This project is deployed on Render. You can make requests to the application at the following URL: https://job-opportunity-management.onrender.com.

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.8 or higher
- Docker (for running PostgreSQL, SonarQube, Prometheus and Grafana)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/nathaliadv/job-opportunity-management.git
   cd job-opportunity-management

2. Start the PostgreSQL service using Docker Compose:```docker-compose up -d```. This command will start all services defined in your docker-compose.yml file, including PostgreSQL.


3. Build the project: ```mvn clean install```.


4. Run the application: ```mvn spring-boot:run```

You can find examples of requests in the **Rocketseat.postman_collection.json** file included in the project. This file contains a collection of requests for interacting with the API, which can be imported into Postman for testing and exploration.
