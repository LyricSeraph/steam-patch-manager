FROM gradle:8.8-jdk17-jammy as backend-builder
WORKDIR /project/steam-patch-manager-be
COPY steam-patch-manager-be .
RUN ./gradlew clean && ./gradlew bootJar

FROM node:20.14-alpine as frontend-builder
WORKDIR /project/steam-patch-manager-fe
COPY steam-patch-manager-fe .
RUN rm -rf node_modules package-lock.json && npm install && npm run build

FROM openjdk:21-jdk-oracle
WORKDIR /app
COPY --from=backend-builder /project/steam-patch-manager-be/Backend/build/libs/*.jar app.jar
COPY --from=frontend-builder /project/steam-patch-manager-fe/dist dist
EXPOSE 80
CMD ["java", "-jar", "-Dspring.profiles.active=docker", "app.jar"]
