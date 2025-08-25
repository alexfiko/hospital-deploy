package com.hn.tgu.hospital.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
public class Cita {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  
  @Column(name = "doctor_id", nullable = false)
  private String doctorId;
  
  @Column(name = "paciente_id", nullable = false)
  private String pacienteId;
  
  @Column(nullable = false)
  private LocalDate fecha;
  
  @Column(nullable = false)
  private String hora;
  
  @Column(nullable = false)
  private String estado = "PENDIENTE";
  
  @Column(columnDefinition = "TEXT")
  private String motivo;
  
  @Column(columnDefinition = "TEXT")
  private String notas;
  
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  
  private String doctorNombre;
  private String pacienteNombre;
  
  // Constructores
  public Cita() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
  
  public Cita(String doctorId, String pacienteId, LocalDate fecha, String hora, 
              String motivo, String notas) {
    this.doctorId = doctorId;
    this.pacienteId = pacienteId;
    this.fecha = fecha;
    this.hora = hora;
    this.motivo = motivo;
    this.notas = notas;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
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
  
  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
} 