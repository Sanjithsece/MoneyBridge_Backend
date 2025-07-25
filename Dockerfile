
FROM maven:3.9.8-eclipse-temurin-24-jammy AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar moneybridge.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "moneybridge.jar"]