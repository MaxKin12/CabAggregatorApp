FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean install

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
ARG SERVICE_NAME
COPY --from=build /app/target/${SERVICE_NAME}-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
