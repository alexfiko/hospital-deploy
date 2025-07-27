package com.hn.tgu.hospital.mapper;

import com.hn.tgu.hospital.dto.DoctorDTO;
import com.hn.tgu.hospital.dto.HorarioTrabajoDTO;
import com.hn.tgu.hospital.entity.Doctor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorMapper {
  
  // Convertir entidad a DTO
  public DoctorDTO toDTO(Doctor doctor) {
    if (doctor == null) {
      return null;
    }
    
    HorarioTrabajoDTO horarioTrabajo = new HorarioTrabajoDTO(
      doctor.getDiasLaborales(),
      doctor.getHorarioEntrada(),
      doctor.getHorarioSalida(),
      doctor.getDuracionCita()
    );
    
    return new DoctorDTO(
      doctor.getId(),
      doctor.getName(),
      doctor.getSpecialty(),
      doctor.getImg(),
      doctor.getExperienceYears(),
      doctor.getRating(),
      doctor.getHospital(),
      doctor.isAvailable(),
      doctor.getDescription(),
      doctor.getTags(),
      horarioTrabajo,
      doctor.getHorariosDisponibles()
    );
  }
  
  // Convertir DTO a entidad
  public Doctor toEntity(DoctorDTO dto) {
    if (dto == null) {
      return null;
    }
    
    return new Doctor(
      dto.name,
      dto.specialty,
      dto.img,
      dto.experienceYears,
      dto.rating,
      dto.hospital,
      dto.available,
      dto.description,
      dto.tags,
      dto.horarioTrabajo != null ? dto.horarioTrabajo.diasLaborales : null,
      dto.horarioTrabajo != null ? dto.horarioTrabajo.horarioEntrada : null,
      dto.horarioTrabajo != null ? dto.horarioTrabajo.horarioSalida : null,
      dto.horarioTrabajo != null ? dto.horarioTrabajo.duracionCita : 30,
      dto.horariosDisponibles
    );
  }
  
  // Convertir lista de entidades a lista de DTOs
  public List<DoctorDTO> toDTOList(List<Doctor> doctors) {
    if (doctors == null) {
      return null;
    }
    
    return doctors.stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
  }
  
  // Actualizar entidad con datos del DTO
  public void updateEntity(Doctor doctor, DoctorDTO dto) {
    if (doctor == null || dto == null) {
      return;
    }
    
    doctor.setName(dto.name);
    doctor.setSpecialty(dto.specialty);
    doctor.setImg(dto.img);
    doctor.setExperienceYears(dto.experienceYears);
    doctor.setRating(dto.rating);
    doctor.setHospital(dto.hospital);
    doctor.setAvailable(dto.available);
    doctor.setDescription(dto.description);
    doctor.setTags(dto.tags);
    
    if (dto.horarioTrabajo != null) {
      doctor.setDiasLaborales(dto.horarioTrabajo.diasLaborales);
      doctor.setHorarioEntrada(dto.horarioTrabajo.horarioEntrada);
      doctor.setHorarioSalida(dto.horarioTrabajo.horarioSalida);
      doctor.setDuracionCita(dto.horarioTrabajo.duracionCita);
    }
    
    doctor.setHorariosDisponibles(dto.horariosDisponibles);
  }
} 