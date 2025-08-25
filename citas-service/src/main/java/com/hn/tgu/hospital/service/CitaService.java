package com.hn.tgu.hospital.service;

import com.hn.tgu.hospital.dto.CitaDTO;
import com.hn.tgu.hospital.entity.Cita;
import com.hn.tgu.hospital.mapper.CitaMapper;
import com.hn.tgu.hospital.repository.CitaRepository;
import com.hn.tgu.hospital.repository.PacienteRepository;
import com.hn.tgu.hospital.entity.Paciente;
import com.hn.tgu.hospital.dto.PacienteDTO;
import com.hn.tgu.hospital.client.PacienteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteClient pacienteClient;

    public List<CitaDTO> listarCitas() {
        return citaRepository.findAll().stream()
            .map(CitaMapper::toDTO)
            .collect(Collectors.toList());
    }

    public CitaDTO crearCita(CitaDTO citaDTO) {
        // Si viene pacienteId directamente del frontend, usarlo
        if (citaDTO.getPacienteId() != null) {
            // El paciente ya existe, solo crear la cita
            Cita cita = CitaMapper.toEntity(citaDTO);
            cita.setId(UUID.randomUUID().toString());
            return CitaMapper.toDTO(citaRepository.save(cita));
        }
        
        // Si viene un objeto paciente completo, crearlo primero
        PacienteDTO pacienteDTO = citaDTO.getPaciente();
        if (pacienteDTO != null) {
            PacienteDTO pacienteCreado = pacienteClient.crearPaciente(pacienteDTO);
            citaDTO.setPacienteId(pacienteCreado.getId());
            citaDTO.setPacienteNombre(pacienteCreado.getNombre() + " " + pacienteCreado.getApellido());
        }
        
        Cita cita = CitaMapper.toEntity(citaDTO);
        cita.setId(UUID.randomUUID().toString());
        return CitaMapper.toDTO(citaRepository.save(cita));
    }

    public CitaDTO actualizarCita(String id, CitaDTO citaDTO) {
        Cita cita = citaRepository.findById(id).orElseThrow();
        cita.setFecha(citaDTO.getFecha());
        cita.setHora(citaDTO.getHora());
        cita.setMotivo(citaDTO.getMotivo());
        cita.setEstado(citaDTO.getEstado());
        cita.setNotas(citaDTO.getNotas());
        cita.setDoctorId(citaDTO.getDoctorId());
        cita.setPacienteId(citaDTO.getPacienteId());
        cita.setDoctorNombre(citaDTO.getDoctorNombre());
        cita.setPacienteNombre(citaDTO.getPacienteNombre());
        return CitaMapper.toDTO(citaRepository.save(cita));
    }

    public void eliminarCita(String id) {
        citaRepository.deleteById(id);
    }

    public List<CitaDTO> buscarPorDoctor(String doctorId) {
        return citaRepository.findAll().stream()
            .filter(c -> doctorId.equals(c.getDoctorId()))
            .map(CitaMapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<CitaDTO> buscarPorPaciente(String pacienteId) {
        return citaRepository.findAll().stream()
            .filter(c -> pacienteId.equals(c.getPacienteId()))
            .map(CitaMapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<CitaDTO> buscarPorFecha(String fecha) {
        return citaRepository.findAll().stream()
            .filter(c -> c.getFecha() != null && c.getFecha().toString().equals(fecha))
            .map(CitaMapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<CitaDTO> buscarPorEstado(String estado) {
        return citaRepository.findAll().stream()
            .filter(c -> estado.equalsIgnoreCase(c.getEstado()))
            .map(CitaMapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<CitaDTO> reporteCitas(String doctorId, String pacienteId, String fecha, String estado) {
        return citaRepository.findAll().stream()
            .filter(c -> doctorId == null || doctorId.equals(c.getDoctorId()))
            .filter(c -> pacienteId == null || pacienteId.equals(c.getPacienteId()))
            .filter(c -> fecha == null || (c.getFecha() != null && c.getFecha().toString().equals(fecha)))
            .filter(c -> estado == null || estado.equalsIgnoreCase(c.getEstado()))
            .map(CitaMapper::toDTO)
            .collect(Collectors.toList());
    }
}