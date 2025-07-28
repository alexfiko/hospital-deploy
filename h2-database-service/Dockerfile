# Usamos imagen de OpenJDK 17 slim
FROM openjdk:17-slim

# Creamos el directorio de trabajo donde estará el .jar
WORKDIR /opt/h2

# Instalamos wget para descargar el archivo del servidor H2
RUN apt-get update && apt-get install -y wget && \
    wget https://www.h2database.com/h2.jar

# Exponemos el puerto H2 TCP
EXPOSE 9092

# Ejecutamos el servidor H2 en modo TCP
CMD ["java", "-cp", "h2.jar", "org.h2.tools.Server", "-tcp", "-tcpAllowOthers", "-tcpPort", "9092"]

