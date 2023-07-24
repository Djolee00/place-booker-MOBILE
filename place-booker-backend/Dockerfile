FROM openjdk:17-oracle

WORKDIR /app

COPY ./target/place-booker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]