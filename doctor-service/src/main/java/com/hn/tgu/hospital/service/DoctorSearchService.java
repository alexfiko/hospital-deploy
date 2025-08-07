package com.hn.tgu.hospital.service;

import com.hn.tgu.hospital.dto.DoctorDTO;
import com.hn.tgu.hospital.entity.Doctor;
import com.hn.tgu.hospital.mapper.DoctorMapper;
import com.hn.tgu.hospital.repository.DoctorRepository;
import com.hn.tgu.hospital.search.DoctorIndex;
import com.hn.tgu.hospital.search.DoctorSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorSearchService {

    @Autowired
    private DoctorSearchRepository doctorSearchRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    /**
     * Búsqueda avanzada con facets usando Elasticsearch
     */
    public List<DoctorDTO> buscarConFacets(String query, String specialty, String hospital, 
                                          Integer minExperience, Integer maxExperience, 
                                          Double minRating, Double maxRating, 
                                          Boolean available, List<String> tags) {
        try {
            // Por ahora usamos JPA como fallback hasta implementar la consulta completa
            return buscarConJPA(query, specialty, hospital, minExperience, maxExperience, minRating, maxRating, available, tags);
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda Elasticsearch: " + e.getMessage());
            // Fallback a JPA
            return buscarConJPA(query, specialty, hospital, minExperience, maxExperience, minRating, maxRating, available, tags);
        }
    }

    /**
     * Búsqueda con sugerencias usando Elasticsearch
     */
    public List<DoctorDTO> buscarConSugerencias(String query) {
        try {
            // Por ahora usamos JPA como fallback hasta implementar la consulta completa
            return buscarConJPA(query, null, null, null, null, null, null, null, null);
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda con sugerencias: " + e.getMessage());
            return buscarConJPA(query, null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Obtener sugerencias de autocompletado
     */
    public List<String> obtenerSugerencias(String prefix) {
        try {
            List<DoctorIndex> doctors = doctorSearchRepository.findByNameStartingWithIgnoreCase(prefix);
            return doctors.stream()
                    .map(DoctorIndex::getName)
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error obteniendo sugerencias: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Búsqueda por especialidad
     */
    public List<DoctorDTO> buscarPorEspecialidad(String specialty) {
        try {
            List<DoctorIndex> doctors = doctorSearchRepository.findBySpecialty(specialty);
            return doctors.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por especialidad: " + e.getMessage());
            return buscarConJPA(null, specialty, null, null, null, null, null, null, null);
        }
    }

    /**
     * Búsqueda por hospital
     */
    public List<DoctorDTO> buscarPorHospital(String hospital) {
        try {
            List<DoctorIndex> doctors = doctorSearchRepository.findByHospital(hospital);
            return doctors.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por hospital: " + e.getMessage());
            return buscarConJPA(null, null, hospital, null, null, null, null, null, null);
        }
    }

    /**
     * Búsqueda por disponibilidad
     */
    public List<DoctorDTO> buscarPorDisponibilidad(boolean available) {
        try {
            List<DoctorIndex> doctors = doctorSearchRepository.findByAvailable(available);
            return doctors.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por disponibilidad: " + e.getMessage());
            return buscarConJPA(null, null, null, null, null, null, null, available, null);
        }
    }

    /**
     * Búsqueda por rango de experiencia
     */
    public List<DoctorDTO> buscarPorExperiencia(int minYears, int maxYears) {
        try {
            // Por ahora usamos JPA como fallback hasta implementar la consulta de rango
            return buscarConJPA(null, null, null, minYears, maxYears, null, null, null, null);
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por experiencia: " + e.getMessage());
            return buscarConJPA(null, null, null, minYears, maxYears, null, null, null, null);
        }
    }

    /**
     * Búsqueda por rango de rating
     */
    public List<DoctorDTO> buscarPorRating(double minRating, double maxRating) {
        try {
            // Por ahora usamos JPA como fallback hasta implementar la consulta de rango
            return buscarConJPA(null, null, null, null, null, minRating, maxRating, null, null);
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por rating: " + e.getMessage());
            return buscarConJPA(null, null, null, null, null, minRating, maxRating, null, null);
        }
    }

    /**
     * Búsqueda por tags
     */
    public List<DoctorDTO> buscarPorTags(List<String> tags) {
        try {
            List<DoctorIndex> doctors = doctorSearchRepository.findByTagsIn(tags);
            return doctors.stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por tags: " + e.getMessage());
            return buscarConJPA(null, null, null, null, null, null, null, null, tags);
        }
    }

    /**
     * Sincronizar datos de JPA a Elasticsearch
     */
    public void sincronizarDatos() {
        try {
            List<Doctor> doctors = doctorRepository.findAll();
            
            List<DoctorIndex> doctorIndices = doctors.stream()
                    .map(this::convertToDoctorIndex)
                    .collect(Collectors.toList());
            
            doctorSearchRepository.saveAll(doctorIndices);
            System.out.println("✅ " + doctors.size() + " doctores sincronizados en Elasticsearch");
            
        } catch (Exception e) {
            System.err.println("❌ Error sincronizando datos: " + e.getMessage());
            throw e;
        }
    }

    // Métodos de fallback usando JPA
    private List<DoctorDTO> buscarConJPA(String query, String specialty, String hospital, 
                                        Integer minExperience, Integer maxExperience, 
                                        Double minRating, Double maxRating, 
                                        Boolean available, List<String> tags) {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    private DoctorDTO convertToDoctorDTO(DoctorIndex doctorIndex) {
        DoctorDTO dto = new DoctorDTO();
        dto.id = doctorIndex.getId();
        dto.name = doctorIndex.getName();
        dto.specialty = doctorIndex.getSpecialty();
        dto.hospital = doctorIndex.getHospital();
        dto.description = doctorIndex.getDescription();
        dto.experienceYears = doctorIndex.getExperienceYears();
        dto.rating = doctorIndex.getRating();
        dto.available = doctorIndex.isAvailable();
        dto.tags = doctorIndex.getTags();
        dto.diasLaborales = doctorIndex.getDiasLaborales();
        return dto;
    }

    private DoctorIndex convertToDoctorIndex(Doctor doctor) {
        DoctorIndex index = new DoctorIndex();
        index.setId(doctor.getId());
        index.setName(doctor.getName());
        index.setSpecialty(doctor.getSpecialty());
        index.setHospital(doctor.getHospital());
        index.setDescription(doctor.getDescription());
        index.setExperienceYears(doctor.getExperienceYears());
        index.setRating(doctor.getRating());
        index.setAvailable(doctor.isAvailable());
        index.setTags(doctor.getTags());
        index.setDiasLaborales(doctor.getDiasLaborales());
        index.setSearchText(doctor.getName() + " " + doctor.getSpecialty() + " " + doctor.getDescription());
        return index;
    }
}
