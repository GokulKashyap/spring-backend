# Use an official OpenJDK base image (Java 17)
FROM eclipse-temurin:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy Maven wrapper and the project files
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port your Spring Boot app runs on
EXPOSE 8081

# Run the jar file (this pattern picks the jar automatically)
CMD ["sh", "-c", "java -jar target/*.jar"]
