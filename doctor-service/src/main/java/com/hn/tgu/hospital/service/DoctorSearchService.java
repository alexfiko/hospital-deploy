package com.hn.tgu.hospital.service;

import com.hn.tgu.hospital.dto.DoctorDTO;
import com.hn.tgu.hospital.entity.Doctor;
import com.hn.tgu.hospital.mapper.DoctorMapper;
import com.hn.tgu.hospital.repository.DoctorRepository;
import com.hn.tgu.hospital.search.DoctorIndex;
import com.hn.tgu.hospital.search.DoctorSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class DoctorSearchService {

    @Autowired
    private DoctorSearchRepository doctorSearchRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    /**
     * Búsqueda avanzada con facets usando Elasticsearch
     */
    public List<DoctorDTO> buscarConFacets(String query, String specialty, String hospital, 
                                          Integer minExperience, Integer maxExperience, 
                                          Double minRating, Double maxRating, 
                                          Boolean available, List<String> tags) {
        try {
            System.out.println("🔍 [DoctorSearchService] Intentando búsqueda con Elasticsearch...");
            
            // Usar Elasticsearch real
            List<DoctorIndex> results = doctorSearchRepository.searchAdvanced(
                query, specialty, hospital, minExperience, maxExperience, 
                minRating, maxRating, available, tags);
            
            System.out.println("✅ [DoctorSearchService] Búsqueda Elasticsearch exitosa: " + results.size() + " resultados");
            
            return results.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda Elasticsearch: " + e.getMessage());
            System.err.println("🔄 [DoctorSearchService] Fallback a JPA...");
            
            // Fallback a JPA
            return buscarConJPA(query, specialty, hospital, minExperience, maxExperience, minRating, maxRating, available, tags);
        }
    }

    /**
     * Búsqueda con sugerencias usando Elasticsearch
     */
    public List<DoctorDTO> buscarConSugerencias(String query) {
        try {
            System.out.println("🔍 [DoctorSearchService] Búsqueda con sugerencias en Elasticsearch: " + query);
            
            // Usar Elasticsearch real
            List<DoctorIndex> results = doctorSearchRepository.searchAdvanced(
                query, null, null, null, null, null, null, null, null);
            
            return results.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda con sugerencias: " + e.getMessage());
            return buscarConJPA(query, null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Obtener sugerencias de autocompletado
     */
    public List<String> obtenerSugerencias(String prefix) {
        try {
            List<DoctorIndex> doctors = doctorSearchRepository.findByNameStartingWithIgnoreCase(prefix);
            return doctors.stream()
                    .map(DoctorIndex::getName)
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error obteniendo sugerencias: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Búsqueda por especialidad
     */
    public List<DoctorDTO> buscarPorEspecialidad(String specialty) {
        try {
            System.out.println("🔍 [DoctorSearchService] Búsqueda por especialidad en Elasticsearch: " + specialty);
            
            List<DoctorIndex> doctors = doctorSearchRepository.findBySpecialty(specialty);
            return doctors.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por especialidad: " + e.getMessage());
            return buscarConJPA(null, specialty, null, null, null, null, null, null, null);
        }
    }

    /**
     * Búsqueda por hospital
     */
    public List<DoctorDTO> buscarPorHospital(String hospital) {
        try {
            List<DoctorIndex> doctors = doctorSearchRepository.findByHospital(hospital);
            return doctors.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por hospital: " + e.getMessage());
            return buscarConJPA(null, null, hospital, null, null, null, null, null, null);
        }
    }

    /**
     * Búsqueda por disponibilidad
     */
    public List<DoctorDTO> buscarPorDisponibilidad(boolean available) {
        try {
            List<DoctorIndex> doctors = doctorSearchRepository.findByAvailable(available);
            return doctors.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por disponibilidad: " + e.getMessage());
            return buscarConJPA(null, null, null, null, null, null, null, available, null);
        }
    }

    /**
     * Búsqueda por rango de experiencia
     */
    public List<DoctorDTO> buscarPorExperiencia(int minYears, int maxYears) {
        try {
            // Por ahora usamos JPA como fallback hasta implementar la consulta de rango
            return buscarConJPA(null, null, null, minYears, maxYears, null, null, null, null);
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por experiencia: " + e.getMessage());
            return buscarConJPA(null, null, null, minYears, maxYears, null, null, null, null);
        }
    }

    /**
     * Búsqueda por rango de rating
     */
    public List<DoctorDTO> buscarPorRating(double minRating, double maxRating) {
        try {
            // Por ahora usamos JPA como fallback hasta implementar la consulta de rango
            return buscarConJPA(null, null, null, null, null, minRating, maxRating, null, null);
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por rating: " + e.getMessage());
            return buscarConJPA(null, null, null, null, null, minRating, maxRating, null, null);
        }
    }

    /**
     * Búsqueda por tags
     */
    public List<DoctorDTO> buscarPorTags(List<String> tags) {
        try {
            List<DoctorIndex> doctors = doctorSearchRepository.findByTagsIn(tags);
            return doctors.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por tags: " + e.getMessage());
            return buscarConJPA(null, null, null, null, null, null, null, null, tags);
        }
    }

    /**
     * Sincronizar datos de JPA a Elasticsearch
     */
    public void sincronizarDatos() {
        try {
            List<Doctor> doctors = doctorRepository.findAll();
            
            List<DoctorIndex> doctorIndices = doctors.stream()
                    .map(this::convertToDoctorIndex)
                    .collect(Collectors.toList());
            
            doctorSearchRepository.saveAll(doctorIndices);
            System.out.println("✅ " + doctors.size() + " doctores sincronizados en Elasticsearch");
            
        } catch (Exception e) {
            System.err.println("❌ Error sincronizando datos: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Procesar query del frontend que viene como string (ej: "specialty:Cardiología AND hospital:Clínica Vida")
     */
    public List<DoctorDTO> procesarQueryFrontend(String queryString) {
        try {
            System.out.println("🔍 [DoctorSearchService] Procesando query del frontend: " + queryString);
            
            if (queryString == null || queryString.trim().isEmpty()) {
                System.out.println("🔍 [DoctorSearchService] Query vacía, devolviendo todos los doctores");
                return buscarConJPA(null, null, null, null, null, null, null, null, null);
            }
            
            // Parsear la query del frontend
            var parsedQuery = parseFrontendQuery(queryString);
            
            // Usar Elasticsearch con los parámetros parseados
            List<DoctorIndex> results = doctorSearchRepository.searchAdvanced(
                parsedQuery.get("query"),
                parsedQuery.get("specialty"),
                parsedQuery.get("hospital"),
                parsedQuery.get("minExperience") != null ? Integer.parseInt(parsedQuery.get("minExperience")) : null,
                parsedQuery.get("maxExperience") != null ? Integer.parseInt(parsedQuery.get("maxExperience")) : null,
                parsedQuery.get("minRating") != null ? Double.parseDouble(parsedQuery.get("minRating")) : null,
                parsedQuery.get("maxRating") != null ? Double.parseDouble(parsedQuery.get("maxRating")) : null,
                parsedQuery.get("available") != null ? Boolean.parseBoolean(parsedQuery.get("available")) : null,
                parsedQuery.get("tags") != null ? List.of(parsedQuery.get("tags").split(",")) : null
            );
            
            return results.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println("❌ Error procesando query del frontend: " + e.getMessage());
            return buscarConJPA(null, null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Parsear query del frontend (ej: "specialty:Cardiología AND hospital:Clínica Vida")
     */
    private Map<String, String> parseFrontendQuery(String queryString) {
        Map<String, String> parsed = new HashMap<>();
        
        try {
            // Dividir por AND
            String[] parts = queryString.split(" AND ");
            
            for (String part : parts) {
                part = part.trim();
                if (part.contains(":")) {
                    String[] keyValue = part.split(":", 2);
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim().replace("\"", "");
                    
                    switch (key) {
                        case "specialty":
                            parsed.put("specialty", value);
                            break;
                        case "hospital":
                            parsed.put("hospital", value);
                            break;
                        case "city":
                            parsed.put("city", value);
                            break;
                        case "available":
                            parsed.put("available", value);
                            break;
                        case "experienceYears":
                            if (value.startsWith(">=")) {
                                parsed.put("minExperience", value.substring(2));
                            } else if (value.startsWith("<=")) {
                                parsed.put("maxExperience", value.substring(2));
                            }
                            break;
                        case "rating":
                            if (value.startsWith(">=")) {
                                parsed.put("minRating", value.substring(2));
                            } else if (value.startsWith("<=")) {
                                parsed.put("maxRating", value.substring(2));
                            }
                            break;
                    }
                }
            }
            
            System.out.println("🔍 [parseFrontendQuery] Query parseada: " + parsed);
            
        } catch (Exception e) {
            System.err.println("❌ Error parseando query: " + e.getMessage());
        }
        
        return parsed;
    }

    // Métodos de fallback usando JPA
    private List<DoctorDTO> buscarConJPA(String query, String specialty, String hospital, 
                                        Integer minExperience, Integer maxExperience, 
                                        Double minRating, Double maxRating, 
                                        Boolean available, List<String> tags) {
        
        System.out.println("🔍 [buscarConJPA] Parámetros recibidos:");
        System.out.println("  - Query: " + query);
        System.out.println("  - Specialty: " + specialty);
        System.out.println("  - Hospital: " + hospital);
        System.out.println("  - MinExperience: " + minExperience);
        System.out.println("  - MaxExperience: " + maxExperience);
        System.out.println("  - MinRating: " + minRating);
        System.out.println("  - MaxRating: " + maxRating);
        System.out.println("  - Available: " + available);
        System.out.println("  - Tags: " + tags);
        
        List<Doctor> doctors = doctorRepository.findAll();
        
        // Aplicar filtros en memoria
        List<Doctor> filteredDoctors = doctors.stream()
                .filter(doctor -> specialty == null || doctor.getSpecialty().equalsIgnoreCase(specialty))
                .filter(doctor -> hospital == null || doctor.getHospital().equalsIgnoreCase(hospital))
                .filter(doctor -> minExperience == null || doctor.getExperienceYears() >= minExperience)
                .filter(doctor -> maxExperience == null || doctor.getExperienceYears() <= maxExperience)
                .filter(doctor -> minRating == null || doctor.getRating() >= minRating)
                .filter(doctor -> maxRating == null || doctor.getRating() <= maxRating)
                .filter(doctor -> available == null || doctor.isAvailable() == available)
                .collect(Collectors.toList());
        
        System.out.println("🔍 [buscarConJPA] Doctores filtrados: " + filteredDoctors.size() + " de " + doctors.size());
        
        return filteredDoctors.stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    private DoctorDTO convertToDoctorDTO(DoctorIndex doctorIndex) {
        DoctorDTO dto = new DoctorDTO();
        dto.id = doctorIndex.getId();
        dto.name = doctorIndex.getName();
        dto.specialty = doctorIndex.getSpecialty();
        dto.hospital = doctorIndex.getHospital();
        dto.description = doctorIndex.getDescription();
        dto.experienceYears = doctorIndex.getExperienceYears();
        dto.rating = doctorIndex.getRating();
        dto.available = doctorIndex.isAvailable();
        dto.tags = doctorIndex.getTags();
        dto.diasLaborales = doctorIndex.getDiasLaborales();
        return dto;
    }

    private DoctorIndex convertToDoctorIndex(Doctor doctor) {
        DoctorIndex index = new DoctorIndex();
        index.setId(doctor.getId());
        index.setName(doctor.getName());
        index.setSpecialty(doctor.getSpecialty());
        index.setHospital(doctor.getHospital());
        index.setDescription(doctor.getDescription());
        index.setExperienceYears(doctor.getExperienceYears());
        index.setRating(doctor.getRating());
        index.setAvailable(doctor.isAvailable());
        index.setTags(doctor.getTags());
        index.setDiasLaborales(doctor.getDiasLaborales());
        index.setSearchText(doctor.getName() + " " + doctor.getSpecialty() + " " + doctor.getDescription());
        return index;
    }
}
