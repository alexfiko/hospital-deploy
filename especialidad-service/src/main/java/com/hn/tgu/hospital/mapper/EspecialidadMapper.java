package com.hn.tgu.hospital.mapper;

import com.hn.tgu.hospital.dto.EspecialidadDTO;
import com.hn.tgu.hospital.entity.Especialidad;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EspecialidadMapper {
  
  /**
   * Convierte una entidad Especialidad a DTO
   */
  public EspecialidadDTO toDTO(Especialidad especialidad) {
    if (especialidad == null) {
      return null;
    }
    
    return new EspecialidadDTO(
      especialidad.getId(),
      especialidad.getNombre(),
      especialidad.getDescripcion(),
      especialidad.getImagen(),
      especialidad.isActiva(),
      especialidad.getCreatedAt(),
      especialidad.getUpdatedAt()
    );
  }
  
  /**
   * Convierte un DTO a entidad Especialidad
   */
  public Especialidad toEntity(EspecialidadDTO dto) {
    if (dto == null) {
      return null;
    }
    
    Especialidad especialidad = new Especialidad();
    especialidad.setId(dto.getId());
    especialidad.setNombre(dto.getNombre());
    especialidad.setDescripcion(dto.getDescripcion());
    especialidad.setImagen(dto.getImagen());
    especialidad.setActiva(dto.isActiva());
    
    return especialidad;
  }
  
  /**
   * Convierte una lista de entidades a DTOs
   */
  public List<EspecialidadDTO> toDTOList(List<Especialidad> especialidades) {
    if (especialidades == null) {
      return null;
    }
    
    return especialidades.stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
  }
  
  /**
   * Convierte una lista de DTOs a entidades
   */
  public List<Especialidad> toEntityList(List<EspecialidadDTO> dtos) {
    if (dtos == null) {
      return null;
    }
    
    return dtos.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }
} 