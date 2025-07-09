FROM openjdk:11-jdk

# Set working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src
COPY testng.xml .

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

# Build the project
RUN mvn compile

# Command to run tests when the container starts
CMD ["mvn", "test"]
