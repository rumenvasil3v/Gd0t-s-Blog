# Stage 1: Build the application using Maven and Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and source code from your nested gd0t folder
COPY gd0t/pom.xml .
COPY gd0t/src ./src

# Compile and package the absolute production JAR file
RUN mvn clean package -DskipTests

# Stage 2: Create the lightweight runtime environment
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the default port Spring Boot listens on
EXPOSE 8080

# Ignition command to launch the monolithic server
ENTRYPOINT ["java", "-jar", "app.jar"]
