# Use an official OpenJDK base image (Java 17)
FROM openjdk:17-jdk-alpine 

# Set the working directory in the container
WORKDIR /app

# Copy Maven wrapper and the project files
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081


# Run the jar file (this pattern picks the jar automatically)
CMD ["java","-jar","app.jar"]
