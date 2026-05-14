FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY softtrack/pom.xml .
COPY /softtrack/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/softtrack-1.0-SNAPSHOT.jar softtrack.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "softtrack.jar"]