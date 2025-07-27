package com.hn.tgu.hospital.dto;

import java.util.List;
import java.util.Map;

public class DoctorDTO {
  public String id;
  public String name;
  public String specialty;
  public String img;
  public int experienceYears;
  public double rating;
  public String hospital;
  public boolean available;
  public String description;
  public List<String> tags;
  
  // Nueva estructura de horarios
  public HorarioTrabajoDTO horarioTrabajo;
  public Map<String, List<String>> horariosDisponibles;
  
  // Constructor por defecto
  public DoctorDTO() {}
  
  // Constructor con todos los campos
  public DoctorDTO(String id, String name, String specialty, String img, 
                   int experienceYears, double rating, String hospital, 
                   boolean available, String description, List<String> tags,
                   HorarioTrabajoDTO horarioTrabajo, Map<String, List<String>> horariosDisponibles) {
    this.id = id;
    this.name = name;
    this.specialty = specialty;
    this.img = img;
    this.experienceYears = experienceYears;
    this.rating = rating;
    this.hospital = hospital;
    this.available = available;
    this.description = description;
    this.tags = tags;
    this.horarioTrabajo = horarioTrabajo;
    this.horariosDisponibles = horariosDisponibles;
  }
}