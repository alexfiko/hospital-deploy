package com.hn.tgu.hospital.client;

import com.hn.tgu.hospital.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "doctor-service", fallback = DoctorClientFallback.class)
public interface DoctorClient {

    @GetMapping("/doctors")
    List<DoctorDTO> getAllDoctors();

    @GetMapping("/doctors/{id}")
    DoctorDTO getDoctorById(@PathVariable("id") String id);

    @GetMapping("/doctors/search/specialty/{specialty}")
    List<DoctorDTO> getDoctorsBySpecialty(@PathVariable("specialty") String specialty);

    @GetMapping("/actuator/health")
    String getHealth();
}
