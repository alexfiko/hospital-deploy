# 🏥 Hospital Microservices Ecosystem

Ecosistema completo de microservicios para gestión hospitalaria construido con Spring Boot, Spring Cloud y Docker.

## 🏗️ Arquitectura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   API Gateway   │    │  Eureka Server  │
│   (React)       │◄──►│   (Port 8080)   │◄──►│   (Port 8761)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
        ┌───────▼──────┐ ┌─────▼─────┐ ┌──────▼──────┐
        │Doctor Service│ │Citas Service│ │Especialidad │
        │ (Port 8081)  │ │(Port 8083) │ │  Service    │
        └──────────────┘ └────────────┘ │(Port 8084)  │
                │               │       └──────────────┘
                └───────────────┼───────────────┘
                                │
                        ┌───────▼──────┐
                        │  H2 Database │
                        │ (Port 9092)  │
                        └──────────────┘
```

## 🚀 Servicios

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| **API Gateway** | 8080 | Gateway principal para todas las APIs |
| **Eureka Server** | 8761 | Service Discovery y Registry |
| **Doctor Service** | 8081 | Gestión de doctores y horarios |
| **Citas Service** | 8083 | Gestión de citas médicas |
| **Especialidad Service** | 8084 | Gestión de especialidades médicas |
| **H2 Database** | 9092 | Base de datos centralizada |

## 🛠️ Tecnologías

- **Spring Boot 3.5.4**
- **Spring Cloud 2024.0.1**
- **Spring Data JPA**
- **H2 Database**
- **Docker & Docker Compose**
- **Java 17**

## 📋 Prerrequisitos

- Docker Desktop
- Docker Compose
- Maven 3.6+ (para desarrollo local)

## 🚀 Despliegue Rápido

### 1. Clonar el repositorio
```bash
git clone <repository-url>
cd java-sprint
```

### 2. Ejecutar con Docker Compose
```bash
# Construir e iniciar todos los servicios
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down
```

### 3. Verificar servicios
```bash
# Verificar estado de contenedores
docker-compose ps

