# Use an official Java 21 image for building
FROM openjdk:21-jdk-slim AS build

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Set work directory
WORKDIR /app

# Copy project files
COPY . .

# Build the project
RUN mvn clean install

# Run Stage
FROM openjdk:21-jdk-slim

# Expose application port
EXPOSE 8080

# Copy the JAR file from the build stage
COPY --from=build /app/target/job-opportunity-management-0.0.1.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
