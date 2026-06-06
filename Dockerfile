FROM eclipse-temurin:21-jdk
LABEL authors="dp220"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080


ENTRYPOINT ["java", "-jar","app.jar"]