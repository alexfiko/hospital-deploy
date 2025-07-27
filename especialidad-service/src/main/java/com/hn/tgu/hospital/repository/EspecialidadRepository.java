package com.hn.tgu.hospital.repository;

import com.hn.tgu.hospital.entity.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, String> {
  
  /**
   * Buscar especialidades por nombre (ignorando mayúsculas/minúsculas)
   */
  List<Especialidad> findByNombreContainingIgnoreCase(String nombre);
  
  /**
   * Buscar especialidades activas
   */
  List<Especialidad> findByActivaTrue();
  
  /**
   * Buscar especialidad por nombre exacto
   */
  Optional<Especialidad> findByNombre(String nombre);
  
  /**
   * Contar especialidades activas
   */
  long countByActivaTrue();
  
  /**
   * Buscar especialidades por nombre con consulta personalizada
   */
  @Query("SELECT e FROM Especialidad e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
  List<Especialidad> buscarPorNombre(@Param("nombre") String nombre);
  
  /**
   * Verificar si existe una especialidad por nombre
   */
  boolean existsByNombre(String nombre);
  
  /**
   * Buscar especialidades por estado de actividad
   */
  List<Especialidad> findByActiva(boolean activa);
} 