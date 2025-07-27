package com.hn.tgu.hospital.repository;

import com.hn.tgu.hospital.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, String> {
  
  /**
   * Buscar citas por doctor
   */
  List<Cita> findByDoctorId(String doctorId);
  
  /**
   * Buscar citas por paciente
   */
  List<Cita> findByPacienteId(String pacienteId);
  
  /**
   * Buscar citas por fecha
   */
  List<Cita> findByFecha(LocalDate fecha);
  
  /**
   * Buscar citas por estado
   */
  List<Cita> findByEstado(String estado);
  
  /**
   * Buscar citas por doctor y fecha
   */
  List<Cita> findByDoctorIdAndFecha(String doctorId, LocalDate fecha);
  
  /**
   * Buscar citas por doctor, fecha y estado
   */
  List<Cita> findByDoctorIdAndFechaAndEstado(String doctorId, LocalDate fecha, String estado);
  
  /**
   * Buscar citas por paciente y estado
   */
  List<Cita> findByPacienteIdAndEstado(String pacienteId, String estado);
  
  /**
   * Verificar si existe cita para doctor, fecha y hora
   */
  boolean existsByDoctorIdAndFechaAndHora(String doctorId, LocalDate fecha, String hora);
  
  /**
   * Contar citas por doctor y fecha
   */
  long countByDoctorIdAndFecha(String doctorId, LocalDate fecha);
  
  /**
   * Contar citas por estado
   */
  long countByEstado(String estado);
  
  /**
   * Buscar citas por rango de fechas
   */
  @Query("SELECT c FROM Cita c WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin")
  List<Cita> findByFechaBetween(@Param("fechaInicio") LocalDate fechaInicio, 
                                @Param("fechaFin") LocalDate fechaFin);
  
  /**
   * Buscar citas por doctor y rango de fechas
   */
  @Query("SELECT c FROM Cita c WHERE c.doctorId = :doctorId AND c.fecha BETWEEN :fechaInicio AND :fechaFin")
  List<Cita> findByDoctorIdAndFechaBetween(@Param("doctorId") String doctorId,
                                          @Param("fechaInicio") LocalDate fechaInicio,
                                          @Param("fechaFin") LocalDate fechaFin);
  
  /**
   * Buscar citas pendientes por doctor
   */
  @Query("SELECT c FROM Cita c WHERE c.doctorId = :doctorId AND c.estado = 'PENDIENTE' ORDER BY c.fecha, c.hora")
  List<Cita> findPendientesByDoctorId(@Param("doctorId") String doctorId);
  
  /**
   * Buscar citas confirmadas por doctor
   */
  @Query("SELECT c FROM Cita c WHERE c.doctorId = :doctorId AND c.estado = 'CONFIRMADA' ORDER BY c.fecha, c.hora")
  List<Cita> findConfirmadasByDoctorId(@Param("doctorId") String doctorId);
  
  /**
   * Buscar citas canceladas por doctor
   */
  @Query("SELECT c FROM Cita c WHERE c.doctorId = :doctorId AND c.estado = 'CANCELADA' ORDER BY c.fecha, c.hora")
  List<Cita> findCanceladasByDoctorId(@Param("doctorId") String doctorId);
  
  /**
   * Buscar citas por paciente y rango de fechas
   */
  @Query("SELECT c FROM Cita c WHERE c.pacienteId = :pacienteId AND c.fecha BETWEEN :fechaInicio AND :fechaFin")
  List<Cita> findByPacienteIdAndFechaBetween(@Param("pacienteId") String pacienteId,
                                            @Param("fechaInicio") LocalDate fechaInicio,
                                            @Param("fechaFin") LocalDate fechaFin);
} 