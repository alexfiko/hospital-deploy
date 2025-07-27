package com.hn.tgu.hospital.service;

import com.hn.tgu.hospital.dto.DoctorDTO;
import com.hn.tgu.hospital.entity.Doctor;
import com.hn.tgu.hospital.mapper.DoctorMapper;
import com.hn.tgu.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

  @Autowired
  private DoctorRepository doctorRepository;
  
  @Autowired
  private DoctorMapper doctorMapper;

  // Obtener todos los doctores
  public List<DoctorDTO> obtenerDoctores() {
    List<Doctor> doctors = doctorRepository.findAll();
    return doctorMapper.toDTOList(doctors);
  }
  
  // Obtener doctor por ID
  public Optional<DoctorDTO> obtenerDoctorPorId(String id) {
    Optional<Doctor> doctor = doctorRepository.findById(id);
    return doctor.map(doctorMapper::toDTO);
  }
  
  // Crear nuevo doctor
  public DoctorDTO crearDoctor(DoctorDTO doctorDTO) {
    Doctor doctor = doctorMapper.toEntity(doctorDTO);
    Doctor savedDoctor = doctorRepository.save(doctor);
    return doctorMapper.toDTO(savedDoctor);
  }
  
  // Actualizar doctor
  public Optional<DoctorDTO> actualizarDoctor(String id, DoctorDTO doctorDTO) {
    Optional<Doctor> existingDoctor = doctorRepository.findById(id);
    
    if (existingDoctor.isPresent()) {
      Doctor doctor = existingDoctor.get();
      doctorMapper.updateEntity(doctor, doctorDTO);
      Doctor updatedDoctor = doctorRepository.save(doctor);
      return Optional.of(doctorMapper.toDTO(updatedDoctor));
    }
    
    return Optional.empty();
  }
  
  // Eliminar doctor
  public boolean eliminarDoctor(String id) {
    if (doctorRepository.existsById(id)) {
      doctorRepository.deleteById(id);
      return true;
    }
    return false;
  }
  
  // Buscar por especialidad
  public List<DoctorDTO> buscarPorEspecialidad(String specialty) {
    List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
    return doctorMapper.toDTOList(doctors);
  }
  
  // Buscar por hospital
  public List<DoctorDTO> buscarPorHospital(String hospital) {
    List<Doctor> doctors = doctorRepository.findByHospital(hospital);
    return doctorMapper.toDTOList(doctors);
  }
  
  // Buscar por disponibilidad
  public List<DoctorDTO> buscarPorDisponibilidad(boolean available) {
    List<Doctor> doctors = doctorRepository.findByAvailable(available);
    return doctorMapper.toDTOList(doctors);
  }
  
  // Buscar por nombre
  public List<DoctorDTO> buscarPorNombre(String name) {
    List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCase(name);
    return doctorMapper.toDTOList(doctors);
  }
  
  // Buscar con filtros múltiples
  public List<DoctorDTO> buscarConFiltros(String specialty, String hospital, Boolean available, String name) {
    List<Doctor> doctors = doctorRepository.findByFilters(specialty, hospital, available, name);
    return doctorMapper.toDTOList(doctors);
  }
  
  // Verificar si existe doctor por ID
  public boolean existeDoctor(String id) {
    return doctorRepository.existsById(id);
  }
  
  // Contar total de doctores
  public long contarDoctores() {
    return doctorRepository.count();
  }
  
  // Obtener doctores disponibles
  public List<DoctorDTO> obtenerDoctoresDisponibles() {
    List<Doctor> doctors = doctorRepository.findByAvailable(true);
    return doctorMapper.toDTOList(doctors);
  }
}