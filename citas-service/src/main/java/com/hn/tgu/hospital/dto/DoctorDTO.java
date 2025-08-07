package com.hn.tgu.hospital.dto;

import java.util.List;

public class DoctorDTO {
    private String id;
    private String name;
    private String specialty;
    private String img;
    private int experienceYears;
    private double rating;
    private String hospital;
    private boolean available;
    private String description;
    private String horarioEntrada;
    private String horarioSalida;
    private int duracionCita;
    private List<String> tags;
    private List<String> diasLaborales;

    // Constructor por defecto
    public DoctorDTO() {
    }

    // Constructor con parámetros
    public DoctorDTO(String id, String name, String specialty, String img, int experienceYears, 
                    double rating, String hospital, boolean available, String description) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.img = img;
        this.experienceYears = experienceYears;
        this.rating = rating;
        this.hospital = hospital;
        this.available = available;
        this.description = description;
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
}
