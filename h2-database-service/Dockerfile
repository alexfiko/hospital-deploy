# Usamos imagen de OpenJDK 17 slim
FROM openjdk:17-slim

# Creamos el directorio de trabajo donde estará el .jar
WORKDIR /opt/h2

# Instalamos wget para descargar el archivo del servidor H2
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://repo1.maven.org/maven2/com/h2database/h2/2.2.224/h2-2.2.224.jar -O h2.jar  && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
# Copiamos el archivo JAR del servidor H2 al directorio de trabajo
# Copiamos el archivo de configuración de H2 si lo tenés
#COPY h2.properties /opt/h2/h2.properties

# Copiar script de inicialización si lo usás
COPY init-db.sql init-db.sql
# Crear un volumen para persistir los datos de la base de datos H2
VOLUME /opt/h2

# Exponemos el puerto H2 TCP
EXPOSE 9092
# Exponemos el puerto H2 Web
EXPOSE 8082

# Ejecutamos el servidor H2 en modo TCP
#CMD ["java", "-cp", "h2.jar", "org.h2.tools.Server", "-tcp", "-tcpAllowOthers", "-tcpPort", "9092", "-web", "-webAllowOthers", "-webPort", "8082"]
CMD ["/bin/sh", "-c", \
  "java -cp h2.jar org.h2.tools.RunScript \
    -url jdbc:h2:/opt/h2/hospitaldb \
    -user ${H2_USER} \
    -password ${H2_PASSWORD} \
    -script /opt/h2/init-db.sql && \
   java -cp h2.jar org.h2.tools.Server \
     -tcp -tcpAllowOthers -tcpPort ${H2_PORT} \
     -web -webAllowOthers -webPort ${H2_WEB_PORT}"]