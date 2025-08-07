package com.hn.tgu.hospital.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CitaDTO {
  
  private String id;
  private String doctorId;
  private String pacienteId;
  private LocalDate fecha;
  private String hora;
  private String estado;
  private String motivo;
  private String notas;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
  // Información adicional del doctor y paciente
  private String doctorNombre;
  private String pacienteNombre;
  
  // Campo para PacienteDTO
  private PacienteDTO paciente;
  
  // Constructores
  public CitaDTO() {}
  
  // Constructor simplificado para CitaService
  public CitaDTO(String id, String doctorId, String especialidad, String fecha, String hora, 
                 PacienteDTO paciente, String motivo) {
    this.id = id;
    this.doctorId = doctorId;
    this.fecha = LocalDate.parse(fecha);
    this.hora = hora;
    this.paciente = paciente;
    this.motivo = motivo;
    this.estado = "PROGRAMADA";
  }
  
  public CitaDTO(String id, String doctorId, String pacienteId, LocalDate fecha, String hora, 
                 String estado, String motivo, String notas, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.doctorId = doctorId;
    this.pacienteId = pacienteId;
    this.fecha = fecha;
    this.hora = hora;
    this.estado = estado;
    this.motivo = motivo;
    this.notas = notas;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
  
  // Getters y Setters
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getDoctorId() {
    return doctorId;
  }
  
  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }
  
  public String getPacienteId() {
    return pacienteId;
  }
  
  public void setPacienteId(String pacienteId) {
    this.pacienteId = pacienteId;
  }
  
  public LocalDate getFecha() {
    return fecha;
  }
  
  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }
  
  public String getHora() {
    return hora;
  }
  
  public void setHora(String hora) {
    this.hora = hora;
  }
  
  public String getEstado() {
    return estado;
  }
  
  public void setEstado(String estado) {
    this.estado = estado;
  }
  
  public String getMotivo() {
    return motivo;
  }
  
  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }
  
  public String getNotas() {
    return notas;
  }
  
  public void setNotas(String notas) {
    this.notas = notas;
  }
  
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
  
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
  
  public String getDoctorNombre() {
    return doctorNombre;
  }
  
  public void setDoctorNombre(String doctorNombre) {
    this.doctorNombre = doctorNombre;
  }
  
  public String getPacienteNombre() {
    return pacienteNombre;
  }
  
  public void setPacienteNombre(String pacienteNombre) {
    this.pacienteNombre = pacienteNombre;
  }
  
  public PacienteDTO getPaciente() {
    return paciente;
  }
  
  public void setPaciente(PacienteDTO paciente) {
    this.paciente = paciente;
  }
}