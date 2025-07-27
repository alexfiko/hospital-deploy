package com.hn.tgu.hospital.service;

import com.hn.tgu.hospital.dto.EspecialidadDTO;
import com.hn.tgu.hospital.entity.Especialidad;
import com.hn.tgu.hospital.mapper.EspecialidadMapper;
import com.hn.tgu.hospital.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {
  
  @Autowired
  private EspecialidadRepository especialidadRepository;
  
  @Autowired
  private EspecialidadMapper especialidadMapper;
  
  /**
   * Obtener todas las especialidades
   */
  public List<EspecialidadDTO> getAllEspecialidades() {
    List<Especialidad> especialidades = especialidadRepository.findAll();
    return especialidadMapper.toDTOList(especialidades);
  }
  
  /**
   * Obtener especialidades activas
   */
  public List<EspecialidadDTO> getEspecialidadesActivas() {
    List<Especialidad> especialidades = especialidadRepository.findByActivaTrue();
    return especialidadMapper.toDTOList(especialidades);
  }
  
  /**
   * Obtener especialidad por ID
   */
  public Optional<EspecialidadDTO> getEspecialidadById(String id) {
    Optional<Especialidad> especialidad = especialidadRepository.findById(id);
    return especialidad.map(especialidadMapper::toDTO);
  }
  
  /**
   * Obtener especialidad por nombre
   */
  public Optional<EspecialidadDTO> getEspecialidadByNombre(String nombre) {
    Optional<Especialidad> especialidad = especialidadRepository.findByNombre(nombre);
    return especialidad.map(especialidadMapper::toDTO);
  }
  
  /**
   * Buscar especialidades por nombre
   */
  public List<EspecialidadDTO> buscarPorNombre(String nombre) {
    List<Especialidad> especialidades = especialidadRepository.buscarPorNombre(nombre);
    return especialidadMapper.toDTOList(especialidades);
  }
  
  /**
   * Crear nueva especialidad
   */
  public EspecialidadDTO createEspecialidad(EspecialidadDTO especialidadDTO) {
    // Verificar si ya existe una especialidad con el mismo nombre
    if (especialidadRepository.existsByNombre(especialidadDTO.getNombre())) {
      throw new RuntimeException("Ya existe una especialidad con el nombre: " + especialidadDTO.getNombre());
    }
    
    Especialidad especialidad = especialidadMapper.toEntity(especialidadDTO);
    Especialidad savedEspecialidad = especialidadRepository.save(especialidad);
    return especialidadMapper.toDTO(savedEspecialidad);
  }
  
  /**
   * Actualizar especialidad
   */
  public Optional<EspecialidadDTO> updateEspecialidad(String id, EspecialidadDTO especialidadDTO) {
    Optional<Especialidad> existingEspecialidad = especialidadRepository.findById(id);
    
    if (existingEspecialidad.isPresent()) {
      Especialidad especialidad = existingEspecialidad.get();
      
      // Verificar si el nuevo nombre ya existe en otra especialidad
      if (!especialidad.getNombre().equals(especialidadDTO.getNombre()) && 
          especialidadRepository.existsByNombre(especialidadDTO.getNombre())) {
        throw new RuntimeException("Ya existe una especialidad con el nombre: " + especialidadDTO.getNombre());
      }
      
      especialidad.setNombre(especialidadDTO.getNombre());
      especialidad.setDescripcion(especialidadDTO.getDescripcion());
      especialidad.setImagen(especialidadDTO.getImagen());
      especialidad.setActiva(especialidadDTO.isActiva());
      
      Especialidad updatedEspecialidad = especialidadRepository.save(especialidad);
      return Optional.of(especialidadMapper.toDTO(updatedEspecialidad));
    }
    
    return Optional.empty();
  }
  
  /**
   * Eliminar especialidad
   */
  public boolean deleteEspecialidad(String id) {
    if (especialidadRepository.existsById(id)) {
      especialidadRepository.deleteById(id);
      return true;
    }
    return false;
  }
  
  /**
   * Activar/Desactivar especialidad
   */
  public Optional<EspecialidadDTO> toggleEspecialidad(String id) {
    Optional<Especialidad> especialidad = especialidadRepository.findById(id);
    
    if (especialidad.isPresent()) {
      Especialidad esp = especialidad.get();
      esp.setActiva(!esp.isActiva());
      Especialidad updatedEspecialidad = especialidadRepository.save(esp);
      return Optional.of(especialidadMapper.toDTO(updatedEspecialidad));
    }
    
    return Optional.empty();
  }
  
  /**
   * Contar especialidades activas
   */
  public long countEspecialidadesActivas() {
    return especialidadRepository.countByActivaTrue();
  }
  
  /**
   * Verificar si existe especialidad por ID
   */
  public boolean existsById(String id) {
    return especialidadRepository.existsById(id);
  }
  
  /**
   * Verificar si existe especialidad por nombre
   */
  public boolean existsByNombre(String nombre) {
    return especialidadRepository.existsByNombre(nombre);
  }
}