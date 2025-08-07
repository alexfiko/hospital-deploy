package com.hn.tgu.hospital.service;

import com.hn.tgu.hospital.client.DoctorClient;
import com.hn.tgu.hospital.dto.DoctorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorIntegrationService {

    @Autowired
    private DoctorClient doctorClient;

    /**
     * Obtiene todos los doctores disponibles
     */
    public List<DoctorDTO> getAllDoctors() {
        try {
            return doctorClient.getAllDoctors();
        } catch (Exception e) {
            System.err.println("❌ Error obteniendo doctores: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Obtiene un doctor por su ID
     */
    public DoctorDTO getDoctorById(String doctorId) {
        try {
            return doctorClient.getDoctorById(doctorId);
        } catch (Exception e) {
            System.err.println("❌ Error obteniendo doctor con ID " + doctorId + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene doctores por especialidad
     */
    public List<DoctorDTO> getDoctorsBySpecialty(String specialty) {
        try {
            return doctorClient.getDoctorsBySpecialty(specialty);
        } catch (Exception e) {
            System.err.println("❌ Error obteniendo doctores por especialidad " + specialty + ": " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Verifica si el doctor service está disponible
     */
    public boolean isDoctorServiceAvailable() {
        try {
            String health = doctorClient.getHealth();
            return health != null && !health.contains("DOWN");
        } catch (Exception e) {
            System.err.println("❌ Doctor service no disponible: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida si un doctor existe y está disponible
     */
    public boolean validateDoctorForAppointment(String doctorId) {
        try {
            DoctorDTO doctor = doctorClient.getDoctorById(doctorId);
            return doctor != null && doctor.isAvailable();
        } catch (Exception e) {
            System.err.println("❌ Error validando doctor para cita: " + e.getMessage());
            return false;
        }
    }
}
