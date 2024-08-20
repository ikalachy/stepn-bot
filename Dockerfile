# Use an official Maven image as the build environment
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the project files
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK image as the runtime environment
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the build stage to the runtime stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Pass environment variables to the application
ENV TELEGRAM_BOT_TOKEN=${TELEGRAM_BOT_TOKEN}
ENV TELEGRAM_BOT_NAME=${TELEGRAM_BOT_NAME}

# Define the command to run the application
CMD ["java", "-jar", "app.jar"]
