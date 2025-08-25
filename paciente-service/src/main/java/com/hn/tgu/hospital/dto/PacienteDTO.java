package com.hn.tgu.hospital.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDTO {
    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String genero;
    private String direccion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PacienteDTO() {}
    // Getters y setters
    // ... (puedes autogenerarlos con tu IDE o Lombok)
}
