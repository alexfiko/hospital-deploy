-- Script DDL para crear la tabla de doctores
-- V1__Create_doctors_table.sql

CREATE TABLE IF NOT EXISTS doctors (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    img VARCHAR(500) NOT NULL,
    experience_years INT NOT NULL,
    rating DOUBLE PRECISION NOT NULL,
    hospital VARCHAR(255) NOT NULL,
    available BOOLEAN NOT NULL DEFAULT true,
    description TEXT,
    horario_entrada VARCHAR(5),
    horario_salida VARCHAR(5),
    duracion_cita INT DEFAULT 30,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla para los tags de los doctores
CREATE TABLE IF NOT EXISTS doctor_tags (
    doctor_id VARCHAR(36) NOT NULL,
    tag VARCHAR(100) NOT NULL,
    PRIMARY KEY (doctor_id, tag),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);

-- Tabla para los días laborales
CREATE TABLE IF NOT EXISTS doctor_dias_laborales (
    doctor_id VARCHAR(36) NOT NULL,
    dia VARCHAR(20) NOT NULL,
    PRIMARY KEY (doctor_id, dia),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);

-- Tabla para los horarios disponibles por día
CREATE TABLE IF NOT EXISTS doctor_horarios_disponibles (
    doctor_id VARCHAR(36) NOT NULL,
    dia VARCHAR(20) NOT NULL,
    horarios TEXT,
    PRIMARY KEY (doctor_id, dia),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_doctors_specialty ON doctors(specialty);
CREATE INDEX idx_doctors_available ON doctors(available);
CREATE INDEX idx_doctors_hospital ON doctors(hospital);
CREATE INDEX idx_doctors_rating ON doctors(rating); 