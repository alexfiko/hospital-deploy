# Dockerfile para H2 Database Service
FROM openjdk:17-jdk-slim

# Variables de entorno
ENV H2_VERSION=2.2.224
ENV H2_DATA_DIR=/data
ENV H2_PORT=9092

# Directorio de trabajo
WORKDIR /app

# Descargar H2 Database
RUN wget -O h2.jar https://repo1.maven.org/maven2/com/h2database/h2/${H2_VERSION}/h2-${H2_VERSION}.jar

# Crear directorio para datos
RUN mkdir -p ${H2_DATA_DIR}

# Exponer puerto
EXPOSE ${H2_PORT}

# Comando para ejecutar H2 en modo servidor
CMD ["java", "-cp", "h2.jar", "org.h2.tools.Server", "-tcp", "-tcpPort", "9092", "-tcpAllowOthers", "-baseDir", "/data", "-ifNotExists"] 