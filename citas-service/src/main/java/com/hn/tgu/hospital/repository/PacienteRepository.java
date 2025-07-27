package com.hn.tgu.hospital.repository;

import com.hn.tgu.hospital.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {
  
  /**
   * Buscar paciente por email
   */
  Optional<Paciente> findByEmail(String email);
  
  /**
   * Buscar pacientes por nombre o apellido
   */
  List<Paciente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
  
  /**
   * Buscar pacientes por género
   */
  List<Paciente> findByGenero(String genero);
  
  /**
   * Verificar si existe paciente por email
   */
  boolean existsByEmail(String email);
  
  /**
   * Buscar pacientes por nombre con consulta personalizada
   */
  @Query("SELECT p FROM Paciente p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(p.apellido) LIKE LOWER(CONCAT('%', :nombre, '%'))")
  List<Paciente> buscarPorNombre(@Param("nombre") String nombre);
  
  /**
   * Contar pacientes por género
   */
  long countByGenero(String genero);
  
  /**
   * Buscar pacientes por ciudad (dirección)
   */
  @Query("SELECT p FROM Paciente p WHERE LOWER(p.direccion) LIKE LOWER(CONCAT('%', :ciudad, '%'))")
  List<Paciente> findByCiudad(@Param("ciudad") String ciudad);
} 