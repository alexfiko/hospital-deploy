package com.hn.tgu.hospital.mapper;

import com.hn.tgu.hospital.dto.PacienteDTO;
import com.hn.tgu.hospital.entity.Paciente;

public class PacienteMapper {
    public static PacienteDTO toDTO(Paciente paciente) {
        if (paciente == null) return null;
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setEmail(paciente.getEmail());
        dto.setTelefono(paciente.getTelefono());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setGenero(paciente.getGenero());
        dto.setDireccion(paciente.getDireccion());
        dto.setCreatedAt(paciente.getCreatedAt());
        dto.setUpdatedAt(paciente.getUpdatedAt());
        return dto;
    }
    public static Paciente toEntity(PacienteDTO dto) {
        if (dto == null) return null;
        Paciente paciente = new Paciente();
        paciente.setId(dto.getId());
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setEmail(dto.getEmail());
        paciente.setTelefono(dto.getTelefono());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setGenero(dto.getGenero());
        paciente.setDireccion(dto.getDireccion());
        paciente.setCreatedAt(dto.getCreatedAt());
        paciente.setUpdatedAt(dto.getUpdatedAt());
        return paciente;
    }
}
