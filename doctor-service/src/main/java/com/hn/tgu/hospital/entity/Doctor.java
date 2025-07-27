package com.hn.tgu.hospital.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "doctors")
public class Doctor {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  
  @Column(nullable = false)
  private String name;
  
  @Column(nullable = false)
  private String specialty;
  
  @Column(nullable = false)
  private String img;
  
  @Column(name = "experience_years")
  private int experienceYears;
  
  @Column(nullable = false)
  private double rating;
  
  @Column(nullable = false)
  private String hospital;
  
  @Column(nullable = false)
  private boolean available;
  
  @Column(columnDefinition = "TEXT")
  private String description;
  
  @ElementCollection
  @CollectionTable(name = "doctor_tags", joinColumns = @JoinColumn(name = "doctor_id"))
  @Column(name = "tag")
  private List<String> tags;
  
  // Horario de trabajo
  @ElementCollection
  @CollectionTable(name = "doctor_dias_laborales", joinColumns = @JoinColumn(name = "doctor_id"))
  @Column(name = "dia")
  private List<String> diasLaborales;
  
  @Column(name = "horario_entrada")
  private String horarioEntrada;
  
  @Column(name = "horario_salida")
  private String horarioSalida;
  
  @Column(name = "duracion_cita")
  private int duracionCita;
  
  // Horarios disponibles por día
  @ElementCollection
  @CollectionTable(name = "doctor_horarios_disponibles", 
                   joinColumns = @JoinColumn(name = "doctor_id"))
  @MapKeyColumn(name = "dia")
  @Column(name = "horarios", columnDefinition = "TEXT")
  private Map<String, List<String>> horariosDisponibles;
  
  // Constructores
  public Doctor() {}
  
  public Doctor(String name, String specialty, String img, int experienceYears, 
                double rating, String hospital, boolean available, String description, 
                List<String> tags, List<String> diasLaborales, String horarioEntrada, 
                String horarioSalida, int duracionCita, Map<String, List<String>> horariosDisponibles) {
    this.name = name;
    this.specialty = specialty;
    this.img = img;
    this.experienceYears = experienceYears;
    this.rating = rating;
    this.hospital = hospital;
    this.available = available;
    this.description = description;
    this.tags = tags;
    this.diasLaborales = diasLaborales;
    this.horarioEntrada = horarioEntrada;
    this.horarioSalida = horarioSalida;
    this.duracionCita = duracionCita;
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
  
  public List<String> getDiasLaborales() {
    return diasLaborales;
  }
  
  public void setDiasLaborales(List<String> diasLaborales) {
    this.diasLaborales = diasLaborales;
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
  
  public Map<String, List<String>> getHorariosDisponibles() {
    return horariosDisponibles;
  }
  
  public void setHorariosDisponibles(Map<String, List<String>> horariosDisponibles) {
    this.horariosDisponibles = horariosDisponibles;
  }
} 