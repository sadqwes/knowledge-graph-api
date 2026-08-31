FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src src
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17-jre
RUN useradd -r -u 10001 app
USER 10001
COPY --from=build /build/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
