FROM gradle:8.7-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle :file_storing_service:build -x test --no-daemon

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/file_storing_service/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]