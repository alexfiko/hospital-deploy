package com.hn.tgu.hospital.controller;

import com.hn.tgu.hospital.dto.DoctorDTO;
import com.hn.tgu.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {
 
  @Autowired
  private DoctorService doctorService;
 
  // GET - Obtener todos los doctores
  @GetMapping("/list")
  public ResponseEntity<List<DoctorDTO>> getDoctores() {
    List<DoctorDTO> doctors = doctorService.obtenerDoctores();
    return ResponseEntity.ok(doctors);
  }
  
  // GET - Obtener doctor por ID
  @GetMapping("/{id}")
  public ResponseEntity<DoctorDTO> getDoctorPorId(@PathVariable String id) {
    Optional<DoctorDTO> doctor = doctorService.obtenerDoctorPorId(id);
    return doctor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
  }
  
  // POST - Crear nuevo doctor
  @PostMapping
  public ResponseEntity<DoctorDTO> crearDoctor(@RequestBody DoctorDTO doctorDTO) {
    DoctorDTO nuevoDoctor = doctorService.crearDoctor(doctorDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDoctor);
  }
  
  // PUT - Actualizar doctor
  @PutMapping("/{id}")
  public ResponseEntity<DoctorDTO> actualizarDoctor(@PathVariable String id, 
                                                   @RequestBody DoctorDTO doctorDTO) {
    Optional<DoctorDTO> doctorActualizado = doctorService.actualizarDoctor(id, doctorDTO);
    return doctorActualizado.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
  }
  
  // DELETE - Eliminar doctor
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarDoctor(@PathVariable String id) {
    boolean eliminado = doctorService.eliminarDoctor(id);
    if (eliminado) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  
  // GET - Buscar por especialidad
  @GetMapping("/specialty/{specialty}")
  public ResponseEntity<List<DoctorDTO>> buscarPorEspecialidad(@PathVariable String specialty) {
    List<DoctorDTO> doctors = doctorService.buscarPorEspecialidad(specialty);
    return ResponseEntity.ok(doctors);
  }
  
  // GET - Buscar por hospital
  @GetMapping("/hospital/{hospital}")
  public ResponseEntity<List<DoctorDTO>> buscarPorHospital(@PathVariable String hospital) {
    List<DoctorDTO> doctors = doctorService.buscarPorHospital(hospital);
    return ResponseEntity.ok(doctors);
  }
  
  // GET - Buscar por disponibilidad
  @GetMapping("/available/{available}")
  public ResponseEntity<List<DoctorDTO>> buscarPorDisponibilidad(@PathVariable boolean available) {
    List<DoctorDTO> doctors = doctorService.buscarPorDisponibilidad(available);
    return ResponseEntity.ok(doctors);
  }
  
  // GET - Buscar por nombre
  @GetMapping("/search")
  public ResponseEntity<List<DoctorDTO>> buscarPorNombre(@RequestParam String name) {
    List<DoctorDTO> doctors = doctorService.buscarPorNombre(name);
    return ResponseEntity.ok(doctors);
  }
  
  // GET - Buscar con filtros múltiples
  @GetMapping("/filter")
  public ResponseEntity<List<DoctorDTO>> buscarConFiltros(
      @RequestParam(required = false) String specialty,
      @RequestParam(required = false) String hospital,
      @RequestParam(required = false) Boolean available,
      @RequestParam(required = false) String name) {
    
    List<DoctorDTO> doctors = doctorService.buscarConFiltros(specialty, hospital, available, name);
    return ResponseEntity.ok(doctors);
  }
  
  // GET - Verificar si existe doctor
  @GetMapping("/exists/{id}")
  public ResponseEntity<Boolean> existeDoctor(@PathVariable String id) {
    boolean existe = doctorService.existeDoctor(id);
    return ResponseEntity.ok(existe);
  }
  
  // GET - Contar total de doctores
  @GetMapping("/count")
  public ResponseEntity<Long> contarDoctores() {
    long total = doctorService.contarDoctores();
    return ResponseEntity.ok(total);
  }
  
  // GET - Obtener doctores disponibles
  @GetMapping("/available")
  public ResponseEntity<List<DoctorDTO>> obtenerDoctoresDisponibles() {
    List<DoctorDTO> doctors = doctorService.obtenerDoctoresDisponibles();
    return ResponseEntity.ok(doctors);
  }
}