package com.hn.tgu.hospital.service;

import com.hn.tgu.hospital.dto.DoctorDTO;
import com.hn.tgu.hospital.entity.Doctor;
import com.hn.tgu.hospital.mapper.DoctorMapper;
import com.hn.tgu.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

  @Autowired
  private DoctorRepository doctorRepository;
  
  @Autowired
  private DoctorMapper doctorMapper;

  // Obtener todos los doctores
  public List<Doctor> obtenerTodos() {
    return doctorRepository.findAll();
  }

  // Obtener doctor por ID
  public Doctor obtenerPorId(String id) {
    return doctorRepository.findById(id).orElse(null);
  }

  // Crear nuevo doctor
  public Doctor crear(Doctor doctor) {
    return doctorRepository.save(doctor);
  }

  // Actualizar doctor
  public Doctor actualizar(Doctor doctor) {
    return doctorRepository.save(doctor);
  }

  // Eliminar doctor
  public void eliminar(String id) {
    doctorRepository.deleteById(id);
  }

  // Buscar por especialidad
  public List<Doctor> buscarPorEspecialidad(String specialty) {
    return doctorRepository.findBySpecialty(specialty);
  }

  // Buscar por hospital
  public List<Doctor> buscarPorHospital(String hospital) {
    return doctorRepository.findByHospital(hospital);
  }

  // Buscar por disponibilidad
  public List<Doctor> buscarPorDisponibilidad(boolean available) {
    return doctorRepository.findByAvailable(available);
  }

  // Buscar por nombre
  public List<Doctor> buscarPorNombre(String name) {
    return doctorRepository.findByNameContainingIgnoreCase(name);
  }

  // Buscar con filtros
  public List<Doctor> buscarConFiltros(String specialty, String hospital, Boolean available, String name) {
    return doctorRepository.findByFilters(specialty, hospital, available, name);
  }

  // Verificar si existe
  public boolean existe(String id) {
    return doctorRepository.existsById(id);
  }

  // Contar doctores
  public long contar() {
    return doctorRepository.count();
  }

  // Obtener doctores disponibles
  public List<Doctor> obtenerDisponibles() {
    return doctorRepository.findByAvailable(true);
  }

  // Métodos legacy para compatibilidad
  public List<DoctorDTO> getAllDoctors() {
    List<Doctor> doctors = doctorRepository.findAll();
    return doctors.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<DoctorDTO> getDoctorById(String id) {
    return doctorRepository.findById(id)
        .map(doctorMapper::toDTO);
  }

  public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
    Doctor doctor = doctorMapper.toEntity(doctorDTO);
    Doctor savedDoctor = doctorRepository.save(doctor);
    return doctorMapper.toDTO(savedDoctor);
  }

  public Optional<DoctorDTO> updateDoctor(String id, DoctorDTO doctorDTO) {
    return doctorRepository.findById(id)
        .map(existingDoctor -> {
          doctorDTO.id = id;
          Doctor doctor = doctorMapper.toEntity(doctorDTO);
          Doctor savedDoctor = doctorRepository.save(doctor);
          return doctorMapper.toDTO(savedDoctor);
        });
  }

  public boolean deleteDoctor(String id) {
    if (doctorRepository.existsById(id)) {
      doctorRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public List<DoctorDTO> findBySpecialty(String specialty) {
    List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
    return doctors.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<DoctorDTO> findByHospital(String hospital) {
    List<Doctor> doctors = doctorRepository.findByHospital(hospital);
    return doctors.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<DoctorDTO> findByAvailable(boolean available) {
    List<Doctor> doctors = doctorRepository.findByAvailable(available);
    return doctors.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<DoctorDTO> findByName(String name) {
    List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCase(name);
    return doctors.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<DoctorDTO> findByFilters(String specialty, String hospital, Boolean available, String name) {
    List<Doctor> doctors = doctorRepository.findByFilters(specialty, hospital, available, name);
    return doctors.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
  }

  public boolean existsById(String id) {
    return doctorRepository.existsById(id);
  }

  public long count() {
    return doctorRepository.count();
  }

  public List<DoctorDTO> getAvailableDoctors() {
    List<Doctor> doctors = doctorRepository.findByAvailable(true);
    return doctors.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
  }
}