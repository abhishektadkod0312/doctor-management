FROM gradle:7.5-jdk11 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle

RUN gradle dependencies --no-daemon || true

COPY src ./src

RUN gradle bootJar --no-daemon

FROM openjdk:11-jdk-slim

EXPOSE 8080

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]