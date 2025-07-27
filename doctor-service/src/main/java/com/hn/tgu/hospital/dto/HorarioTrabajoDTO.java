package com.hn.tgu.hospital.dto;

import java.util.List;

public class HorarioTrabajoDTO {
  public List<String> diasLaborales;
  public String horarioEntrada;
  public String horarioSalida;
  public int duracionCita;
  
  // Constructor por defecto
  public HorarioTrabajoDTO() {}
  
  // Constructor con todos los campos
  public HorarioTrabajoDTO(List<String> diasLaborales, String horarioEntrada, 
                           String horarioSalida, int duracionCita) {
    this.diasLaborales = diasLaborales;
    this.horarioEntrada = horarioEntrada;
    this.horarioSalida = horarioSalida;
    this.duracionCita = duracionCita;
  }
} 