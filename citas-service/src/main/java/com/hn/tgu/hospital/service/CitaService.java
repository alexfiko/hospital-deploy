package com.hn.tgu.hospital.service;

import com.hn.tgu.hospital.dto.CitaDTO;
import com.hn.tgu.hospital.dto.PacienteDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaService {

    public List<CitaDTO> listarCitas() {
        return List.of(
            new CitaDTO(
                "2b9740b5-ef0f-470b-8164-5982018f38a2",
                "f1b19bc7-8b4d-4857-8a38-1a0185588e9c",
                "cardio",
                "2025-07-01",
                "16:00",
                new PacienteDTO("Maria", "paciente7@correo.com"),
                "Consulta general"
            ),
            new CitaDTO(
                "3c1829a1-fb7a-4a53-a4d1-a8c32e08b4db",
                "a4c91d82-1d54-4d3c-9d22-ff0b1c2a14d5",
                "pedia",
                "2025-07-02",
                "09:30",
                new PacienteDTO("Carlos", "paciente8@correo.com"),
                "Chequeo postvacuna"
            ),
            new CitaDTO(
                "5d837c44-1e6e-4f99-baa7-11b670d991e1",
                "b6ee73ad-f9dd-4b1e-82ff-7762b4c3d2a8",
                "derma",
                "2025-07-03",
                "13:45",
                new PacienteDTO("Lucía", "paciente9@correo.com"),
                "Revisión de alergia"
            )
        );
    }
}