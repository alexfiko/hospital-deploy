package com.hn.tgu.hospital.elasticsearch;

import com.hn.tgu.hospital.entity.Doctor;
import com.hn.tgu.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Query;

@Service
public class DoctorElasticsearchService {
    
    @Autowired
    private DoctorElasticsearchRepository doctorElasticsearchRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;
    
    /**
     * Búsqueda simple por texto
     */
    public Page<DoctorElasticsearch> searchByText(String query, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return doctorElasticsearchRepository.findBySearchTextContaining(query, pageable);
        } catch (Exception e) {
            // Retornar página vacía en caso de error
            Pageable pageable = PageRequest.of(page, size);
            return Page.empty(pageable);
        }
    }
    
    /**
     * Obtener todas las especialidades disponibles
     */
    public List<String> getAllSpecialties() {
        try {
            // Usar búsqueda con paginación
            Pageable pageable = PageRequest.of(0, 1000);
            Page<DoctorElasticsearch> page = doctorElasticsearchRepository.findBySearchTextContaining("", pageable);
            
            Set<String> specialties = new HashSet<>();
            for (DoctorElasticsearch doctor : page.getContent()) {
                if (doctor.getSpecialty() != null) {
                    specialties.add(doctor.getSpecialty());
                }
            }
            return new ArrayList<>(specialties);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtener todos los hospitales disponibles
     */
    public List<String> getAllHospitals() {
        try {
            // Usar búsqueda con paginación
            Pageable pageable = PageRequest.of(0, 1000);
            Page<DoctorElasticsearch> page = doctorElasticsearchRepository.findBySearchTextContaining("", pageable);
            
            Set<String> hospitals = new HashSet<>();
            for (DoctorElasticsearch doctor : page.getContent()) {
                if (doctor.getHospital() != null) {
                    hospitals.add(doctor.getHospital());
                }
            }
            return new ArrayList<>(hospitals);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtener todos los tags disponibles
     */
    public List<String> getAllTags() {
        try {
            // Usar búsqueda con paginación
            Pageable pageable = PageRequest.of(0, 1000);
            Page<DoctorElasticsearch> page = doctorElasticsearchRepository.findBySearchTextContaining("", pageable);
            
            Set<String> allTags = new HashSet<>();
            for (DoctorElasticsearch doctor : page.getContent()) {
                if (doctor.getTags() != null) {
                    allTags.addAll(doctor.getTags());
                }
            }
            return new ArrayList<>(allTags);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtener todos los doctores (con paginación)
     */
    public Page<DoctorElasticsearch> findAll(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return doctorElasticsearchRepository.findBySearchTextContaining("", pageable);
        } catch (Exception e) {
            // Retornar página vacía en caso de error
            Pageable pageable = PageRequest.of(page, size);
            return Page.empty(pageable);
        }
    }
    
    /**
     * Búsqueda con facets simplificada
     */
    public Map<String, Object> searchWithFacets(String query, String specialty, String hospital, 
                                               Integer minExperience, Integer maxExperience, 
                                               Double minRating, Double maxRating, 
                                               Boolean available, List<String> tags, 
                                               int page, int size) {
        
        try {
            // Usar búsqueda avanzada del repositorio
            Page<DoctorElasticsearch> doctorPage = searchAdvanced(
                query, specialty, hospital, minExperience, maxExperience, 
                minRating, maxRating, available, page, size);
            
            // Obtener facets básicos
            List<String> allSpecialties = getAllSpecialties();
            List<String> allHospitals = getAllHospitals();
            List<String> allTags = getAllTags();
            
            // Construir respuesta
            Map<String, Object> result = new HashMap<>();
            result.put("doctors", doctorPage.getContent());
            result.put("totalHits", doctorPage.getTotalElements());
            result.put("page", page);
            result.put("size", size);
            
            // Facets simplificados
            Map<String, Object> facets = new HashMap<>();
            facets.put("specialties", allSpecialties);
            facets.put("hospitals", allHospitals);
            facets.put("tags", allTags);
            
            result.put("facets", facets);
            
            return result;
            
        } catch (Exception e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("error", "Error en búsqueda con facets: " + e.getMessage());
            return errorResult;
        }
    }
    
    /**
     * Búsqueda avanzada con múltiples criterios
     */
    public Page<DoctorElasticsearch> searchAdvanced(String query, String specialty, String hospital, 
                                                   Integer minExperience, Integer maxExperience, 
                                                   Double minRating, Double maxRating, 
                                                   Boolean available, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            
            // Si hay query, usar búsqueda de texto
            if (query != null && !query.trim().isEmpty()) {
                return doctorElasticsearchRepository.findBySearchTextContaining(query, pageable);
            }
            
            // Si no hay query, usar filtros específicos
            List<DoctorElasticsearch> results = new ArrayList<>();
            
            // Aplicar filtros
            if (specialty != null && !specialty.isEmpty()) {
                results = doctorElasticsearchRepository.findBySpecialty(specialty);
            }
            
            if (hospital != null && !hospital.isEmpty()) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findByHospitalContaining(hospital);
                } else {
                    results = results.stream()
                        .filter(d -> d.getHospital().toLowerCase().contains(hospital.toLowerCase()))
                        .collect(Collectors.toList());
                }
            }
            
            if (minExperience != null && maxExperience != null) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findByExperienceYearsBetween(minExperience, maxExperience);
                } else {
                    results = results.stream()
                        .filter(d -> d.getExperienceYears() >= minExperience && d.getExperienceYears() <= maxExperience)
                        .collect(Collectors.toList());
                }
            }
            
            if (minRating != null && maxRating != null) {
                if (results.isEmpty()) {
                    results = results.stream()
                        .filter(d -> d.getRating() >= minRating && d.getRating() <= maxRating)
                        .collect(Collectors.toList());
                } else {
                    results = results.stream()
                        .filter(d -> d.getRating() >= minRating && d.getRating() <= maxRating)
                        .collect(Collectors.toList());
                }
            }
            
            if (available != null) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findByAvailable(available);
                } else {
                    results = results.stream()
                        .filter(d -> d.isAvailable() == available)
                        .collect(Collectors.toList());
                }
            }
            
            // Aplicar paginación
            int start = page * size;
            int end = Math.min(start + size, results.size());
            List<DoctorElasticsearch> paginatedResults = results.subList(start, end);
            
            // Crear página personalizada
            return new PageImpl<>(paginatedResults, pageable, results.size());
            
        } catch (Exception e) {
            // Retornar página vacía en caso de error
            Pageable pageable = PageRequest.of(page, size);
            return Page.empty(pageable);
        }
    }
    
    /**
     * Métodos de búsqueda básicos usando Spring Data
     */
    public List<DoctorElasticsearch> findBySpecialty(String specialty) {
        try {
            return doctorElasticsearchRepository.findBySpecialty(specialty);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public List<DoctorElasticsearch> findByHospital(String hospital) {
        try {
            return doctorElasticsearchRepository.findByHospitalContaining(hospital);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public List<DoctorElasticsearch> findByAvailable(boolean available) {
        try {
            return doctorElasticsearchRepository.findByAvailable(available);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public List<DoctorElasticsearch> findByTagsIn(List<String> tags) {
        try {
            // Implementación manual ya que no existe findByTagsIn
            List<DoctorElasticsearch> allDoctors = doctorElasticsearchRepository.findBySearchTextContaining("", PageRequest.of(0, 1000)).getContent();
            return allDoctors.stream()
                .filter(d -> d.getTags() != null && d.getTags().stream().anyMatch(tags::contains))
                .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public List<DoctorElasticsearch> findByExperienceYearsBetween(int minYears, int maxYears) {
        try {
            return doctorElasticsearchRepository.findByExperienceYearsBetween(minYears, maxYears);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public List<DoctorElasticsearch> findByRatingBetween(double minRating, double maxRating) {
        try {
            // Implementación manual ya que no existe findByRatingBetween
            List<DoctorElasticsearch> allDoctors = doctorElasticsearchRepository.findBySearchTextContaining("", PageRequest.of(0, 1000)).getContent();
            return allDoctors.stream()
                .filter(d -> d.getRating() >= minRating && d.getRating() <= maxRating)
                .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Búsqueda full-text por hospital con facets de experiencia
     * Ejemplo de uso: hospital = "San José" retorna doctores con facets por nivel
     */
    public Map<String, Object> searchByHospitalWithFacets(String hospital, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            
            // Búsqueda principal por hospital
            Page<DoctorElasticsearch> results = doctorElasticsearchRepository.findByHospitalContaining(hospital, pageable);
            
            // Obtener facets por nivel de experiencia
            Map<String, Long> experienceFacets = getExperienceFacets(hospital);
            
            // Obtener facets por especialidad
            Map<String, Long> specialtyFacets = getSpecialtyFacets(hospital);
            
            // Construir respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("doctors", results.getContent());
            response.put("totalElements", results.getTotalElements());
            response.put("totalPages", results.getTotalPages());
            response.put("currentPage", page);
            response.put("pageSize", size);
            response.put("facets", Map.of(
                "experienceLevel", experienceFacets,
                "specialty", specialtyFacets
            ));
            response.put("searchQuery", hospital);
            
            return response;
            
        } catch (Exception e) {
            throw new RuntimeException("Error en búsqueda por hospital con facets: " + e.getMessage(), e);
        }
    }
    
    /**
     * Búsqueda por hospital y nivel de experiencia específico
     * Ejemplo: hospital = "San José", experienceLevel = "Experto"
     */
    public List<DoctorElasticsearch> searchByHospitalAndExperienceLevel(String hospital, String experienceLevel) {
        try {
            return doctorElasticsearchRepository.searchByHospitalAndExperienceLevel(hospital, experienceLevel);
        } catch (Exception e) {
            throw new RuntimeException("Error en búsqueda por hospital y experiencia: " + e.getMessage(), e);
        }
    }
    
    /**
     * Búsqueda fuzzy por hospital (tolerante a errores de escritura)
     * Ejemplo: "san jose" encuentra "San José", "San Jose", etc.
     */
    public List<DoctorElasticsearch> searchByHospitalFuzzy(String hospital) {
        try {
            return doctorElasticsearchRepository.searchByHospitalFuzzy(hospital);
        } catch (Exception e) {
            throw new RuntimeException("Error en búsqueda fuzzy por hospital: " + e.getMessage(), e);
        }
    }
    
    /**
     * Búsqueda con wildcards por hospital
     * Ejemplo: "san*" encuentra "San José", "San Francisco", etc.
     */
    public List<DoctorElasticsearch> searchByHospitalWildcard(String hospital) {
        try {
            return doctorElasticsearchRepository.searchByHospitalWildcard(hospital);
        } catch (Exception e) {
            throw new RuntimeException("Error en búsqueda wildcard por hospital: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtener facets de nivel de experiencia para un hospital específico
     */
    private Map<String, Long> getExperienceFacets(String hospital) {
        try {
            List<DoctorElasticsearch> doctors = doctorElasticsearchRepository.findByHospitalContaining(hospital);
            
            return doctors.stream()
                .collect(Collectors.groupingBy(
                    DoctorElasticsearch::getExperienceLevel,
                    Collectors.counting()
                ));
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
    
    /**
     * Obtener facets de especialidad para un hospital específico
     */
    private Map<String, Long> getSpecialtyFacets(String hospital) {
        try {
            List<DoctorElasticsearch> doctors = doctorElasticsearchRepository.findByHospitalContaining(hospital);
            
            return doctors.stream()
                .collect(Collectors.groupingBy(
                    DoctorElasticsearch::getSpecialty,
                    Collectors.counting()
                ));
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
    
    /**
     * Búsqueda avanzada con múltiples criterios y facets
     */
    public Map<String, Object> advancedSearchWithFacets(String hospital, String specialty, 
                                                       String experienceLevel, boolean available, 
                                                       int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<DoctorElasticsearch> results = new ArrayList<>();
            
            // Aplicar filtros
            if (hospital != null && !hospital.isEmpty()) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findByHospitalContaining(hospital);
                } else {
                    results = results.stream()
                        .filter(d -> d.getHospital().toLowerCase().contains(hospital.toLowerCase()))
                        .collect(Collectors.toList());
                }
            }
            
            if (specialty != null && !specialty.isEmpty()) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findBySpecialty(specialty);
                } else {
                    results = results.stream()
                        .filter(d -> d.getSpecialty().equals(specialty))
                        .collect(Collectors.toList());
                }
            }
            
            if (experienceLevel != null && !experienceLevel.isEmpty()) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findByExperienceLevel(experienceLevel);
                } else {
                    results = results.stream()
                        .filter(d -> d.getExperienceLevel().equals(experienceLevel))
                        .collect(Collectors.toList());
                }
            }
            
            if (available) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findByAvailable(true);
                } else {
                    results = results.stream()
                        .filter(DoctorElasticsearch::isAvailable)
                        .collect(Collectors.toList());
                }
            }
            
            // Aplicar paginación
            int start = page * size;
            int end = Math.min(start + size, results.size());
            List<DoctorElasticsearch> paginatedResults = results.subList(start, end);
            
            // Obtener facets
            Map<String, Long> experienceFacets = getExperienceFacets(hospital != null ? hospital : "");
            Map<String, Long> specialtyFacets = getSpecialtyFacets(hospital != null ? hospital : "");
            
            // Construir respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("doctors", paginatedResults);
            response.put("totalElements", results.size());
            response.put("totalPages", (int) Math.ceil((double) results.size() / size));
            response.put("currentPage", page);
            response.put("pageSize", size);
            response.put("facets", Map.of(
                "experienceLevel", experienceFacets,
                "specialty", specialtyFacets
            ));
            response.put("filters", Map.of(
                "hospital", hospital,
                "specialty", specialty,
                "experienceLevel", experienceLevel,
                "available", available
            ));
            
            return response;
            
        } catch (Exception e) {
            throw new RuntimeException("Error en búsqueda avanzada con facets: " + e.getMessage(), e);
        }
    }
    
    /**
     * Búsqueda por query con filtros avanzados
     * Procesa queries como: hospital.term:"Centro Médico Integral"
     */
    public Map<String, Object> searchByQueryAdvanced(String query, String hospital, String specialty, 
                                                    String experienceLevel, boolean available, 
                                                    int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<DoctorElasticsearch> results = new ArrayList<>();
            
            // Procesar query si existe
            if (query != null && !query.trim().isEmpty()) {
                results = processQueryString(query);
            }
            
            // Aplicar filtros adicionales
            if (hospital != null && !hospital.isEmpty()) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findByHospitalContaining(hospital);
                } else {
                    results = results.stream()
                        .filter(d -> d.getHospital().toLowerCase().contains(hospital.toLowerCase()))
                        .collect(Collectors.toList());
                }
            }
            
            if (specialty != null && !specialty.isEmpty()) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findBySpecialty(specialty);
                } else {
                    results = results.stream()
                        .filter(d -> d.getSpecialty().equals(specialty))
                        .collect(Collectors.toList());
                }
            }
            
            if (experienceLevel != null && !experienceLevel.isEmpty()) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findByExperienceLevel(experienceLevel);
                } else {
                    results = results.stream()
                        .filter(d -> d.getExperienceLevel().equals(experienceLevel))
                        .collect(Collectors.toList());
                }
            }
            
            if (available) {
                if (results.isEmpty()) {
                    results = doctorElasticsearchRepository.findByAvailable(true);
                } else {
                    results = results.stream()
                        .filter(DoctorElasticsearch::isAvailable)
                        .collect(Collectors.toList());
                }
            }
            
            // Aplicar paginación
            int start = page * size;
            int end = Math.min(start + size, results.size());
            List<DoctorElasticsearch> paginatedResults = results.subList(start, end);
            
            // Obtener facets
            Map<String, Long> experienceFacets = getExperienceFacets(hospital != null ? hospital : "");
            Map<String, Long> specialtyFacets = getSpecialtyFacets(hospital != null ? hospital : "");
            
            // Construir respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("doctors", paginatedResults);
            response.put("totalElements", results.size());
            response.put("totalPages", (int) Math.ceil((double) results.size() / size));
            response.put("currentPage", page);
            response.put("pageSize", size);
            response.put("facets", Map.of(
                "experienceLevel", experienceFacets,
                "specialty", specialtyFacets
            ));
            response.put("filters", Map.of(
                "query", query,
                "hospital", hospital,
                "specialty", specialty,
                "experienceLevel", experienceLevel,
                "available", available
            ));
            response.put("processedQuery", query);
            
            return response;
            
        } catch (Exception e) {
            throw new RuntimeException("Error en búsqueda por query: " + e.getMessage(), e);
        }
    }
    
    /**
     * Procesar string de query para extraer filtros
     * Ejemplo: hospital.term:"Centro Médico Integral" -> filtrar por hospital
     */
    private List<DoctorElasticsearch> processQueryString(String query) {
        try {
            // Patrón para queries como: field.term:"value"
            if (query.contains(".term:")) {
                String[] parts = query.split("\\.term:");
                if (parts.length == 2) {
                    String field = parts[0].trim();
                    String value = parts[1].trim().replace("\"", "");
                    
                    switch (field.toLowerCase()) {
                        case "hospital":
                            return doctorElasticsearchRepository.findByHospitalContaining(value);
                        case "specialty":
                            return doctorElasticsearchRepository.findBySpecialty(value);
                        case "experiencelevel":
                            return doctorElasticsearchRepository.findByExperienceLevel(value);
                        case "name":
                            return doctorElasticsearchRepository.findBySearchTextContaining(value, PageRequest.of(0, 1000)).getContent();
                        default:
                            // Búsqueda general en searchText
                            return doctorElasticsearchRepository.findBySearchTextContaining(value, PageRequest.of(0, 1000)).getContent();
                    }
                }
            }
            
            // Si no es un patrón específico, hacer búsqueda general
            return doctorElasticsearchRepository.findBySearchTextContaining(query, PageRequest.of(0, 1000)).getContent();
            
        } catch (Exception e) {
            // En caso de error, retornar lista vacía
            return new ArrayList<>();
        }
    }
    
    /**
     * BÚSQUEDA EXACTA - Como WHERE field = "valor"
     * Resuelve el problema de espacios y búsqueda parcial
     */
    public Map<String, Object> searchExact(String field, String value, int page, int size) {
        try {
            List<DoctorElasticsearch> results = new ArrayList<>();
            
            // Búsqueda exacta según el campo
            switch (field.toLowerCase()) {
                case "hospital":
                    results = doctorElasticsearchRepository.searchByHospitalExact(value);
                    break;
                case "specialty":
                    results = doctorElasticsearchRepository.searchBySpecialtyExact(value);
                    break;
                case "experiencelevel":
                    results = doctorElasticsearchRepository.searchByExperienceLevelExact(value);
                    break;
                case "name":
                    results = doctorElasticsearchRepository.searchByNameExact(value);
                    break;
                default:
                    throw new IllegalArgumentException("Campo no soportado: " + field);
            }
            
            // Aplicar paginación
            int start = page * size;
            int end = Math.min(start + size, results.size());
            List<DoctorElasticsearch> paginatedResults = results.subList(start, end);
            
            // Obtener facets
            Map<String, Long> experienceFacets = getExperienceFacetsExact(field, value);
            Map<String, Long> specialtyFacets = getSpecialtyFacetsExact(field, value);
            
            // Construir respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("doctors", paginatedResults);
            response.put("totalElements", results.size());
            response.put("totalPages", (int) Math.ceil((double) results.size() / size));
            response.put("currentPage", page);
            response.put("pageSize", size);
            response.put("facets", Map.of(
                "experienceLevel", experienceFacets,
                "specialty", specialtyFacets
            ));
            response.put("search", Map.of(
                "field", field,
                "value", value,
                "type", "exact"
            ));
            
            return response;
            
        } catch (Exception e) {
            throw new RuntimeException("Error en búsqueda exacta: " + e.getMessage(), e);
        }
    }
    
    /**
     * Búsqueda exacta por hospital (WHERE hospital = "valor")
     */
    public Map<String, Object> searchByHospitalExact(String hospital, int page, int size) {
        return searchExact("hospital", hospital, page, size);
    }
    
    /**
     * Búsqueda exacta por especialidad (WHERE specialty = "valor")
     */
    public Map<String, Object> searchBySpecialtyExact(String specialty, int page, int size) {
        return searchExact("specialty", specialty, page, size);
    }
    
    /**
     * Búsqueda exacta por nivel de experiencia (WHERE experienceLevel = "valor")
     */
    public Map<String, Object> searchByExperienceLevelExact(String experienceLevel, int page, int size) {
        return searchExact("experiencelevel", experienceLevel, page, size);
    }
    
    /**
     * Obtener facets de experiencia para búsqueda exacta
     */
    private Map<String, Long> getExperienceFacetsExact(String field, String value) {
        try {
            List<DoctorElasticsearch> doctors = new ArrayList<>();
            
            switch (field.toLowerCase()) {
                case "hospital":
                    doctors = doctorElasticsearchRepository.searchByHospitalExact(value);
                    break;
                case "specialty":
                    doctors = doctorElasticsearchRepository.searchBySpecialtyExact(value);
                    break;
                case "experiencelevel":
                    doctors = doctorElasticsearchRepository.searchByExperienceLevelExact(value);
                    break;
                default:
                    return new HashMap<>();
            }
            
            return doctors.stream()
                .collect(Collectors.groupingBy(
                    DoctorElasticsearch::getExperienceLevel,
                    Collectors.counting()
                ));
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
    
    /**
     * Obtener facets de especialidad para búsqueda exacta
     */
    private Map<String, Long> getSpecialtyFacetsExact(String field, String value) {
        try {
            List<DoctorElasticsearch> doctors = new ArrayList<>();
            
            switch (field.toLowerCase()) {
                case "hospital":
                    doctors = doctorElasticsearchRepository.searchByHospitalExact(value);
                    break;
                case "specialty":
                    doctors = doctorElasticsearchRepository.searchBySpecialtyExact(value);
                    break;
                case "experiencelevel":
                    doctors = doctorElasticsearchRepository.searchByExperienceLevelExact(value);
                    break;
                default:
                    return new HashMap<>();
            }
            
            return doctors.stream()
                .collect(Collectors.groupingBy(
                    DoctorElasticsearch::getSpecialty,
                    Collectors.counting()
                ));
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
    
    /**
     * Sincronización inteligente desde la base de datos
     * Verifica si ya existe antes de crear
     */
    public Map<String, Object> syncFromDatabase() {
        try {
            Map<String, Object> response = new HashMap<>();
            
            // Obtener todos los doctores de la base de datos
            List<Doctor> doctorsFromDB = doctorRepository.findAll();
            
            if (doctorsFromDB.isEmpty()) {
                response.put("message", "No hay doctores en la base de datos para sincronizar");
                response.put("syncedCount", 0);
                response.put("skippedCount", 0);
                response.put("status", "warning");
                return response;
            }
            
            int syncedCount = 0;
            int skippedCount = 0;
            List<String> syncedIds = new ArrayList<>();
            List<String> skippedIds = new ArrayList<>();
            List<String> errorIds = new ArrayList<>();
            
            for (Doctor doctor : doctorsFromDB) {
                try {
                    // Verificar si ya existe en Elasticsearch
                    boolean exists = checkIfDoctorExists(doctor.getId());
                    
                    if (exists) {
                        // Ya existe, saltar al siguiente
                        skippedCount++;
                        skippedIds.add(doctor.getId());
                        System.out.println("⏭️ Doctor ya existe, saltando: " + doctor.getName() + " (ID: " + doctor.getId() + ")");
                        continue;
                    }
                    
                    // Convertir a formato Elasticsearch
                    DoctorElasticsearch doctorES = new DoctorElasticsearch(
                        doctor.getId(), doctor.getName(), doctor.getSpecialty(), doctor.getImg(),
                        doctor.getExperienceYears(), doctor.getRating(), doctor.getHospital(),
                        doctor.isAvailable(), doctor.getDescription(), doctor.getTags(),
                        doctor.getDiasLaborales(), doctor.getHorarioEntrada(), doctor.getHorarioSalida(),
                        doctor.getDuracionCita(), doctor.getHorariosDisponibles()
                    );
                    
                    // Intentar guardar usando el template
                    try {
                        // Usar ElasticsearchRestTemplate directamente
                        elasticsearchTemplate.save(doctorES);
                        syncedCount++;
                        syncedIds.add(doctor.getId());
                        System.out.println("✅ Doctor sincronizado: " + doctor.getName() + " (ID: " + doctor.getId() + ")");
                    } catch (Exception e) {
                        System.err.println("❌ Error guardando doctor " + doctor.getId() + ": " + e.getMessage());
                        errorIds.add(doctor.getId());
                    }
                    
                } catch (Exception e) {
                    System.err.println("❌ Error procesando doctor " + doctor.getId() + ": " + e.getMessage());
                    errorIds.add(doctor.getId());
                }
            }
            
            response.put("message", "Sincronización completada");
            response.put("totalDoctors", doctorsFromDB.size());
            response.put("syncedCount", syncedCount);
            response.put("skippedCount", skippedCount);
            response.put("errorCount", errorIds.size());
            response.put("syncedIds", syncedIds);
            response.put("skippedIds", skippedIds);
            response.put("errorIds", errorIds);
            response.put("timestamp", System.currentTimeMillis());
            response.put("status", "success");
            
            System.out.println("🎉 Sincronización completada: " + syncedCount + " nuevos, " + skippedCount + " existentes, " + errorIds.size() + " errores");
            return response;
            
        } catch (Exception e) {
            System.err.println("❌ Error en sincronización: " + e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en sincronización: " + e.getMessage());
            errorResponse.put("status", "error");
            return errorResponse;
        }
    }
    
    /**
     * Verificar si un doctor ya existe en Elasticsearch
     */
    private boolean checkIfDoctorExists(String doctorId) {
        try {
            // Usar el template para buscar por ID
            DoctorElasticsearch existing = elasticsearchTemplate.get(doctorId, DoctorElasticsearch.class);
            return existing != null;
        } catch (Exception e) {
            // Si hay error, asumir que no existe
            return false;
        }
    }
    
    /**
     * Obtener estado de sincronización
     */
    public Map<String, Object> getSyncStatus() {
        try {
            Map<String, Object> response = new HashMap<>();
            
            // Contar doctores en la base de datos
            long dbCount = doctorRepository.count();
            
            // Contar doctores en Elasticsearch usando el template
            long esCount = 0;
            try {
                // Crear query para contar todos
                Query query = Query.findAll();
                esCount = elasticsearchTemplate.count(query, DoctorElasticsearch.class);
            } catch (Exception e) {
                System.err.println("⚠️ Error contando en Elasticsearch: " + e.getMessage());
                // Fallback: usar método manual del repositorio
                try {
                    esCount = doctorElasticsearchRepository.countAllDoctors();
                } catch (Exception e2) {
                    System.err.println("⚠️ Fallback también falló: " + e2.getMessage());
                }
            }
            
            // Calcular porcentaje de sincronización
            double syncPercentage = dbCount > 0 ? (esCount * 100.0 / dbCount) : 0;
            
            response.put("databaseCount", dbCount);
            response.put("elasticsearchCount", esCount);
            response.put("syncPercentage", Math.round(syncPercentage * 100.0) / 100.0);
            response.put("status", "success");
            
            if (syncPercentage == 100) {
                response.put("message", "Sincronización completa");
            } else if (syncPercentage > 0) {
                response.put("message", "Sincronización parcial");
            } else {
                response.put("message", "Sin sincronización");
            }
            
            return response;
            
        } catch (Exception e) {
            System.err.println("❌ Error obteniendo estado de sincronización: " + e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error obteniendo estado de sincronización: " + e.getMessage());
            errorResponse.put("status", "error");
            return errorResponse;
        }
    }
}
