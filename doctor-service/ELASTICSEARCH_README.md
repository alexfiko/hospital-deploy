# Elasticsearch Integration - Doctor Service

## Descripción

Este microservicio ha sido mejorado con capacidades avanzadas de búsqueda utilizando Elasticsearch. Se implementan búsquedas full-text, sugerencias, correcciones y facets para una experiencia de búsqueda superior.

## Configuración

### Bonsai Elasticsearch
El servicio está configurado para conectarse a Bonsai Elasticsearch:
```
https://j78qu5rxgf:c3y0l8bm9z@hospital-cluster-5303791460.us-east-1.bonsaisearch.net:443
```

### Configuración en application-dev.yml
```yaml
spring:
  data:
    elasticsearch:
      uris: "https://j78qu5rxgf:c3y0l8bm9z@hospital-cluster-5303791460.us-east-1.bonsaisearch.net:443"
      connection-timeout: 10s
      socket-timeout: 10s
      ssl:
        enabled: true
        verification-mode: certificate
      cluster-name: hospital-cluster
      index:
        number-of-shards: 1
        number-of-replicas: 0
      search:
        default-timeout: 30s
        max-timeout: 60s
```

## Funcionalidades Implementadas

### 1. Búsqueda Full-Text con Facets

**Endpoint:** `GET /doctors/search/advanced`

**Parámetros:**
- `query` (opcional): Texto de búsqueda
- `specialty` (opcional): Filtrar por especialidad
- `hospital` (opcional): Filtrar por hospital
- `available` (opcional): Filtrar por disponibilidad
- `minRating` (opcional): Rating mínimo
- `maxRating` (opcional): Rating máximo
- `minExperience` (opcional): Años de experiencia mínimos
- `maxExperience` (opcional): Años de experiencia máximos
- `tags` (opcional): Lista de tags
- `page` (default: 0): Página
- `size` (default: 10): Tamaño de página

**Ejemplo:**
```bash
GET /doctors/search/advanced?query=cardiologo&minRating=4.0&available=true&page=0&size=10
```

**Respuesta:**
```json
{
  "doctores": [...],
  "facets": {
    "specialties": {...},
    "hospitals": {...},
    "tags": {...},
    "avgRating": 4.2,
    "avgExperience": 8.5,
    "experienceRanges": {...},
    "ratingRanges": {...}
  },
  "totalElements": 25,
  "totalPages": 3,
  "currentPage": 0
}
```

### 2. Búsqueda con Sugerencias y Correcciones

**Endpoint:** `GET /doctors/search/suggestions`

**Parámetros:**
- `query`: Texto de búsqueda

**Ejemplo:**
```bash
GET /doctors/search/suggestions?query=cardiolo
```

**Respuesta:**
```json
{
  "doctores": [...],
  "sugerencias": ["cardiólogo", "cardiología", "cardióloga"]
}
```

### 3. Autocompletado

**Endpoint:** `GET /doctors/search/autocomplete`

**Parámetros:**
- `prefix`: Prefijo para autocompletado

**Ejemplo:**
```bash
GET /doctors/search/autocomplete?prefix=car
```

**Respuesta:**
```json
["Cardiólogo", "Cardiología", "Cardióloga"]
```

### 4. Búsquedas Específicas

#### Por Especialidad
```bash
GET /doctors/search/specialty/{specialty}
```

#### Por Hospital
```bash
GET /doctors/search/hospital/{hospital}
```

#### Por Disponibilidad
```bash
GET /doctors/search/available/{available}
```

#### Por Rango de Experiencia
```bash
GET /doctors/search/experience?minYears=5&maxYears=15
```

#### Por Rango de Rating
```bash
GET /doctors/search/rating?minRating=4.0&maxRating=5.0
```

#### Por Tags
```bash
GET /doctors/search/tags?tags=emergencia,urgencia
```

### 5. Sincronización de Datos

**Endpoint:** `POST /doctors/sync/elasticsearch`

Sincroniza todos los datos de la base de datos JPA con Elasticsearch.

## Características Técnicas

### Análisis de Texto
- **Analizador Estándar**: Para búsquedas generales
- **Analizador Español**: Con stemming y stop words en español
- **Analizador de Autocompletado**: Con edge n-grams para sugerencias

