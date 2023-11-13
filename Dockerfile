FROM maven:3.8.4-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/receipt-processor-0.0.1-SNAPSHOT.jar receipt-processor-0.0.1.jar
ENTRYPOINT ["java","-jar","/receipt-processor-0.0.1.jar"]