# Verificar health checks
docker-compose exec api-gateway curl http://localhost:8080/actuator/health
```

## 🌐 URLs de Acceso

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **API Gateway** | http://localhost:8080 | Gateway principal |
| **Eureka Dashboard** | http://localhost:8761 | Service Discovery |
| **Doctor Service** | http://localhost:8081 | API de doctores |
| **Citas Service** | http://localhost:8083 | API de citas |
| **Especialidad Service** | http://localhost:8084 | API de especialidades |
| **H2 Console** | http://localhost:8082 | Consola de base de datos |

## 📊 API Endpoints

### API Gateway (http://localhost:8080)
```
GET  /api/doctors/**          → Doctor Service
GET  /api/citas/**            → Citas Service  
GET  /api/especialidades/**   → Especialidad Service
```

### Doctor Service (http://localhost:8081)
```
GET    /api/doctors           # Listar doctores
GET    /api/doctors/{id}      # Obtener doctor
POST   /api/doctors           # Crear doctor
PUT    /api/doctors/{id}      # Actualizar doctor
DELETE /api/doctors/{id}      # Eliminar doctor
```

### Citas Service (http://localhost:8083)
```
GET    /api/citas             # Listar citas
GET    /api/citas/{id}        # Obtener cita
POST   /api/citas             # Crear cita
PUT    /api/citas/{id}        # Actualizar cita
DELETE /api/citas/{id}        # Eliminar cita
```

### Especialidad Service (http://localhost:8084)
```
GET    /api/especialidades    # Listar especialidades
GET    /api/especialidades/{id} # Obtener especialidad
POST   /api/especialidades    # Crear especialidad
PUT    /api/especialidades/{id} # Actualizar especialidad
DELETE /api/especialidades/{id} # Eliminar especialidad
```

## 🗄️ Base de Datos

### Configuración H2
- **URL**: `jdbc:h2:tcp://h2-database:9092/hospitaldb`
- **Usuario**: `sa`
- **Contraseña**: `password`
- **Consola**: http://localhost:8082

### Estructura de Datos
```sql
-- Tablas principales
doctors              # Información de doctores
citas                # Citas médicas
pacientes            # Información de pacientes
especialidades       # Especialidades médicas

-- Tablas de relación
doctor_tags          # Tags de doctores
doctor_dias_laborales # Días laborales
doctor_horarios_disponibles # Horarios disponibles
doctor_especialidades # Relación doctor-especialidad
```

## 🔧 Configuración por Entornos

### Desarrollo Local
```bash
# Ejecutar servicios individualmente
cd doctor-service
mvn spring-boot:run

cd citas-service  
mvn spring-boot:run

cd especialidad-service
mvn spring-boot:run
```

### Docker (Producción)
```bash
# Perfil: docker
# Base de datos: H2 centralizada
# Discovery: Eureka Server
docker-compose up -d
```

## 📈 Monitoreo

### Health Checks
```bash
# Verificar estado de todos los servicios
curl http://localhost:8080/actuator/health
curl http://localhost:8081/actuator/health
curl http://localhost:8083/actuator/health
curl http://localhost:8084/actuator/health
```

### Métricas
```bash
# Obtener métricas de cada servicio
curl http://localhost:8081/actuator/metrics
curl http://localhost:8083/actuator/metrics
curl http://localhost:8084/actuator/metrics
```

## 🐳 Docker

### Comandos Útiles
```bash
# Construir imágenes
docker-compose build

# Ejecutar servicios
docker-compose up -d

# Ver logs
docker-compose logs -f [service-name]

# Reiniciar servicio
docker-compose restart [service-name]

# Escalar servicio
docker-compose up -d --scale doctor-service=2

# Limpiar
docker-compose down -v
docker system prune -f
```

### Variables de Entorno
```yaml
# Base de datos
SPRING_DATASOURCE_URL: jdbc:h2:tcp://h2-database:9092/hospitaldb
SPRING_DATASOURCE_USERNAME: sa
SPRING_DATASOURCE_PASSWORD: password

# Eureka
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE: http://eureka-server:8761/eureka/
EUREKA_INSTANCE_PREFERIPADDRESS: true

# Perfiles
SPRING_PROFILES_ACTIVE: docker
```

## 🔍 Troubleshooting

### Problemas Comunes

1. **Puertos ocupados**
   ```bash
   # Verificar puertos en uso
   netstat -tulpn | grep :8080
   
   # Cambiar puertos en docker-compose.yml
   ports:
     - "8081:8081"  # Cambiar a "8082:8081"
   ```

2. **Base de datos no accesible**
   ```bash
   # Verificar contenedor H2
   docker-compose logs h2-database
   
   # Conectar manualmente
   docker-compose exec h2-database java -cp h2.jar org.h2.tools.Server -tcp
   ```

3. **Eureka no disponible**
   ```bash
   # Verificar Eureka
   curl http://localhost:8761/eureka/apps
   
   # Reiniciar Eureka
   docker-compose restart eureka-server
   ```

### Logs
```bash
# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs de servicio específico
docker-compose logs -f doctor-service

# Ver logs con timestamps
docker-compose logs -f --timestamps
```

## 🚀 Despliegue en Render

### 1. Preparar para Render
```yaml
# render.yaml
services:
  - type: web
    name: hospital-api-gateway
    env: docker
    plan: starter
    dockerfilePath: ./api-gateway/Dockerfile
    dockerContext: .
    dockerCommand: docker-compose up api-gateway
```

### 2. Variables de Entorno en Render
```bash
SPRING_PROFILES_ACTIVE=docker
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
SPRING_DATASOURCE_URL=jdbc:h2:tcp://h2-database:9092/hospitaldb
```

### 3. Despliegue
```bash
# Conectar a Render
render deploy

# Verificar despliegue
render logs
```

## 🤝 Contribución

1. Fork el proyecto
2. Crear rama feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver [LICENSE](LICENSE) para detalles.

## 📞 Contacto

- **Desarrollador**: [Tu Nombre]
- **Email**: [tu-email@ejemplo.com]
- **Proyecto**: [https://github.com/tu-usuario/hospital-microservices] 