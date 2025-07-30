# 👉 Etapa 1: Builder
FROM maven:3.9.3-eclipse-temurin-17 AS builder

WORKDIR /app

# Variables de entorno de Spring Boot y Eureka
ENV SPRING_PROFILES_ACTIVE=dev 
# Copiar archivos para construir
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src

# Compilar
RUN ./mvnw clean package -DskipTests



# 👉 Etapa 2: Runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el .jar desde la etapa "builder"
COPY --from=builder /app/target/doctor-service-1.0.0.jar doctor-service.jar

# Variables de entorno opcionales
ENV JAVA_OPTS="-Xmx512m -Xms256m"

EXPOSE 8090

CMD ["sh", "-c", "java $JAVA_OPTS -jar doctor-service.jar"]