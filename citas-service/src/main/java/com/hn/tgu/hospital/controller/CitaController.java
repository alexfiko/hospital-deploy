package com.hn.tgu.hospital.controller;

import com.hn.tgu.hospital.dto.CitaDTO;
import com.hn.tgu.hospital.service.CitaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping("/list")
    public List<CitaDTO> obtenerCitas(@RequestParam(required = false) String hospital) {
        if ("tgu".equalsIgnoreCase(hospital)) {
            return citaService.listarCitas();
        }
        return List.of(); // Pensado para escalar a otras sedes
    }

}