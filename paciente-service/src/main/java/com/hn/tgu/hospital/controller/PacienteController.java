package com.hn.tgu.hospital.controller;

import com.hn.tgu.hospital.dto.PacienteDTO;
import com.hn.tgu.hospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/list") 
    public ResponseEntity<List<PacienteDTO>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> obtenerPorId(@PathVariable String id) {
        PacienteDTO dto = pacienteService.obtenerPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PacienteDTO> obtenerPorEmail(@RequestParam String email) {
        PacienteDTO dto = pacienteService.obtenerPorEmail(email);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<PacienteDTO> crearPaciente(@RequestBody PacienteDTO dto) {
        return ResponseEntity.ok(pacienteService.crearPaciente(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizarPaciente(@PathVariable String id, @RequestBody PacienteDTO dto) {
        PacienteDTO actualizado = pacienteService.actualizarPaciente(id, dto);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable String id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
