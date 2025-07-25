FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar moneybridge.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "moneybridge.jar"]