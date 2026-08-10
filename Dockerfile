FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/simple-jar-project-1.0-SNAPSHOT.jar app.jar

# Inform Docker that the container listens on port 8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

