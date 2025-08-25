# 🏥 Elasticsearch Facets y Búsqueda Full-Text por Hospital

## 🎯 **Funcionalidades Implementadas**

### **1. Búsqueda Full-Text por Hospital**
- **Búsqueda inteligente** en el campo `hospital`
- **Búsqueda parcial** (contiene texto)
- **Búsqueda fuzzy** (tolerante a errores de escritura)
- **Búsqueda con wildcards** (patrones como `san*`)

### **2. Facets por Nivel de Experiencia**
- **Principiante:** 0-2 años de experiencia
- **Intermedio:** 3-5 años de experiencia  
- **Experto:** 6-10 años de experiencia
- **Senior:** 10+ años de experiencia

## 🚀 **Endpoints Disponibles**

### **Búsqueda por Hospital con Facets**
```http
GET /api/elasticsearch/doctors/hospital/{hospital}/facets?page=0&size=10
```

**Ejemplo:**
```http
GET /api/elasticsearch/doctors/hospital/San José/facets?page=0&size=5
```

**Respuesta:**
```json
{
  "doctors": [...],
  "totalElements": 15,
  "totalPages": 3,
  "currentPage": 0,
  "pageSize": 5,
  "facets": {
    "experienceLevel": {
      "Principiante": 3,
      "Intermedio": 5,
      "Experto": 4,
      "Senior": 3
    },
    "specialty": {
      "Cardiología": 6,
      "Neurología": 4,
      "Pediatría": 5
    }
  },
  "searchQuery": "San José"
}
```

### **Búsqueda por Hospital y Nivel de Experiencia**
```http
GET /api/elasticsearch/doctors/hospital/{hospital}/experience/{level}
```

**Ejemplos:**
```http
GET /api/elasticsearch/doctors/hospital/San José/experience/Experto
GET /api/elasticsearch/doctors/hospital/Clínica Central/experience/Senior
```

### **Búsqueda Fuzzy por Hospital**
```http
GET /api/elasticsearch/doctors/hospital/{hospital}/fuzzy
```

**Ejemplo:**
```http
GET /api/elasticsearch/doctors/hospital/san jose/fuzzy
```
**Encuentra:** "San José", "San Jose", "san jose", etc.

### **Búsqueda con Wildcards**
```http
GET /api/elasticsearch/doctors/hospital/{hospital}/wildcard
```

**Ejemplos:**
```http
GET /api/elasticsearch/doctors/hospital/san*/wildcard
GET /api/elasticsearch/doctors/hospital/*central*/wildcard
```

### **Búsqueda Avanzada con Múltiples Criterios**
```http
GET /api/elasticsearch/doctors/advanced?hospital=San José&specialty=Cardiología&experienceLevel=Experto&available=true&page=0&size=10
```

**Parámetros:**
- `hospital`: Nombre del hospital (opcional)
- `specialty`: Especialidad médica (opcional)
- `experienceLevel`: Nivel de experiencia (opcional)
- `available`: Disponibilidad (opcional, default: false)
- `page`: Página (opcional, default: 0)
- `size`: Tamaño de página (opcional, default: 10)

### **Niveles de Experiencia Disponibles**
```http
GET /api/elasticsearch/doctors/experience-levels
```

**Respuesta:**
```json
{
  "levels": ["Principiante", "Intermedio", "Experto", "Senior"],
  "description": {
    "Principiante": "0-2 años de experiencia",
    "Intermedio": "3-5 años de experiencia",
    "Experto": "6-10 años de experiencia",
    "Senior": "10+ años de experiencia"
  }
}
```

## 🔍 **Casos de Uso**

### **1. Búsqueda de Doctores por Hospital**
```bash
# Encontrar todos los doctores del Hospital San José
curl "http://localhost:8081/api/elasticsearch/doctors/hospital/San José/facets"

# Con paginación
curl "http://localhost:8081/api/elasticsearch/doctors/hospital/San José/facets?page=0&size=5"
```

### **2. Filtrar por Experiencia**
```bash
# Solo doctores expertos del Hospital San José
curl "http://localhost:8081/api/elasticsearch/doctors/hospital/San José/experience/Experto"

# Solo doctores senior disponibles
curl "http://localhost:8081/api/elasticsearch/doctors/advanced?experienceLevel=Senior&available=true"
```

### **3. Búsqueda Tolerante a Errores**
```bash
# Encuentra "San José" aunque escribas "san jose"
curl "http://localhost:8081/api/elasticsearch/doctors/hospital/san jose/fuzzy"

# Encuentra hospitales que empiecen con "San"
curl "http://localhost:8081/api/elasticsearch/doctors/hospital/san*/wildcard"
```

