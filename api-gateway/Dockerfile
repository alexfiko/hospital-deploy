# 🐳 Dockerfile para API Gateway
FROM maven:3.9.3-eclipse-temurin-17 AS builder


# 🌱 Variables de entorno
ENV APP_NAME=api-gateway
ENV APP_VERSION=1.0.0
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# 📁 Directorio de trabajo
WORKDIR /app

# ⚙️ Copiar archivos necesarios
COPY pom.xml ./
COPY mvnw ./
COPY .mvn/ .mvn/
# Dar permisos de ejecución al wrapper
RUN chmod +x mvnw

# 📦 Descargar dependencias
RUN ./mvnw dependency:go-offline -B

# 👩‍💻 Copiar el código fuente
COPY src ./src

# 🔨 Compilar el proyecto
RUN ./mvnw clean package -DskipTests

# 👉 Etapa 2: Runtime
FROM openjdk:17-jdk-slim

# 📤 Copiar el .jar compilado al directorio de trabajo
WORKDIR /app
COPY --from=builder /app/target/api-gateway-1.0.0.jar api-gateway.jar

# 🚪 Exponer el puerto de la aplicación
EXPOSE 8080

# 🚀 Ejecutar la app
CMD ["sh", "-c", "java $JAVA_OPTS -jar api-gateway.jar"]