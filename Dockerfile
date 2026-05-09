# ============================
# STAGE 1: Build con Gradle
# ============================
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle clean build -x test

# ============================
# STAGE 2: Run con OpenJDK
# ============================
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/discografia-1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
