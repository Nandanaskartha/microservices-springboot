# ====== Build Stage ======
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn clean package -DskipTests

# ====== Runtime Stage ======
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar /app/question-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/question-service.jar"]