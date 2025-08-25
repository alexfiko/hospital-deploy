package com.hn.tgu.hospital.controller;

import com.hn.tgu.hospital.dto.CitaDTO;
import com.hn.tgu.hospital.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class CitaController {

    @Autowired
    private CitaService citaService;

    // CRUD principal
    @GetMapping("/list")
    public ResponseEntity<List<CitaDTO>> getCitas() {
        return ResponseEntity.ok(citaService.listarCitas());
    }

    @PostMapping("/create")
    public ResponseEntity<CitaDTO> crearCita(@RequestBody CitaDTO citaDTO) {
        CitaDTO citaCreada = citaService.crearCita(citaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(citaCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizarCita(@PathVariable String id, @RequestBody CitaDTO citaDTO) {
        CitaDTO citaActualizada = citaService.actualizarCita(id, citaDTO);
        return ResponseEntity.ok(citaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable String id) {
        citaService.eliminarCita(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS DE BÚSQUEDA ÚTILES ---

    // Buscar citas por doctorId
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<CitaDTO>> getCitasPorDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(citaService.buscarPorDoctor(doctorId));
    }

    // Buscar citas por pacienteId
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaDTO>> getCitasPorPaciente(@PathVariable String pacienteId) {
        return ResponseEntity.ok(citaService.buscarPorPaciente(pacienteId));
    }

    // Buscar citas por fecha
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaDTO>> getCitasPorFecha(@PathVariable String fecha) {
        return ResponseEntity.ok(citaService.buscarPorFecha(fecha));
    }

    // Buscar citas por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaDTO>> getCitasPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(citaService.buscarPorEstado(estado));
    }

    // Endpoint de reporte flexible
    @GetMapping("/report")
    public ResponseEntity<List<CitaDTO>> getReporteCitas(
            @RequestParam(required = false) String doctorId,
            @RequestParam(required = false) String pacienteId,
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) String estado
    ) {
        return ResponseEntity.ok(
            citaService.reporteCitas(doctorId, pacienteId, fecha, estado)
        );
    }
}