package com.hn.tgu.hospital.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "especialidades")
public class Especialidad {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  
  @Column(nullable = false, unique = true)
  private String nombre;
  
  @Column(columnDefinition = "TEXT")
  private String descripcion;
  
  @Column
  private String imagen;
  
  @Column(nullable = false)
  private boolean activa = true;
  
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  
  // Constructores
  public Especialidad() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
  
  public Especialidad(String nombre, String descripcion, String imagen, boolean activa) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.imagen = imagen;
    this.activa = activa;
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
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public String getImagen() {
    return imagen;
  }
  
  public void setImagen(String imagen) {
    this.imagen = imagen;
  }
  
  public boolean isActiva() {
    return activa;
  }
  
  public void setActiva(boolean activa) {
    this.activa = activa;
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
  
  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
} 