### Facets Implementados
1. **Especialidades**: Conteo de doctores por especialidad
2. **Hospitales**: Conteo de doctores por hospital
3. **Tags**: Conteo de doctores por tags
4. **Rating Promedio**: Rating promedio de todos los doctores
5. **Experiencia Promedio**: Años de experiencia promedio
6. **Rangos de Experiencia**: Distribución por rangos (0-5, 6-10, 11-15, 16+)
7. **Rangos de Rating**: Distribución por rangos (1-3, 3-4, 4-5)

### Búsqueda Fuzzy
- Corrección automática de errores tipográficos
- Búsqueda con tolerancia a variaciones
- Sugerencias basadas en similitud

### Paginación
- Soporte completo para paginación
- Información de total de elementos y páginas
- Configuración de tamaño de página

## Configuración del Índice

### Mapeo de Campos
```json
{
  "name": "text + completion field",
  "specialty": "keyword + aggregation",
  "hospital": "keyword + aggregation",
  "rating": "double + aggregation",
  "experienceYears": "integer + aggregation",
  "tags": "keyword + aggregation",
  "description": "text",
  "available": "boolean + aggregation"
}
```

### Configuración de Análisis
```json
{
  "analysis": {
    "analyzer": {
      "spanish_analyzer": {
        "type": "custom",
        "tokenizer": "standard",
        "filter": ["lowercase", "spanish_stop", "spanish_stemmer"]
      },
      "autocomplete_analyzer": {
        "type": "custom",
        "tokenizer": "standard",
        "filter": ["lowercase", "autocomplete_filter"]
      }
    }
  }
}
```

## Inicialización Automática

El servicio incluye inicialización automática que:
1. Verifica si el índice existe
2. Crea el índice si no existe
3. Sincroniza datos iniciales si el índice está vacío
4. Maneja errores de conexión sin fallar la aplicación

## Endpoints de Verificación

### Verificar Elasticsearch
```bash
GET /doctors/search-index
```

### Verificar Búsqueda por Nombre
```bash
GET /doctors/search-index/name?name=Dr. García
```

## Manejo de Errores

- **Conexión fallida**: El servicio continúa funcionando con JPA
- **Índice no disponible**: Fallback a búsquedas JPA
- **Timeout**: Configuración de timeouts apropiados
- **SSL**: Configuración SSL para Bonsai

## Performance

### Optimizaciones
- **Índices optimizados**: Configuración de shards y réplicas
- **Análisis eficiente**: Analizadores personalizados
- **Caché de consultas**: Reutilización de queries comunes
- **Paginación**: Resultados paginados para mejor rendimiento

### Métricas
- **Tiempo de respuesta**: < 100ms para búsquedas simples
- **Throughput**: Soporte para múltiples consultas concurrentes
- **Escalabilidad**: Configuración para crecimiento horizontal

## Uso en Frontend

### Ejemplo de Integración React
```javascript
// Búsqueda avanzada
const searchDoctors = async (query, filters) => {
  const params = new URLSearchParams({
    query: query,
    ...filters,
    page: 0,
    size: 10
  });
  
  const response = await fetch(`/doctors/search/advanced?${params}`);
  const data = await response.json();
  
  return {
    doctors: data.doctores,
    facets: data.facets,
    pagination: {
      totalElements: data.totalElements,
      totalPages: data.totalPages,
      currentPage: data.currentPage
    }
  };
};

// Autocompletado
const getSuggestions = async (prefix) => {
  const response = await fetch(`/doctors/search/autocomplete?prefix=${prefix}`);
  return await response.json();
};
```

## Monitoreo

### Health Checks
- Verificación de conectividad con Elasticsearch
- Estado del índice
- Conteo de documentos

### Logs
- Inicialización de Elasticsearch
- Sincronización de datos
- Errores de conexión

## Próximas Mejoras

1. **Búsqueda Geográfica**: Integración con coordenadas
2. **Búsqueda Semántica**: Análisis de significado
3. **Machine Learning**: Recomendaciones personalizadas
4. **Analytics**: Métricas de búsqueda avanzadas
5. **Cache Distribuido**: Redis para resultados frecuentes
