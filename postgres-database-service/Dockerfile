# Usamos imagen de OpenJDK 17 slim
FROM openjdk:17-slim

# Creamos el directorio de trabajo donde estará el .jar
WORKDIR /opt/h2

# Instalamos wget para descargar el archivo del servidor H2
RUN apt-get update && \
    apt-get install -y wget curl && \
    wget https://repo1.maven.org/maven2/com/h2database/h2/2.2.224/h2-2.2.224.jar -O h2.jar && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copiar script de inicialización
COPY init-db.sql init-db.sql



# Exponemos el puerto H2 TCP
EXPOSE 9092
# Exponemos el puerto H2 Web
EXPOSE 8082

# Ejecutar script de inicialización y luego iniciar el servidor
CMD java -cp h2.jar org.h2.tools.RunScript \
    -url "jdbc:h2:/opt/h2/hospitaldb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE" \
    -user ${H2_USER:-sa} \
    -password ${H2_PASSWORD:-password} \
    -script /opt/h2/init-db.sql \
    -continueOnError && \
  java -cp h2.jar org.h2.tools.Server \
    -tcp \
    -tcpAllowOthers \
    -tcpPort ${H2_PORT:-9092} \
    -web \
    -webAllowOthers \
    -webPort ${H2_WEB_PORT:-8082} \
    -baseDir /opt/h2