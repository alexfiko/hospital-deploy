package com.hn.tgu.hospital.dto;

import java.time.LocalDateTime;

public class EspecialidadDTO {
  
  private String id;
  private String nombre;
  private String descripcion;
  private String imagen;
  private boolean activa;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
  // Constructores
  public EspecialidadDTO() {}
  
  public EspecialidadDTO(String id, String nombre, String descripcion, String imagen, boolean activa, 
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.imagen = imagen;
    this.activa = activa;
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
}