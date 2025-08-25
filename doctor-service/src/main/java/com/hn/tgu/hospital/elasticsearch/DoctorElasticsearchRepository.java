package com.hn.tgu.hospital.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorElasticsearchRepository extends ElasticsearchRepository<DoctorElasticsearch, String> {

    // Métodos básicos heredados de ElasticsearchRepository
    // Estos métodos ya están disponibles automáticamente:
    // - save(DoctorElasticsearch entity)
    // - count()
    // - findAll()
    // - findById(String id)
    // - deleteById(String id)

    // Búsqueda básica por texto
    List<DoctorElasticsearch> findBySearchTextContaining(String searchText);
    
    // Búsqueda por especialidad
    List<DoctorElasticsearch> findBySpecialty(String specialty);
    
    // Búsqueda por hospital (full-text)
    List<DoctorElasticsearch> findByHospitalContaining(String hospital);
    
    // Búsqueda por nivel de experiencia
    List<DoctorElasticsearch> findByExperienceLevel(String experienceLevel);
    
    // Búsqueda por disponibilidad
    List<DoctorElasticsearch> findByAvailable(boolean available);
    
    // Búsqueda por rango de años de experiencia
    List<DoctorElasticsearch> findByExperienceYearsBetween(int minYears, int maxYears);
    
    // Búsqueda por rating mínimo
    List<DoctorElasticsearch> findByRatingGreaterThanEqual(double minRating);
    
    // Búsqueda combinada: hospital + especialidad
    List<DoctorElasticsearch> findByHospitalContainingAndSpecialty(String hospital, String specialty);
    
    // Búsqueda combinada: hospital + nivel de experiencia
    List<DoctorElasticsearch> findByHospitalContainingAndExperienceLevel(String hospital, String experienceLevel);
    
    // Búsqueda paginada por texto
    Page<DoctorElasticsearch> findBySearchTextContaining(String searchText, Pageable pageable);
    
    // Búsqueda paginada por hospital
    Page<DoctorElasticsearch> findByHospitalContaining(String hospital, Pageable pageable);
    
    // Búsqueda paginada por especialidad
    Page<DoctorElasticsearch> findBySpecialty(String specialty, Pageable pageable);
    
    // Búsqueda paginada por nivel de experiencia
    Page<DoctorElasticsearch> findByExperienceLevel(String experienceLevel, Pageable pageable);
    
    // Query personalizada para búsqueda full-text avanzada
    @Query("{\"bool\": {\"should\": [{\"match\": {\"hospital\": \"?0\"}}, {\"match\": {\"searchText\": \"?0\"}}], \"minimum_should_match\": 1}}")
    List<DoctorElasticsearch> searchByHospitalFullText(String query);
    
    // Query personalizada para facets de experiencia
    @Query("{\"bool\": {\"must\": [{\"match\": {\"hospital\": \"?0\"}}], \"filter\": [{\"term\": {\"experienceLevel\": \"?1\"}}]}}")
    List<DoctorElasticsearch> searchByHospitalAndExperienceLevel(String hospital, String experienceLevel);
    
    // Query personalizada para búsqueda fuzzy por hospital
    @Query("{\"fuzzy\": {\"hospital\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    List<DoctorElasticsearch> searchByHospitalFuzzy(String hospital);
    
    // Query personalizada para búsqueda con wildcards
    @Query("{\"wildcard\": {\"hospital\": {\"value\": \"*?0*\"}}}")
    List<DoctorElasticsearch> searchByHospitalWildcard(String hospital);
    
    // BÚSQUEDA EXACTA - Como WHERE field = "valor"
    @Query("{\"term\": {\"hospitalKeyword\": \"?0\"}}")
    List<DoctorElasticsearch> searchByHospitalExact(String hospital);
    
    @Query("{\"term\": {\"specialty\": \"?0\"}}")
    List<DoctorElasticsearch> searchBySpecialtyExact(String specialty);
    
    @Query("{\"term\": {\"experienceLevel\": \"?0\"}}")
    List<DoctorElasticsearch> searchByExperienceLevelExact(String experienceLevel);
    
    @Query("{\"term\": {\"name\": \"?0\"}}")
    List<DoctorElasticsearch> searchByNameExact(String name);
    
    // Métodos manuales si los heredados no funcionan
    @Query("{\"match_all\": {}}")
    List<DoctorElasticsearch> findAllDoctors();
    
    @Query("{\"match_all\": {}}")
    long countAllDoctors();
}