### **4. Búsqueda Combinada**
```bash
# Doctores de Cardiología en Hospital San José con experiencia intermedia
curl "http://localhost:8081/api/elasticsearch/doctors/advanced?hospital=San José&specialty=Cardiología&experienceLevel=Intermedio"
```

## 🏗️ **Arquitectura Técnica**

### **Entidad Elasticsearch**
```java
@Document(indexName = "doctores")
public class DoctorElasticsearch {
    // Campo hospital con múltiples tipos
    @Field(type = FieldType.Text, analyzer = "standard")
    private String hospital;
    
    @Field(type = FieldType.Keyword)
    private String hospitalKeyword;
    
    // Campo para facets de experiencia
    @Field(type = FieldType.Keyword)
    private String experienceLevel;
    
    // Campo de búsqueda completa
    @Field(type = FieldType.Text, analyzer = "standard")
    private String searchText;
}
```

### **Repositorio con Métodos Avanzados**
```java
public interface DoctorElasticsearchRepository extends ElasticsearchRepository<DoctorElasticsearch, String> {
    // Búsqueda por hospital (full-text)
    List<DoctorElasticsearch> findByHospitalContaining(String hospital);
    
    // Búsqueda por nivel de experiencia
    List<DoctorElasticsearch> findByExperienceLevel(String experienceLevel);
    
    // Query personalizada para facets
    @Query("{\"bool\": {\"must\": [{\"match\": {\"hospital\": \"?0\"}}], \"filter\": [{\"term\": {\"experienceLevel\": \"?1\"}}]}}")
    List<DoctorElasticsearch> searchByHospitalAndExperienceLevel(String hospital, String experienceLevel);
}
```

### **Servicio con Lógica de Facets**
```java
@Service
public class DoctorElasticsearchService {
    public Map<String, Object> searchByHospitalWithFacets(String hospital, int page, int size) {
        // Búsqueda principal
        Page<DoctorElasticsearch> results = repository.findByHospitalContaining(hospital, pageable);
        
        // Obtener facets
        Map<String, Long> experienceFacets = getExperienceFacets(hospital);
        Map<String, Long> specialtyFacets = getSpecialtyFacets(hospital);
        
        // Construir respuesta con facets
        return buildResponseWithFacets(results, experienceFacets, specialtyFacets);
    }
}
```

## 📊 **Ventajas de esta Implementación**

### **1. Búsqueda Inteligente**
- ✅ **Full-text search** en hospital
- ✅ **Fuzzy matching** para errores de escritura
- ✅ **Wildcard search** para patrones
- ✅ **Búsqueda parcial** y exacta

### **2. Facets Dinámicos**
- ✅ **Agrupación automática** por experiencia
- ✅ **Conteo en tiempo real** de resultados
- ✅ **Filtros interactivos** para el usuario
- ✅ **Navegación por facetas**

### **3. Rendimiento**
- ✅ **Indexación optimizada** con campos múltiples
- ✅ **Queries eficientes** con filtros
- ✅ **Paginación** para grandes volúmenes
- ✅ **Caché automático** de Elasticsearch

### **4. Flexibilidad**
- ✅ **Múltiples criterios** de búsqueda
- ✅ **Combinación de filtros** y facets
- ✅ **Parámetros opcionales** para búsquedas
- ✅ **Respuestas estructuradas** con metadatos

## 🧪 **Pruebas Recomendadas**

### **1. Búsqueda Básica**
```bash
# Probar endpoint de sync primero
curl "http://localhost:8081/api/elasticsearch/doctors/sync"

# Luego probar búsqueda por hospital
curl "http://localhost:8081/api/elasticsearch/doctors/hospital/San José/facets"
```

### **2. Facets de Experiencia**
```bash
# Ver niveles disponibles
curl "http://localhost:8081/api/elasticsearch/doctors/experience-levels"

# Filtrar por nivel específico
curl "http://localhost:8081/api/elasticsearch/doctors/hospital/San José/experience/Experto"
```

### **3. Búsqueda Avanzada**
```bash
# Múltiples criterios
curl "http://localhost:8081/api/elasticsearch/doctors/advanced?hospital=San José&specialty=Cardiología&available=true"
```

## 🚀 **Próximos Pasos**

### **1. Datos de Prueba**
- Crear doctores con diferentes hospitales
- Variar años de experiencia para probar facets
- Agregar especialidades diversas

### **2. Frontend Integration**
- Implementar filtros por facets
- Mostrar conteos de cada categoría
- Permitir selección múltiple de filtros

### **3. Optimizaciones**
- Agregar más campos para facets
- Implementar búsqueda geoespacial por ubicación
- Agregar sugerencias de autocompletado

---

**🎉 ¡Elasticsearch está configurado con búsqueda full-text por hospital y facets por experiencia!**
