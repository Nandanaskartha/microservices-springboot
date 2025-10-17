FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY /src /app/src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/api-gateway.jar /app/api-gateway.jar
EXPOSE 8765
ENTRYPOINT ["java","-jar","/app/api-gateway.jar"]

