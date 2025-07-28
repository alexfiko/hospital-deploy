package com.hn.tgu.hospital.controller;

import com.hn.tgu.hospital.dto.EspecialidadDTO;
import com.hn.tgu.hospital.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@FeignClient(name = "especialidad-service", url = "${especialidad.service.url:http://localhost:8080}")
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "*")
public class EspecialidadController {
  
  @Autowired
  private EspecialidadService especialidadService;
  
  /**
   * GET /api/especialidades - Obtener todas las especialidades
   */
  @GetMapping
  public ResponseEntity<List<EspecialidadDTO>> getAllEspecialidades() {
    List<EspecialidadDTO> especialidades = especialidadService.getAllEspecialidades();
    return ResponseEntity.ok(especialidades);
  }
  
  /**
   * GET /api/especialidades/activas - Obtener especialidades activas
   */
  @GetMapping("/activas")
  public ResponseEntity<List<EspecialidadDTO>> getEspecialidadesActivas() {
    List<EspecialidadDTO> especialidades = especialidadService.getEspecialidadesActivas();
    return ResponseEntity.ok(especialidades);
  }
  
  /**
   * GET /api/especialidades/{id} - Obtener especialidad por ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<EspecialidadDTO> getEspecialidadById(@PathVariable String id) {
    Optional<EspecialidadDTO> especialidad = especialidadService.getEspecialidadById(id);
    return especialidad.map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }
  
  /**
   * GET /api/especialidades/nombre/{nombre} - Obtener especialidad por nombre
   */
  @GetMapping("/nombre/{nombre}")
  public ResponseEntity<EspecialidadDTO> getEspecialidadByNombre(@PathVariable String nombre) {
    Optional<EspecialidadDTO> especialidad = especialidadService.getEspecialidadByNombre(nombre);
    return especialidad.map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }
  
  /**
   * GET /api/especialidades/buscar?nombre={nombre} - Buscar especialidades por nombre
   */
  @GetMapping("/buscar")
  public ResponseEntity<List<EspecialidadDTO>> buscarPorNombre(@RequestParam String nombre) {
    List<EspecialidadDTO> especialidades = especialidadService.buscarPorNombre(nombre);
    return ResponseEntity.ok(especialidades);
  }
  
  /**
   * POST /api/especialidades - Crear nueva especialidad
   */
  @PostMapping
  public ResponseEntity<EspecialidadDTO> createEspecialidad(@RequestBody EspecialidadDTO especialidadDTO) {
    try {
      EspecialidadDTO createdEspecialidad = especialidadService.createEspecialidad(especialidadDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdEspecialidad);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }
  
  /**
   * PUT /api/especialidades/{id} - Actualizar especialidad
   */
  @PutMapping("/{id}")
  public ResponseEntity<EspecialidadDTO> updateEspecialidad(@PathVariable String id, 
                                                           @RequestBody EspecialidadDTO especialidadDTO) {
    try {
      Optional<EspecialidadDTO> updatedEspecialidad = especialidadService.updateEspecialidad(id, especialidadDTO);
      return updatedEspecialidad.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }
  
  /**
   * DELETE /api/especialidades/{id} - Eliminar especialidad
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEspecialidad(@PathVariable String id) {
    boolean deleted = especialidadService.deleteEspecialidad(id);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
  
  /**
   * PATCH /api/especialidades/{id}/toggle - Activar/Desactivar especialidad
   */
  @PatchMapping("/{id}/toggle")
  public ResponseEntity<EspecialidadDTO> toggleEspecialidad(@PathVariable String id) {
    Optional<EspecialidadDTO> especialidad = especialidadService.toggleEspecialidad(id);
    return especialidad.map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }
  
  /**
   * GET /api/especialidades/count/activas - Contar especialidades activas
   */
  @GetMapping("/count/activas")
  public ResponseEntity<Long> countEspecialidadesActivas() {
    long count = especialidadService.countEspecialidadesActivas();
    return ResponseEntity.ok(count);
  }
  
  /**
   * GET /api/especialidades/exists/{id} - Verificar si existe especialidad por ID
   */
  @GetMapping("/exists/{id}")
  public ResponseEntity<Boolean> existsById(@PathVariable String id) {
    boolean exists = especialidadService.existsById(id);
    return ResponseEntity.ok(exists);
  }
  
  /**
   * GET /api/especialidades/exists/nombre/{nombre} - Verificar si existe especialidad por nombre
   */
  @GetMapping("/exists/nombre/{nombre}")
  public ResponseEntity<Boolean> existsByNombre(@PathVariable String nombre) {
    boolean exists = especialidadService.existsByNombre(nombre);
    return ResponseEntity.ok(exists);
  }
  
  /**
   * GET /api/especialidades/health - Health check
   */
  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Especialidad Service is running!");
  }
}