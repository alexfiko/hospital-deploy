# 🏥 Doctor Service

Servicio de gestión de doctores para el sistema hospitalario. Este microservicio maneja toda la información relacionada con los doctores, incluyendo sus horarios laborales y disponibilidad.

## 🚀 Características

- **Gestión de Doctores**: CRUD completo de doctores
- **Horarios Laborales**: Manejo de días laborales y horarios de trabajo
- **Disponibilidad**: Cálculo automático de horarios disponibles
- **Microservicio**: Integración con Eureka Server
- **Base de Datos**: H2 con migraciones Flyway
- **Docker**: Contenedorización completa

## 🛠️ Tecnologías

- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **H2 Database**
- **Flyway** (Migraciones)
- **Spring Cloud Netflix Eureka**
- **Docker**
- **Java 17**

## 📋 Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- Docker (opcional)

## 🏗️ **Arquitectura Propuesta**

```
Hospital Microservices Ecosystem
├── h2-database-service/     # Base de datos centralizada
├── eureka-server/           # Service Discovery
├── api-gateway/             # Gateway principal
├── doctor-service/          # Gestión de doctores
├── citas-service/           # Gestión de citas
├── especialidad-service/    # Gestión de especialidades
└── docker-compose.yml       # Orquestación completa
```

## 🗄️ **H2 Database Service**

Primero, voy a crear el servicio de base de datos independiente:

## 🚀 Ejecución Local

### Opción 1: Maven

```bash
# Clonar el repositorio
git clone <repository-url>
cd doctor-service

# Compilar y ejecutar
mvn clean install
mvn spring-boot:run
```

### Opción 2: Docker

```bash
# Construir la imagen
docker build -t doctor-service .

# Ejecutar con docker-compose
docker-compose up -d
```

## 🗄️ Base de Datos

### Configuración H2

- **URL**: `jdbc:h2:mem:doctordb` (desarrollo) / `jdbc:h2:file:/data/doctordb` (Docker)
- **Usuario**: `sa`
- **Contraseña**: `password`
- **Consola H2**: `http://localhost:8081/h2-console`

### Migraciones Flyway

Los scripts de migración se encuentran en `src/main/resources/db/migration/`:

- `V1__Create_doctors_table.sql`: Estructura de tablas
- `V2__Insert_sample_doctors.sql`: Datos de ejemplo

## 📊 API Endpoints

### Doctores

- `GET /api/doctors` - Listar todos los doctores
- `GET /api/doctors/{id}` - Obtener doctor por ID
- `POST /api/doctors` - Crear nuevo doctor
- `PUT /api/doctors/{id}` - Actualizar doctor
- `DELETE /api/doctors/{id}` - Eliminar doctor

### Horarios

- `GET /api/doctors/{id}/horarios` - Obtener horarios del doctor
- `GET /api/doctors/{id}/disponibilidad` - Obtener disponibilidad

## 🐳 Docker

### Variables de Entorno

```yaml
SPRING_PROFILES_ACTIVE: docker
SPRING_DATASOURCE_URL: jdbc:h2:file:/data/doctordb
SPRING_DATASOURCE_USERNAME: sa
SPRING_DATASOURCE_PASSWORD: password
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE: http://eureka-server:8761/eureka/
```

### Volúmenes

- `doctor-data:/data`: Persistencia de datos H2

### Red

- `hospital-network`: Red interna para microservicios

## 🔧 Configuración

### Perfiles Spring

- **default**: Desarrollo local con H2 en memoria
- **docker**: Producción con H2 persistente

### Propiedades Principales

```yaml
server:
  port: 8081

spring:
  application:
    name: doctor-service
  datasource:
    url: jdbc:h2:mem:doctordb
  flyway:
    enabled: true
    locations: classpath:db/migration
```

## 📈 Monitoreo

### Actuator Endpoints

- `GET /actuator/health` - Estado del servicio
- `GET /actuator/info` - Información de la aplicación
- `GET /actuator/metrics` - Métricas del sistema

## 🔍 Troubleshooting

### Problemas Comunes

1. **Puerto ocupado**: Cambiar `server.port` en `application.yml`
2. **Base de datos no accesible**: Verificar credenciales H2
3. **Eureka no disponible**: Verificar conexión al servidor Eureka

### Logs

```bash
# Ver logs del contenedor
docker logs doctor-service

# Ver logs de la aplicación
mvn spring-boot:run -Dspring-boot.run.arguments="--logging.level.com.hn.tgu.hospital=DEBUG"
```

## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 📞 Contacto

- **Desarrollador**: [Tu Nombre]
- **Email**: [tu-email@ejemplo.com]
- **Proyecto**: [https://github.com/tu-usuario/doctor-service] 

## 🤔 **Respuestas a tus Preguntas**

### **1. ¿Qué se hizo en el doctor-service?**

El `doctor-service` es un **microservicio completo** que implementa:

- **Arquitectura hexagonal** con capas separadas (Controller, Service, Repository, Entity)
- **Spring Boot 3.x** con Java 17
- **Spring Data JPA** para persistencia
- **H2 Database** (memoria/archivo)
- **Eureka Client** para registro de servicios
- **Flyway** para migraciones de base de datos
- **Docker** para contenedorización

### **2. Sobre H2 y Docker**

**Credenciales H2:**
- Usuario: `sa`
- Contraseña: `password`
- URL: `jdbc:h2:mem:doctordb` (memoria) / `jdbc:h2:file:/data/doctordb` (archivo)

**Docker y H2:**
- ✅ **Cada contenedor crea su propia instancia H2**
- ✅ **Los datos NO se comparten entre contenedores**
- ✅ **Para persistencia, usamos volúmenes Docker**
- ✅ **En el docker-compose, los datos se guardan en `/data`**

### **3. Horarios Laborales vs Disponibilidad**

**Backend (Responsabilidad):**
```java
// Horario laboral del doctor
private List<String> diasLaborales;     // ["lunes", "martes", "miércoles"]
private String horarioEntrada;          // "08:00"
private String horarioSalida;           // "17:00"
private int duracionCita;               // 30 minutos
```

**Frontend (Responsabilidad):**
```javascript
// Cálculo de horarios disponibles
const horariosDisponibles = calcularHorariosDisponibles(
  horarioEntrada,    // "08:00"
  horarioSalida,     // "17:00"
  duracionCita,      // 30
  citasReservadas    // ["08:30", "09:00"]
);
```

### **4. Scripts DDL y DML**

He creado los scripts de migración:

**DDL (V1__Create_doctors_table.sql):**
- Tabla principal `doctors`
- Tabla `doctor_tags` para etiquetas
- Tabla `doctor_dias_laborales` para días laborales
- Tabla `doctor_horarios_disponibles` para horarios
- Índices para optimización

**DML (V2__Insert_sample_doctors.sql):**
- 3 doctores de ejemplo con datos completos
- Horarios laborales configurados
- Tags y especialidades

### **5. Migraciones Futuras**

Con **Flyway**, puedes crear nuevas migraciones:

```sql
-- V3__Add_new_features.sql
ALTER TABLE doctors ADD COLUMN telefono VARCHAR(20);
ALTER TABLE doctors ADD COLUMN email VARCHAR(100);
```

## 🤔 **Comandos para Ejecutar**

### **Local (Desarrollo):**
```bash
cd doctor-service
mvn spring-boot:run
```

### **Docker (Producción):**
```bash
cd doctor-service
docker-compose up -d
```

### **Verificar:**
- API: http://localhost:8081/api/doctors
- H2 Console: http://localhost:8081/h2-console
- Health: http://localhost:8081/actuator/health

## 📊 **Estructura de Datos Final**

```json
{
  "id": "uuid",
  "name": "Dr. Juan Pérez",
  "specialty": "Cardiología",
  "img": "https://randomuser.me/api/portraits/men/10.jpg",
  "experienceYears": 15,
  "rating": 4.5,
  "hospital": "Hospital Central",
  "available": true,
  "description": "Especialista en cardiología",
  "tags": ["salud", "consultas", "cardiología"],
    "diasLaborales": ["lunes", "martes", "miércoles", "jueves", "viernes"],
    "horarioEntrada": "08:00",
    "horarioSalida": "17:00",
  "duracionCita": 30,
  "horariosDisponibles": {
    "lunes": ["08:00", "08:30", "09:00", "09:30"],
    "martes": ["08:00", "08:30", "09:00", "09:30"],
    "miércoles": ["08:00", "08:30", "09:00", "09:30"],
    "jueves": ["08:00", "08:30", "09:00", "09:30"],
    "viernes": ["08:00", "08:30", "09:00", "09:30"],
    "sábado": [],
    "domingo": []
  }
}
```

¿Te gustaría que implemente alguna funcionalidad específica adicional o que explique algún aspecto en más detalle? 