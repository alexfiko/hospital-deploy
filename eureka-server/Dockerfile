# Dockerfile para Eureka Server
FROM openjdk:17-jdk-slim

# Variables de entorno
ENV APP_NAME=eureka-server
ENV APP_VERSION=1.0.0
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml
COPY pom.xml .
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
EXPOSE 8761

# Comando para ejecutar la aplicación
#CMD ["java", "-jar", "target/eureka-server-1.0.0.jar"] 
CMD ["sh", "-c", "java $JAVA_OPTS -jar target/${APP_NAME}-${APP_VERSION}.jar"]