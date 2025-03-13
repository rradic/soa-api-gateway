FROM openjdk:23-jdk-slim

WORKDIR /app

COPY target/administrator-service-1.0.1.jar app.jar

EXPOSE 7000

ENTRYPOINT ["java", "-jar", "app.jar"]