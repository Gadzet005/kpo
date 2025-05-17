FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /workspace/app

COPY gradle gradle
COPY gradlew .
COPY gradlew.bat .
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle/libs.versions.toml gradle/libs.versions.toml

COPY common-lib common-lib
COPY file_analisys_service file_analisys_service

RUN chmod +x ./gradlew

RUN ./gradlew :file_analisys_service:bootJar -x test

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /workspace/app/file_analisys_service/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]