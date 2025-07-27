# Dockerfile para Citas Service
FROM openjdk:17-jdk-slim

# Variables de entorno
ENV APP_NAME=citas-service
ENV APP_VERSION=1.0.0
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml
COPY pom.xml .

# Copiar el wrapper de Maven
COPY mvnw .
COPY .mvn .mvn

# Dar permisos de ejecución al wrapper
RUN chmod +x mvnw

# Descargar dependencias
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer el puerto
EXPOSE 8083

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/citas-service-1.0.0.jar"] 