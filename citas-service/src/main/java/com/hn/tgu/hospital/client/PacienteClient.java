package com.hn.tgu.hospital.client;

import com.hn.tgu.hospital.dto.PacienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paciente-service", url = "http://paciente-service:8085")
public interface PacienteClient {
    @PostMapping("/patients/create")
    PacienteDTO crearPaciente(@RequestBody PacienteDTO pacienteDTO);
}
