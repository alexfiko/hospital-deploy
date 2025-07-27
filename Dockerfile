FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiar los servicios
COPY doctor-service/target/doctor-service.jar doctor-service.jar
COPY citas-service/target/citas-service.jar citas-service.jar
COPY especialidad-service/target/especialidad-service.jar especialidad-service.jar
COPY api-gateway/target/api-gateway.jar api-gateway.jar
COPY eureka-server/target/eureka-server.jar eureka-server.jar
COPY h2-database-service/target/h2-database-service.jar h2-database-service.jar

# Supervisord config
COPY supervisord.conf /etc/supervisord.conf

# Instalar supervisord
RUN apt-get update && \
    apt-get install -y supervisor && \
    mkdir -p /var/log/supervisor

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]
# Exponer puertos
EXPOSE 8080 8761 8081 8082 8083 
# Exponer el puerto de la base de datos H2
EXPOSE 8084
# Exponer el puerto de la consola de supervisord
EXPOSE 9001
# Configuración de supervisord
# supervisord.conf
