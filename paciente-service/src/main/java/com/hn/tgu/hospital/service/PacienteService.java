package com.hn.tgu.hospital.service;

import com.hn.tgu.hospital.dto.PacienteDTO;
import com.hn.tgu.hospital.entity.Paciente;
import com.hn.tgu.hospital.mapper.PacienteMapper;
import com.hn.tgu.hospital.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<PacienteDTO> listarPacientes() {
        return pacienteRepository.findAll().stream()
                .map(PacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PacienteDTO obtenerPorId(String id) {
        return pacienteRepository.findById(id)
                .map(PacienteMapper::toDTO)
                .orElse(null);
    }

    public PacienteDTO obtenerPorEmail(String email) {
        return pacienteRepository.findByEmail(email)
                .map(PacienteMapper::toDTO)
                .orElse(null);
    }

    public PacienteDTO crearPaciente(PacienteDTO dto) {
        // Solo buscar por id si viene un valor
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            Optional<Paciente> pacienteOpt = pacienteRepository.findById(dto.getId());
            if (pacienteOpt.isPresent()) {
                return PacienteMapper.toDTO(pacienteOpt.get());
            }
        }
        // Buscar por email
        Optional<Paciente> pacienteEmailOpt = pacienteRepository.findByEmail(dto.getEmail());
        if (pacienteEmailOpt.isPresent()) {
            // Si existe por email, actualiza el registro pero NO cambies el id
            Paciente paciente = pacienteEmailOpt.get();
            // NO actualizar el id aquí
            paciente.setNombre(dto.getNombre());
            paciente.setApellido(dto.getApellido());
            paciente.setTelefono(dto.getTelefono());
            paciente.setFechaNacimiento(dto.getFechaNacimiento());
            paciente.setGenero(dto.getGenero());
            paciente.setDireccion(dto.getDireccion());
            paciente.setUpdatedAt(dto.getUpdatedAt());
            return PacienteMapper.toDTO(pacienteRepository.save(paciente));
        }
        // Si no existe por ninguno, crea uno nuevo
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(java.util.UUID.randomUUID().toString());
        }
        Paciente paciente = PacienteMapper.toEntity(dto);
        return PacienteMapper.toDTO(pacienteRepository.save(paciente));
    }

    public PacienteDTO actualizarPaciente(String id, PacienteDTO dto) {
        Optional<Paciente> op = pacienteRepository.findById(id);
        if (op.isEmpty()) return null;
        Paciente paciente = op.get();
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setEmail(dto.getEmail());
        paciente.setTelefono(dto.getTelefono());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setGenero(dto.getGenero());
        paciente.setDireccion(dto.getDireccion());
        paciente.setUpdatedAt(dto.getUpdatedAt());
        return PacienteMapper.toDTO(pacienteRepository.save(paciente));
    }

    public void eliminarPaciente(String id) {
        pacienteRepository.deleteById(id);
    }
}
