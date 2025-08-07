package com.hn.tgu.hospital.repository;

import com.hn.tgu.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
  
  // Buscar por especialidad
  List<Doctor> findBySpecialty(String specialty);
  
  // Buscar por hospital
  List<Doctor> findByHospital(String hospital);
  
  // Buscar por disponibilidad
  List<Doctor> findByAvailable(boolean available);
  
  // Buscar por nombre (contiene)
  List<Doctor> findByNameContainingIgnoreCase(String name);
  
  // Buscar por nombre que empiece con (para autocompletado)
  List<Doctor> findByNameStartingWithIgnoreCase(String name);
  
  // Buscar por especialidad y disponibilidad
  List<Doctor> findBySpecialtyAndAvailable(String specialty, boolean available);
  
  // Buscar por hospital y disponibilidad
  List<Doctor> findByHospitalAndAvailable(String hospital, boolean available);
  
  // Buscar por rango de experiencia
  List<Doctor> findByExperienceYearsBetween(int minYears, int maxYears);
  
  // Buscar por rango de rating
  List<Doctor> findByRatingBetween(double minRating, double maxRating);
  
  // Buscar por tags
  List<Doctor> findByTagsIn(List<String> tags);
  
  // Query personalizada para buscar por múltiples criterios
  @Query("SELECT d FROM Doctor d WHERE " +
         "(:specialty IS NULL OR d.specialty = :specialty) AND " +
         "(:hospital IS NULL OR d.hospital = :hospital) AND " +
         "(:available IS NULL OR d.available = :available) AND " +
         "(:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))")
  List<Doctor> findByFilters(@Param("specialty") String specialty,
                            @Param("hospital") String hospital,
                            @Param("available") Boolean available,
                            @Param("name") String name);
} 