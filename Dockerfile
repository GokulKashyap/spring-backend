FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy jar file (assumes it is built already)
COPY target/*.jar app.jar

# Expose port (change if needed)
EXPOSE 8081

# Command to run your app
ENTRYPOINT ["java", "-jar", "app.jar"]
