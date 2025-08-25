package com.hn.tgu.hospital.mapper;

import com.hn.tgu.hospital.dto.CitaDTO;
import com.hn.tgu.hospital.dto.PacienteDTO;
import com.hn.tgu.hospital.entity.Cita;

public class CitaMapper {
    public static CitaDTO toDTO(Cita cita) {
        if (cita == null) return null;
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());
        dto.setDoctorId(cita.getDoctorId());
        dto.setPacienteId(cita.getPacienteId());
        dto.setFecha(cita.getFecha());
        dto.setHora(cita.getHora());
        dto.setEstado(cita.getEstado());
        dto.setMotivo(cita.getMotivo());
        dto.setNotas(cita.getNotas());
        dto.setCreatedAt(cita.getCreatedAt());
        dto.setUpdatedAt(cita.getUpdatedAt());
        dto.setDoctorNombre(cita.getDoctorNombre());
        dto.setPacienteNombre(cita.getPacienteNombre());
        if (cita.getPacienteNombre() != null || cita.getPacienteId() != null) {
            dto.setPaciente(new PacienteDTO(cita.getPacienteNombre(), ""));
        }
        return dto;
    }

    public static Cita toEntity(CitaDTO dto) {
        if (dto == null) return null;
        Cita cita = new Cita();
        cita.setId(dto.getId());
        cita.setDoctorId(dto.getDoctorId());
        cita.setPacienteId(dto.getPacienteId());
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setEstado(dto.getEstado());
        cita.setMotivo(dto.getMotivo());
        cita.setNotas(dto.getNotas());
        cita.setCreatedAt(dto.getCreatedAt());
        cita.setUpdatedAt(dto.getUpdatedAt());
        cita.setDoctorNombre(dto.getDoctorNombre());
        cita.setPacienteNombre(dto.getPacienteNombre());
        return cita;
    }
}
