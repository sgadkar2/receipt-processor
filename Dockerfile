FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/receipt-processor-0.0.1-SNAPSHOT.jar receipt-processor-0.0.1.jar
ENTRYPOINT ["java","-jar","receipt-processor-0.0.1.jar"]