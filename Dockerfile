# ────────────────
# Stage 1: Build JAR
# ────────────────
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven files first (better caching)
COPY pom.xml .
COPY src ./src

RUN mvn clean package

# ────────────────
# Stage 2: Run JAR
# ────────────────
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]