package com.hn.tgu.hospital.controller;

import com.hn.tgu.hospital.dto.CitaDTO;
import com.hn.tgu.hospital.dto.DoctorDTO;
import com.hn.tgu.hospital.service.CitaService;
import com.hn.tgu.hospital.service.DoctorIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class CitaController {

    private final CitaService citaService;
    private final DoctorIntegrationService doctorIntegrationService;

    public CitaController(CitaService citaService, DoctorIntegrationService doctorIntegrationService) {
        this.citaService = citaService;
        this.doctorIntegrationService = doctorIntegrationService;
    }

    @GetMapping("/list")
    public List<CitaDTO> obtenerCitas(@RequestParam(required = false) String hospital) {
        if ("tgu".equalsIgnoreCase(hospital)) {
            return citaService.listarCitas();
        }
        return List.of(); // Pensado para escalar a otras sedes
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> obtenerDoctores() {
        List<DoctorDTO> doctores = doctorIntegrationService.getAllDoctors();
        return ResponseEntity.ok(doctores);
    }

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<DoctorDTO> obtenerDoctor(@PathVariable String doctorId) {
        DoctorDTO doctor = doctorIntegrationService.getDoctorById(doctorId);
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/doctors/specialty/{specialty}")
    public ResponseEntity<List<DoctorDTO>> obtenerDoctoresPorEspecialidad(@PathVariable String specialty) {
        List<DoctorDTO> doctores = doctorIntegrationService.getDoctorsBySpecialty(specialty);
        return ResponseEntity.ok(doctores);
    }

    @GetMapping("/doctors/available")
    public ResponseEntity<List<DoctorDTO>> obtenerDoctoresDisponibles() {
        List<DoctorDTO> doctores = doctorIntegrationService.getAllDoctors();
        List<DoctorDTO> disponibles = doctores.stream()
                .filter(DoctorDTO::isAvailable)
                .toList();
        return ResponseEntity.ok(disponibles);
    }

    @GetMapping("/health/doctor-service")
    public ResponseEntity<String> verificarDoctorService() {
        boolean disponible = doctorIntegrationService.isDoctorServiceAvailable();
        if (disponible) {
            return ResponseEntity.ok("Doctor Service está disponible");
        } else {
            return ResponseEntity.status(503).body("Doctor Service no está disponible");
        }
    }

    @GetMapping("/validate-doctor/{doctorId}")
    public ResponseEntity<String> validarDoctorParaCita(@PathVariable String doctorId) {
        boolean valido = doctorIntegrationService.validateDoctorForAppointment(doctorId);
        if (valido) {
            return ResponseEntity.ok("Doctor válido para cita");
        } else {
            return ResponseEntity.badRequest().body("Doctor no válido o no disponible");
        }
    }
}