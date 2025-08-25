package com.hn.tgu.hospital.repository;

import com.hn.tgu.hospital.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {
    Optional<Paciente> findByEmail(String email);
    boolean existsByEmail(String email);
}
