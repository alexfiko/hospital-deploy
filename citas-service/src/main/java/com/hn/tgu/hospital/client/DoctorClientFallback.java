package com.hn.tgu.hospital.client;

import com.hn.tgu.hospital.dto.DoctorDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorClientFallback implements DoctorClient {

    @Override
    public List<DoctorDTO> getAllDoctors() {
        System.err.println("❌ Fallback: No se pudo obtener la lista de doctores");
        return new ArrayList<>();
    }

    @Override
    public DoctorDTO getDoctorById(String id) {
        System.err.println("❌ Fallback: No se pudo obtener el doctor con ID: " + id);
        return null;
    }

    @Override
    public List<DoctorDTO> getDoctorsBySpecialty(String specialty) {
        System.err.println("❌ Fallback: No se pudo obtener doctores por especialidad: " + specialty);
        return new ArrayList<>();
    }

    @Override
    public String getHealth() {
        return "DOWN";
    }
}
