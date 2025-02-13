# Use an OpenJDK base image (you can change the version if needed)
FROM openjdk:23-jdk-slim

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files (including pom.xml) into the container
COPY . /app

# Build the project using Maven (this assumes your pom.xml is in the root directory)
RUN mvn clean install -DskipTests

# Expose port 8080 (or change this to the port your app uses)
EXPOSE 8080

# Command to run your application (adjust this to the main class or jar you want to run)
CMD ["java", "-jar", "target/opti-1.0-SNAPSHOT.jar"]
