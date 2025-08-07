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
  
  // Campos adicionales para Elasticsearch
  public String horarioEntrada;
  public String horarioSalida;
  public int duracionCita;
  public List<String> diasLaborales;
  
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

  // Getters y Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSpecialty() {
    return specialty;
  }

  public void setSpecialty(String specialty) {
    this.specialty = specialty;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public int getExperienceYears() {
    return experienceYears;
  }

  public void setExperienceYears(int experienceYears) {
    this.experienceYears = experienceYears;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public String getHospital() {
    return hospital;
  }

  public void setHospital(String hospital) {
    this.hospital = hospital;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public HorarioTrabajoDTO getHorarioTrabajo() {
    return horarioTrabajo;
  }

  public void setHorarioTrabajo(HorarioTrabajoDTO horarioTrabajo) {
    this.horarioTrabajo = horarioTrabajo;
  }

  public Map<String, List<String>> getHorariosDisponibles() {
    return horariosDisponibles;
  }

  public void setHorariosDisponibles(Map<String, List<String>> horariosDisponibles) {
    this.horariosDisponibles = horariosDisponibles;
  }

  public String getHorarioEntrada() {
    return horarioEntrada;
  }

  public void setHorarioEntrada(String horarioEntrada) {
    this.horarioEntrada = horarioEntrada;
  }

  public String getHorarioSalida() {
    return horarioSalida;
  }

  public void setHorarioSalida(String horarioSalida) {
    this.horarioSalida = horarioSalida;
  }

  public int getDuracionCita() {
    return duracionCita;
  }

  public void setDuracionCita(int duracionCita) {
    this.duracionCita = duracionCita;
  }

  public List<String> getDiasLaborales() {
    return diasLaborales;
  }

  public void setDiasLaborales(List<String> diasLaborales) {
    this.diasLaborales = diasLaborales;
  }
}