package com.hn.tgu.hospital.controller;

import com.hn.tgu.hospital.dto.DoctorDTO;
import com.hn.tgu.hospital.entity.Doctor;
import com.hn.tgu.hospital.mapper.DoctorMapper;
import com.hn.tgu.hospital.repository.DoctorRepository;
import com.hn.tgu.hospital.service.DoctorSearchService;
import com.hn.tgu.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

  @Autowired
  private DoctorService doctorService;

  @Autowired
  private DoctorSearchService doctorSearchService;

  @Autowired
  private DoctorRepository doctorRepository;

  @Autowired
  private DoctorMapper doctorMapper;

  // GET - Obtener todos los doctores
  @GetMapping("/list")
  @Transactional(readOnly = true)
  public ResponseEntity<List<DoctorDTO>> getDoctores() {
    List<Doctor> doctores = doctorService.obtenerTodos();
    List<DoctorDTO> doctorDTOs = doctores.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(doctorDTOs);
  }

  // GET - Obtener doctor por ID
  @GetMapping("/{id}")
  public ResponseEntity<DoctorDTO> getDoctorPorId(@PathVariable String id) {
    Doctor doctor = doctorService.obtenerPorId(id);
    if (doctor != null) {
      return ResponseEntity.ok(doctorMapper.toDTO(doctor));
    }
    return ResponseEntity.notFound().build();
  }

  // POST - Crear nuevo doctor
  @PostMapping
  public ResponseEntity<DoctorDTO> crearDoctor(@RequestBody DoctorDTO doctorDTO) {
    Doctor doctor = doctorMapper.toEntity(doctorDTO);
    Doctor doctorCreado = doctorService.crear(doctor);
    return ResponseEntity.status(HttpStatus.CREATED).body(doctorMapper.toDTO(doctorCreado));
  }

  // PUT - Actualizar doctor
  @PutMapping("/{id}")
  public ResponseEntity<DoctorDTO> actualizarDoctor(@PathVariable String id, 
                                                   @RequestBody DoctorDTO doctorDTO) {
    Doctor doctor = doctorMapper.toEntity(doctorDTO);
    doctor.setId(id);
    Doctor doctorActualizado = doctorService.actualizar(doctor);
    return ResponseEntity.ok(doctorMapper.toDTO(doctorActualizado));
  }

  // DELETE - Eliminar doctor
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarDoctor(@PathVariable String id) {
    doctorService.eliminar(id);
    return ResponseEntity.noContent().build();
  }

  // GET - Búsqueda por especialidad (JPA)
  @GetMapping("/specialty/{specialty}")
  public ResponseEntity<List<DoctorDTO>> buscarPorEspecialidad(@PathVariable String specialty) {
    List<Doctor> doctores = doctorService.buscarPorEspecialidad(specialty);
    List<DoctorDTO> doctorDTOs = doctores.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(doctorDTOs);
  }

  // GET - Búsqueda por hospital (JPA)
  @GetMapping("/hospital/{hospital}")
  public ResponseEntity<List<DoctorDTO>> buscarPorHospital(@PathVariable String hospital) {
    List<Doctor> doctores = doctorService.buscarPorHospital(hospital);
    List<DoctorDTO> doctorDTOs = doctores.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(doctorDTOs);
  }

  // GET - Búsqueda por disponibilidad (JPA)
  @GetMapping("/available/{available}")
  public ResponseEntity<List<DoctorDTO>> buscarPorDisponibilidad(@PathVariable boolean available) {
    List<Doctor> doctores = doctorService.buscarPorDisponibilidad(available);
    List<DoctorDTO> doctorDTOs = doctores.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(doctorDTOs);
  }

  // GET - Búsqueda por nombre (JPA)
  @GetMapping("/search")
  public ResponseEntity<List<DoctorDTO>> buscarPorNombre(@RequestParam String name) {
    List<Doctor> doctores = doctorService.buscarPorNombre(name);
    List<DoctorDTO> doctorDTOs = doctores.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(doctorDTOs);
  }

  // GET - Búsqueda con filtros (JPA)
  @GetMapping("/filter")
  public ResponseEntity<List<DoctorDTO>> buscarConFiltros(
      @RequestParam(required = false) String specialty,
      @RequestParam(required = false) String hospital,
      @RequestParam(required = false) Boolean available,
      @RequestParam(required = false) String name) {
    List<Doctor> doctores = doctorService.buscarConFiltros(specialty, hospital, available, name);
    List<DoctorDTO> doctorDTOs = doctores.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(doctorDTOs);
  }

  // GET - Verificar si existe doctor
  @GetMapping("/exists/{id}")
  public ResponseEntity<Boolean> existeDoctor(@PathVariable String id) {
    boolean existe = doctorService.existe(id);
    return ResponseEntity.ok(existe);
  }

  // GET - Contar doctores
  @GetMapping("/count")
  public ResponseEntity<Long> contarDoctores() {
    long count = doctorService.contar();
    return ResponseEntity.ok(count);
  }

  // GET - Obtener doctores disponibles
  @GetMapping("/available")
  public ResponseEntity<List<DoctorDTO>> obtenerDoctoresDisponibles() {
    List<Doctor> doctores = doctorService.obtenerDisponibles();
    List<DoctorDTO> doctorDTOs = doctores.stream()
        .map(doctorMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(doctorDTOs);
  }

  // GET - Búsqueda avanzada con Elasticsearch
  @GetMapping("/search/advanced")
  public ResponseEntity<List<DoctorDTO>> buscarAvanzado(
      @RequestParam(required = false) String query,
      @RequestParam(required = false) String specialty,
      @RequestParam(required = false) String hospital,
      @RequestParam(required = false) Boolean available,
      @RequestParam(required = false) Double minRating,
      @RequestParam(required = false) Double maxRating,
      @RequestParam(required = false) Integer minExperience,
      @RequestParam(required = false) Integer maxExperience,
      @RequestParam(required = false) List<String> tags) {
    
    System.out.println("🔍 [DoctorController] /search/advanced llamado con:");
    System.out.println("  - Query: " + query);
    System.out.println("  - Specialty: " + specialty);
    System.out.println("  - Hospital: " + hospital);
    System.out.println("  - Available: " + available);
    System.out.println("  - MinRating: " + minRating);
    System.out.println("  - MaxRating: " + maxRating);
    System.out.println("  - MinExperience: " + minExperience);
    System.out.println("  - MaxExperience: " + maxExperience);
    System.out.println("  - Tags: " + tags);
    
    // Si solo tenemos query (del frontend), usar el nuevo método
    if (query != null && !query.trim().isEmpty() && 
        specialty == null && hospital == null && available == null && 
        minRating == null && maxRating == null && minExperience == null && 
        maxExperience == null && (tags == null || tags.isEmpty())) {
      
      System.out.println("🔍 [DoctorController] Usando procesarQueryFrontend para query: " + query);
      List<DoctorDTO> resultado = doctorSearchService.procesarQueryFrontend(query);
      System.out.println("🔍 [DoctorController] Resultados devueltos: " + resultado.size());
      return ResponseEntity.ok(resultado);
    }
    
    // Si tenemos parámetros individuales, usar el método original
    System.out.println("🔍 [DoctorController] Usando buscarConFacets con parámetros individuales");
    System.out.println("🔍 [DoctorController] Parámetros recibidos:");
    System.out.println("  - Query: " + query);
    System.out.println("  - Specialty: " + specialty);
    System.out.println("  - Hospital: " + hospital);
    System.out.println("  - Available: " + available);
    System.out.println("  - MinRating: " + minRating);
    System.out.println("  - MaxRating: " + maxRating);
    System.out.println("  - MinExperience: " + minExperience);
    System.out.println("  - MaxExperience: " + maxExperience);
    System.out.println("  - Tags: " + tags);
    
    List<DoctorDTO> resultado = doctorSearchService.buscarConFacets(query, specialty, hospital, 
        minExperience, maxExperience, minRating, maxRating, available, tags);
    
    System.out.println("🔍 [DoctorController] Resultados devueltos: " + resultado.size());
    System.out.println("🔍 [DoctorController] Primeros 3 resultados:");
    resultado.stream().limit(3).forEach(doc -> 
        System.out.println("  - " + doc.name + " (" + doc.hospital + ") - " + doc.specialty)
    );
    
    return ResponseEntity.ok(resultado);
  }

  // GET - Búsqueda con sugerencias
  @GetMapping("/search/suggestions")
  public ResponseEntity<List<DoctorDTO>> buscarConSugerencias(@RequestParam String query) {
    List<DoctorDTO> resultado = doctorSearchService.buscarConSugerencias(query);
    return ResponseEntity.ok(resultado);
  }

  // GET - Autocompletado
  @GetMapping("/search/autocomplete")
  public ResponseEntity<List<String>> obtenerSugerencias(@RequestParam String prefix) {
    List<String> sugerencias = doctorSearchService.obtenerSugerencias(prefix);
    return ResponseEntity.ok(sugerencias);
  }

  // GET - Búsqueda por especialidad (Elasticsearch)
  @GetMapping("/search/specialty/{specialty}")
  public ResponseEntity<List<DoctorDTO>> buscarPorEspecialidadIndexado(@PathVariable String specialty) {
    List<DoctorDTO> doctores = doctorSearchService.buscarPorEspecialidad(specialty);
    return ResponseEntity.ok(doctores);
  }

  // GET - Búsqueda por hospital (Elasticsearch)
  @GetMapping("/search/hospital/{hospital}")
  public ResponseEntity<List<DoctorDTO>> buscarPorHospitalIndexado(@PathVariable String hospital) {
    List<DoctorDTO> doctores = doctorSearchService.buscarPorHospital(hospital);
    return ResponseEntity.ok(doctores);
  }

  // GET - Búsqueda por disponibilidad (Elasticsearch)
  @GetMapping("/search/available/{available}")
  public ResponseEntity<List<DoctorDTO>> buscarPorDisponibilidadIndexado(@PathVariable boolean available) {
    List<DoctorDTO> doctores = doctorSearchService.buscarPorDisponibilidad(available);
    return ResponseEntity.ok(doctores);
  }

  // GET - Búsqueda por rango de experiencia
  @GetMapping("/search/experience")
  public ResponseEntity<List<DoctorDTO>> buscarPorExperiencia(
      @RequestParam int minYears, 
      @RequestParam int maxYears) {
    List<DoctorDTO> doctores = doctorSearchService.buscarPorExperiencia(minYears, maxYears);
    return ResponseEntity.ok(doctores);
  }

  // GET - Búsqueda por rango de rating
  @GetMapping("/search/rating")
  public ResponseEntity<List<DoctorDTO>> buscarPorRating(
      @RequestParam double minRating, 
      @RequestParam double maxRating) {
    List<DoctorDTO> doctores = doctorSearchService.buscarPorRating(minRating, maxRating);
    return ResponseEntity.ok(doctores);
  }

  // GET - Búsqueda por tags
  @GetMapping("/search/tags")
  public ResponseEntity<List<DoctorDTO>> buscarPorTags(@RequestParam List<String> tags) {
    List<DoctorDTO> doctores = doctorSearchService.buscarPorTags(tags);
    return ResponseEntity.ok(doctores);
  }

  // POST - Sincronizar datos con Elasticsearch
  @PostMapping("/sync/elasticsearch")
  public ResponseEntity<String> sincronizarConElasticsearch() {
    try {
      doctorSearchService.sincronizarDatos();
      return ResponseEntity.ok("Datos sincronizados exitosamente con Elasticsearch");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                         .body("Error al sincronizar datos: " + e.getMessage());
    }
  }

  // GET - Sincronizar datos con Elasticsearch (alternativa)
  @GetMapping("/sync/elasticsearch")
  public ResponseEntity<String> sincronizarConElasticsearchGet() {
    try {
      doctorSearchService.sincronizarDatos();
      return ResponseEntity.ok("Datos sincronizados exitosamente con Elasticsearch");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                         .body("Error al sincronizar datos: " + e.getMessage());
    }
  }

  // GET - Verificar estado de Elasticsearch
  @GetMapping("/search-index")
  public ResponseEntity<List<DoctorDTO>> verificarElasticsearch() {
    try {
      List<DoctorDTO> doctores = doctorSearchService.buscarPorEspecialidad("Cardiología");
      return ResponseEntity.ok(doctores);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                         .body(List.of());
    }
  }

  // GET - Búsqueda por nombre en Elasticsearch
  @GetMapping("/search-index/name")
  public ResponseEntity<List<DoctorDTO>> buscarPorNombreIndexado(@RequestParam String name) {
    List<String> sugerencias = doctorSearchService.obtenerSugerencias(name);
    List<DoctorDTO> doctores = sugerencias.stream()
        .map(sugerencia -> {
          DoctorDTO dto = new DoctorDTO();
          dto.setName(sugerencia);
          return dto;
        })
        .collect(Collectors.toList());
    return ResponseEntity.ok(doctores);
  }

    @GetMapping("/hospitales")
    @Transactional(readOnly = true)
    public ResponseEntity<List<String>> getHospitales() {
        try {
            List<String> hospitales = doctorRepository.findDistinctHospitales();
            return ResponseEntity.ok(hospitales);
        } catch (Exception e) {
            // Assuming logger is available, otherwise replace with System.err.println
            // System.err.println("Error obteniendo hospitales: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
}