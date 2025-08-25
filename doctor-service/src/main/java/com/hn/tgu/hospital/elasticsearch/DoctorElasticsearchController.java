package com.hn.tgu.hospital.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

@RestController
@RequestMapping("/doctors/elasticsearch")
public class DoctorElasticsearchController {
    
    @Autowired
    private DoctorElasticsearchService doctorElasticsearchService;
    
    /**
     * Búsqueda con facets
     * GET /api/elasticsearch/doctors/search-with-facets
     */
    @GetMapping("/search-with-facets")
    public ResponseEntity<Map<String, Object>> searchWithFacets(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String hospital,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Map<String, Object> result = doctorElasticsearchService.searchWithFacets(
                query, specialty, hospital, minExperience, maxExperience, 
                minRating, maxRating, available, tags, page, size);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error en búsqueda con facets: " + e.getMessage()));
        }
    }
    
    /**
     * Búsqueda por texto
     * GET /api/elasticsearch/doctors/search
     */
    @GetMapping("/search")
    public ResponseEntity<Page<DoctorElasticsearch>> searchByText(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Page<DoctorElasticsearch> result = doctorElasticsearchService.searchByText(query, page, size);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda avanzada
     * GET /api/elasticsearch/doctors/search-advanced
     */
    @GetMapping("/search-advanced")
    public ResponseEntity<Page<DoctorElasticsearch>> searchAdvanced(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String hospital,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating,
            @RequestParam(required = false) Boolean available,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Page<DoctorElasticsearch> result = doctorElasticsearchService.searchAdvanced(
                query, specialty, hospital, minExperience, maxExperience, 
                minRating, maxRating, available, page, size);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Obtener todas las especialidades
     * GET /api/elasticsearch/doctors/specialties
     */
    @GetMapping("/specialties")
    public ResponseEntity<List<String>> getAllSpecialties() {
        try {
            List<String> specialties = doctorElasticsearchService.getAllSpecialties();
            return ResponseEntity.ok(specialties);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Obtener todos los hospitales
     * GET /api/elasticsearch/doctors/hospitals
     */
    @GetMapping("/hospitals")
    public ResponseEntity<List<String>> getAllHospitals() {
        try {
            List<String> hospitals = doctorElasticsearchService.getAllHospitals();
            return ResponseEntity.ok(hospitals);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Obtener todos los tags
     * GET /api/elasticsearch/doctors/tags
     */
    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAllTags() {
        try {
            List<String> tags = doctorElasticsearchService.getAllTags();
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Obtener doctor por ID (usando búsqueda por texto)
     * GET /api/elasticsearch/doctors/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<DoctorElasticsearch> getDoctorById(@PathVariable String id) {
        try {
            // Buscar por ID usando búsqueda por texto
            Page<DoctorElasticsearch> page = doctorElasticsearchService.searchByText(id, 0, 1);
            if (!page.getContent().isEmpty()) {
                return ResponseEntity.ok(page.getContent().get(0));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Obtener todos los doctores
     * GET /api/elasticsearch/doctors
     */
    @GetMapping
    public ResponseEntity<Page<DoctorElasticsearch>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        try {
            Page<DoctorElasticsearch> doctors = doctorElasticsearchService.findAll(page, size);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda por especialidad
     * GET /api/elasticsearch/doctors/specialty/{specialty}
     */
    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<DoctorElasticsearch>> getDoctorsBySpecialty(@PathVariable String specialty) {
        try {
            List<DoctorElasticsearch> doctors = doctorElasticsearchService.findBySpecialty(specialty);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda por hospital
     * GET /api/elasticsearch/doctors/hospital/{hospital}
     */
    @GetMapping("/hospital/{hospital}")
    public ResponseEntity<List<DoctorElasticsearch>> getDoctorsByHospital(@PathVariable String hospital) {
        try {
            List<DoctorElasticsearch> doctors = doctorElasticsearchService.findByHospital(hospital);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda por disponibilidad
     * GET /api/elasticsearch/doctors/available/{available}
     */
    @GetMapping("/available/{available}")
    public ResponseEntity<List<DoctorElasticsearch>> getDoctorsByAvailability(@PathVariable boolean available) {
        try {
            List<DoctorElasticsearch> doctors = doctorElasticsearchService.findByAvailable(available);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda por tags
     * POST /api/elasticsearch/doctors/tags
     */
    @PostMapping("/tags")
    public ResponseEntity<List<DoctorElasticsearch>> getDoctorsByTags(@RequestBody List<String> tags) {
        try {
            List<DoctorElasticsearch> doctors = doctorElasticsearchService.findByTagsIn(tags);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda por rango de experiencia
     * GET /api/elasticsearch/doctors/experience
     */
    @GetMapping("/experience")
    public ResponseEntity<List<DoctorElasticsearch>> getDoctorsByExperienceRange(
            @RequestParam int minYears,
            @RequestParam int maxYears) {
        try {
            List<DoctorElasticsearch> doctors = doctorElasticsearchService.findByExperienceYearsBetween(minYears, maxYears);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda por rango de rating
     * GET /api/elasticsearch/doctors/rating
     */
    @GetMapping("/rating")
    public ResponseEntity<List<DoctorElasticsearch>> getDoctorsByRatingRange(
            @RequestParam double minRating,
            @RequestParam double maxRating) {
        try {
            List<DoctorElasticsearch> doctors = doctorElasticsearchService.findByRatingBetween(minRating, maxRating);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda por hospital con facets de experiencia
     * GET /api/elasticsearch/doctors/hospital/{hospital}/facets?page=0&size=10
     * Ejemplo: /api/elasticsearch/doctors/hospital/San José/facets
     */
    @GetMapping("/hospital/{hospital}/facets")
    public ResponseEntity<Map<String, Object>> searchByHospitalWithFacets(
            @PathVariable String hospital,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> results = doctorElasticsearchService.searchByHospitalWithFacets(hospital, page, size);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en búsqueda por hospital con facets: " + e.getMessage());
            errorResponse.put("status", "error");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Búsqueda por hospital y nivel de experiencia específico
     * GET /api/elasticsearch/doctors/hospital/{hospital}/experience/{level}
     * Ejemplo: /api/elasticsearch/doctors/hospital/San José/experience/Experto
     */
    @GetMapping("/hospital/{hospital}/experience/{level}")
    public ResponseEntity<List<DoctorElasticsearch>> searchByHospitalAndExperienceLevel(
            @PathVariable String hospital,
            @PathVariable String level) {
        try {
            List<DoctorElasticsearch> results = doctorElasticsearchService.searchByHospitalAndExperienceLevel(hospital, level);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda fuzzy por hospital (tolerante a errores)
     * GET /api/elasticsearch/doctors/hospital/{hospital}/fuzzy
     * Ejemplo: /api/elasticsearch/doctors/hospital/san jose/fuzzy
     */
    @GetMapping("/hospital/{hospital}/fuzzy")
    public ResponseEntity<List<DoctorElasticsearch>> searchByHospitalFuzzy(
            @PathVariable String hospital) {
        try {
            List<DoctorElasticsearch> results = doctorElasticsearchService.searchByHospitalFuzzy(hospital);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda con wildcards por hospital
     * GET /api/elasticsearch/doctors/hospital/{hospital}/wildcard
     * Ejemplo: /api/elasticsearch/doctors/hospital/sanwildcard
     */
    @GetMapping("/hospital/{hospital}/wildcard")
    public ResponseEntity<List<DoctorElasticsearch>> searchByHospitalWildcard(
            @PathVariable String hospital) {
        try {
            List<DoctorElasticsearch> results = doctorElasticsearchService.searchByHospitalWildcard(hospital);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Búsqueda avanzada con múltiples criterios y facets
     * GET /api/elasticsearch/doctors/advanced?hospital=San José&specialty=Cardiología&experienceLevel=Experto&available=true&page=0&size=10
     */
    @GetMapping("/advanced")
    public ResponseEntity<Map<String, Object>> advancedSearchWithFacets(
            @RequestParam(required = false) String hospital,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(defaultValue = "false") boolean available,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> results = doctorElasticsearchService.advancedSearchWithFacets(
                hospital, specialty, experienceLevel, available, page, size);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en búsqueda avanzada: " + e.getMessage());
            errorResponse.put("status", "error");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Búsqueda por query con filtros avanzados
     * GET /api/elasticsearch/doctors/search/advanced?query=hospital.term:"Centro Médico Integral"
     * GET /api/elasticsearch/doctors/search/advanced?query=specialty.term:"Cardiología"
     * GET /api/elasticsearch/doctors/search/advanced?query=experienceLevel.term:"Experto"
     */
    @GetMapping("/search/advanced")
    public ResponseEntity<Map<String, Object>> searchByQueryAdvanced(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String hospital,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(defaultValue = "false") boolean available,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> results = doctorElasticsearchService.searchByQueryAdvanced(
                query, hospital, specialty, experienceLevel, available, page, size);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en búsqueda por query: " + e.getMessage());
            errorResponse.put("status", "error");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * BÚSQUEDA EXACTA con Facets por Rango
     * GET /api/elasticsearch/doctors/exact?field=hospital&value=Centro Médico Integral&page=0&size=10
     * GET /api/elasticsearch/doctors/exact?field=specialty&value=Cardiología&page=0&size=10
     * GET /api/elasticsearch/doctors/exact?field=experienceLevel&value=Experto&page=0&size=10
     */
    @GetMapping("/exact")
    public ResponseEntity<Map<String, Object>> searchExact(
            @RequestParam String field,
            @RequestParam String value,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> results = doctorElasticsearchService.searchExact(field, value, page, size);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en búsqueda exacta: " + e.getMessage());
            errorResponse.put("status", "error");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Búsqueda exacta por hospital con facets por rango
     * GET /api/elasticsearch/doctors/exact/hospital?value=Centro Médico Integral&page=0&size=10
     */
    @GetMapping("/exact/hospital")
    public ResponseEntity<Map<String, Object>> searchExactByHospital(
            @RequestParam String value,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> results = doctorElasticsearchService.searchByHospitalExact(value, page, size);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en búsqueda exacta por hospital: " + e.getMessage());
            errorResponse.put("status", "error");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Búsqueda exacta por especialidad con facets por rango
     * GET /api/elasticsearch/doctors/exact/specialty?value=Cardiología&page=0&size=10
     */
    @GetMapping("/exact/specialty")
    public ResponseEntity<Map<String, Object>> searchExactBySpecialty(
            @RequestParam String value,
            @RequestParam(defaultValue = "0") int size) {
        try {
            Map<String, Object> results = doctorElasticsearchService.searchBySpecialtyExact(value, 0, size);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en búsqueda exacta por especialidad: " + e.getMessage());
            errorResponse.put("status", "error");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Búsqueda exacta por nivel de experiencia con facets por rango
     * GET /api/elasticsearch/doctors/exact/experience?value=Experto&page=0&size=10
     */
    @GetMapping("/exact/experience")
    public ResponseEntity<Map<String, Object>> searchExactByExperience(
            @RequestParam String value,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> results = doctorElasticsearchService.searchByExperienceLevelExact(value, page, size);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en búsqueda exacta por experiencia: " + e.getMessage());
            errorResponse.put("status", "error");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Obtener todos los niveles de experiencia disponibles
     * GET /doctors/elasticsearch/experience-levels
     */
    @GetMapping("/experience-levels")
    public ResponseEntity<Map<String, Object>> getExperienceLevels() {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("levels", List.of("Principiante", "Intermedio", "Experto", "Senior"));
            response.put("description", Map.of(
                "Principiante", "0-2 años de experiencia",
                "Intermedio", "3-5 años de experiencia",
                "Experto", "6-10 años de experiencia",
                "Senior", "10+ años de experiencia"
            ));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error obteniendo niveles de experiencia: " + e.getMessage());
            errorResponse.put("status", "error");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Sincronizar desde la base de datos (inteligente)
     * POST /doctors/elasticsearch/sync
     */
    @PostMapping("/sync")
    public ResponseEntity<Map<String, Object>> syncFromDatabase() {
        try {
            Map<String, Object> result = doctorElasticsearchService.syncFromDatabase();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en sincronización: " + e.getMessage());
            errorResponse.put("status", "error");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Verificar estado de sincronización
     * GET /doctors/elasticsearch/sync-status
     */
    @GetMapping("/sync-status")
    public ResponseEntity<Map<String, Object>> getSyncStatus() {
        try {
            Map<String, Object> result = doctorElasticsearchService.getSyncStatus();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error obteniendo estado de sincronización: " + e.getMessage());
            errorResponse.put("status", "error");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
