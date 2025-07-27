-- Script de inicialización para el ecosistema hospitalario
-- Este script crea todas las tablas necesarias para todos los microservicios

-- ========================================
-- TABLAS PARA DOCTOR SERVICE
-- ========================================

-- Tabla principal de doctores
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

-- ========================================
-- TABLAS PARA CITAS SERVICE
-- ========================================

-- Tabla principal de citas
CREATE TABLE IF NOT EXISTS citas (
    id VARCHAR(36) PRIMARY KEY,
    doctor_id VARCHAR(36) NOT NULL,
    paciente_id VARCHAR(36) NOT NULL,
    fecha DATE NOT NULL,
    hora VARCHAR(5) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    motivo TEXT,
    notas TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);

-- Tabla para pacientes
CREATE TABLE IF NOT EXISTS pacientes (
    id VARCHAR(36) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    fecha_nacimiento DATE,
    genero VARCHAR(10),
    direccion TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ========================================
-- TABLAS PARA ESPECIALIDAD SERVICE
-- ========================================

-- Tabla principal de especialidades
CREATE TABLE IF NOT EXISTS especialidades (
    id VARCHAR(36) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    imagen VARCHAR(500),
    activa BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de relación doctor-especialidad
CREATE TABLE IF NOT EXISTS doctor_especialidades (
    doctor_id VARCHAR(36) NOT NULL,
    especialidad_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (doctor_id, especialidad_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE,
    FOREIGN KEY (especialidad_id) REFERENCES especialidades(id) ON DELETE CASCADE
);

-- ========================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- ========================================

-- Índices para doctors
CREATE INDEX IF NOT EXISTS idx_doctors_specialty ON doctors(specialty);
CREATE INDEX IF NOT EXISTS idx_doctors_available ON doctors(available);
CREATE INDEX IF NOT EXISTS idx_doctors_hospital ON doctors(hospital);
CREATE INDEX IF NOT EXISTS idx_doctors_rating ON doctors(rating);

-- Índices para citas
CREATE INDEX IF NOT EXISTS idx_citas_doctor_id ON citas(doctor_id);
CREATE INDEX IF NOT EXISTS idx_citas_paciente_id ON citas(paciente_id);
CREATE INDEX IF NOT EXISTS idx_citas_fecha ON citas(fecha);
CREATE INDEX IF NOT EXISTS idx_citas_estado ON citas(estado);

-- Índices para pacientes
CREATE INDEX IF NOT EXISTS idx_pacientes_email ON pacientes(email);
CREATE INDEX IF NOT EXISTS idx_pacientes_nombre ON pacientes(nombre, apellido);

-- Índices para especialidades
CREATE INDEX IF NOT EXISTS idx_especialidades_nombre ON especialidades(nombre);
CREATE INDEX IF NOT EXISTS idx_especialidades_activa ON especialidades(activa);

-- ========================================
-- DATOS DE EJEMPLO
-- ========================================

-- Insertar especialidades basadas en especialidades.json
INSERT INTO especialidades (id, nombre, descripcion, imagen, activa) VALUES
('esp-001', 'Cardiología', 'Especialista en enfermedades del corazón', 'https://example.com/cardiologia.jpg', true),
('esp-002', 'Dermatología', 'Experto en salud de la piel', 'https://example.com/dermatologia.jpg', true),
('esp-003', 'Pediatría', 'Atiende a niños y adolescentes', 'https://example.com/pediatria.jpg', true),
('esp-004', 'Neurología', 'Especialista en el sistema nervioso', 'https://example.com/neurologia.jpg', true),
('esp-005', 'Medicina General', 'Profesional médico general con alta preparación', 'https://example.com/general.jpg', true),
('esp-006', 'Ginecología', 'Especialidad médica que se ocupa de la salud del aparato reproductor femenino', 'https://example.com/ginecologia.jpg', true),
('esp-007', 'Oftalmología', 'Especialista en enfermedades de los ojos', 'https://example.com/oftalmologia.jpg', true),
('esp-008', 'Psicología', 'Especialidad que se ocupa del comportamiento y la mente humana', 'https://example.com/psicologia.jpg', true),
('esp-009', 'Endocrinología', 'Especialista en enfermedades del sistema endocrino', 'https://example.com/endocrinologia.jpg', true),
('esp-010', 'Urología', 'Especialista en enfermedades del sistema urinario', 'https://example.com/urologia.jpg', true);

-- Insertar 50 doctores de ejemplo con datos variados
INSERT INTO doctors (id, name, specialty, img, experience_years, rating, hospital, available, description, horario_entrada, horario_salida, duracion_cita) VALUES
('doc-001', 'Dra. Sofía Torres', 'Pediatría', 'https://randomuser.me/api/portraits/women/1.jpg', 15, 4.8, 'Hospital Central', true, 'Especialista en pediatría con amplia experiencia en atención infantil', '08:00', '17:00', 30),
('doc-002', 'Dr. Carlos Pineda', 'Ginecología', 'https://randomuser.me/api/portraits/men/2.jpg', 20, 4.7, 'Clínica Vida', true, 'Especialista en ginecología y obstetricia', '09:00', '18:00', 45),
('doc-003', 'Dra. Ana Ramírez', 'Cardiología', 'https://randomuser.me/api/portraits/women/3.jpg', 18, 4.9, 'Centro Médico Esperanza', true, 'Cardióloga especialista en enfermedades del corazón', '07:00', '16:00', 30),
('doc-004', 'Dr. Luis Méndez', 'Dermatología', 'https://randomuser.me/api/portraits/men/4.jpg', 12, 4.6, 'Hospital San Marcos', true, 'Dermatólogo especialista en enfermedades de la piel', '08:30', '17:30', 30),
('doc-005', 'Dra. Laura Morales', 'Neurología', 'https://randomuser.me/api/portraits/women/5.jpg', 22, 4.8, 'Clínica Neurológica', true, 'Neuróloga especialista en enfermedades del sistema nervioso', '09:00', '18:00', 45),
('doc-006', 'Dr. Javier Castillo', 'Psicología', 'https://randomuser.me/api/portraits/men/6.jpg', 10, 4.5, 'Centro Psicológico', true, 'Psicólogo especialista en terapia cognitivo-conductual', '10:00', '19:00', 60),
('doc-007', 'Dra. Camila Torres', 'Oftalmología', 'https://randomuser.me/api/portraits/women/7.jpg', 16, 4.7, 'Clínica Oftalmológica', true, 'Oftalmóloga especialista en cirugía refractiva', '08:00', '17:00', 30),
('doc-008', 'Dr. Andrés Pineda', 'Endocrinología', 'https://randomuser.me/api/portraits/men/8.jpg', 19, 4.6, 'Hospital Central', true, 'Endocrinólogo especialista en diabetes y tiroides', '09:30', '18:30', 45),
('doc-009', 'Dra. María Ramírez', 'Urología', 'https://randomuser.me/api/portraits/women/9.jpg', 14, 4.8, 'Clínica Urológica', true, 'Uróloga especialista en enfermedades del tracto urinario', '08:00', '17:00', 30),
('doc-010', 'Dr. Roberto Méndez', 'Medicina General', 'https://randomuser.me/api/portraits/men/10.jpg', 25, 4.9, 'Centro de Salud Familiar', true, 'Médico general con amplia experiencia en atención primaria', '07:00', '16:00', 20),
('doc-011', 'Dra. Sofía Morales', 'Cardiología', 'https://randomuser.me/api/portraits/women/11.jpg', 17, 4.7, 'Hospital del Corazón', true, 'Cardióloga especialista en cardiología intervencionista', '08:30', '17:30', 45),
('doc-012', 'Dr. Carlos Castillo', 'Pediatría', 'https://randomuser.me/api/portraits/men/12.jpg', 13, 4.6, 'Clínica Infantil', true, 'Pediatra especialista en neonatología', '09:00', '18:00', 30),
('doc-013', 'Dra. Ana Torres', 'Dermatología', 'https://randomuser.me/api/portraits/women/13.jpg', 15, 4.8, 'Centro Dermatológico', true, 'Dermatóloga especialista en cirugía dermatológica', '08:00', '17:00', 30),
('doc-014', 'Dr. Luis Pineda', 'Ginecología', 'https://randomuser.me/api/portraits/men/14.jpg', 21, 4.7, 'Clínica de la Mujer', true, 'Ginecólogo especialista en reproducción asistida', '09:30', '18:30', 45),
('doc-015', 'Dra. Laura Ramírez', 'Neurología', 'https://randomuser.me/api/portraits/women/15.jpg', 18, 4.9, 'Instituto Neurológico', true, 'Neuróloga especialista en epilepsia', '08:00', '17:00', 45),
('doc-016', 'Dr. Javier Morales', 'Psicología', 'https://randomuser.me/api/portraits/men/16.jpg', 11, 4.5, 'Centro de Psicología', true, 'Psicólogo especialista en terapia familiar', '10:00', '19:00', 60),
('doc-017', 'Dra. Camila Castillo', 'Oftalmología', 'https://randomuser.me/api/portraits/women/17.jpg', 14, 4.6, 'Clínica de Ojos', true, 'Oftalmóloga especialista en retina', '08:30', '17:30', 30),
('doc-018', 'Dr. Andrés Torres', 'Endocrinología', 'https://randomuser.me/api/portraits/men/18.jpg', 16, 4.8, 'Centro Endocrinológico', true, 'Endocrinólogo especialista en obesidad', '09:00', '18:00', 45),
('doc-019', 'Dra. María Pineda', 'Urología', 'https://randomuser.me/api/portraits/women/19.jpg', 13, 4.7, 'Clínica Urológica Avanzada', true, 'Uróloga especialista en urología oncológica', '08:00', '17:00', 30),
('doc-020', 'Dr. Roberto Ramírez', 'Medicina General', 'https://randomuser.me/api/portraits/men/20.jpg', 23, 4.9, 'Centro Médico Integral', true, 'Médico general especialista en medicina preventiva', '07:30', '16:30', 20);

-- Continuar con más doctores...
INSERT INTO doctors (id, name, specialty, img, experience_years, rating, hospital, available, description, horario_entrada, horario_salida, duracion_cita) VALUES
('doc-021', 'Dra. Patricia López', 'Cardiología', 'https://randomuser.me/api/portraits/women/21.jpg', 19, 4.8, 'Hospital Cardiovascular', true, 'Cardióloga especialista en arritmias', '08:00', '17:00', 45),
('doc-022', 'Dr. Fernando García', 'Pediatría', 'https://randomuser.me/api/portraits/men/22.jpg', 16, 4.7, 'Clínica Pediátrica Especializada', true, 'Pediatra especialista en cardiología pediátrica', '09:00', '18:00', 30),
('doc-023', 'Dra. Carmen Herrera', 'Dermatología', 'https://randomuser.me/api/portraits/women/23.jpg', 14, 4.6, 'Centro de Dermatología Avanzada', true, 'Dermatóloga especialista en estética dermatológica', '08:30', '17:30', 30),
('doc-024', 'Dr. Ricardo Silva', 'Ginecología', 'https://randomuser.me/api/portraits/men/24.jpg', 20, 4.8, 'Clínica Ginecológica Integral', true, 'Ginecólogo especialista en ginecología oncológica', '09:30', '18:30', 45),
('doc-025', 'Dra. Elena Vargas', 'Neurología', 'https://randomuser.me/api/portraits/women/25.jpg', 17, 4.9, 'Instituto de Neurología Avanzada', true, 'Neuróloga especialista en enfermedades neurodegenerativas', '08:00', '17:00', 45),
('doc-026', 'Dr. Manuel Cruz', 'Psicología', 'https://randomuser.me/api/portraits/men/26.jpg', 12, 4.5, 'Centro de Psicología Clínica', true, 'Psicólogo especialista en trastornos de ansiedad', '10:00', '19:00', 60),
('doc-027', 'Dra. Isabel Mendoza', 'Oftalmología', 'https://randomuser.me/api/portraits/women/27.jpg', 15, 4.7, 'Clínica Oftalmológica Especializada', true, 'Oftalmóloga especialista en glaucoma', '08:30', '17:30', 30),
('doc-028', 'Dr. Alejandro Ruiz', 'Endocrinología', 'https://randomuser.me/api/portraits/men/28.jpg', 18, 4.8, 'Centro de Endocrinología y Nutrición', true, 'Endocrinólogo especialista en diabetes tipo 1', '09:00', '18:00', 45),
('doc-029', 'Dra. Rosa Jiménez', 'Urología', 'https://randomuser.me/api/portraits/women/29.jpg', 16, 4.7, 'Clínica Urológica Especializada', true, 'Uróloga especialista en litiasis renal', '08:00', '17:00', 30),
('doc-030', 'Dr. Eduardo Moreno', 'Medicina General', 'https://randomuser.me/api/portraits/men/30.jpg', 24, 4.9, 'Centro de Medicina Familiar', true, 'Médico general especialista en geriatría', '07:30', '16:30', 20);

-- Continuar con los últimos 20 doctores...
INSERT INTO doctors (id, name, specialty, img, experience_years, rating, hospital, available, description, horario_entrada, horario_salida, duracion_cita) VALUES
('doc-031', 'Dra. Lucía Fernández', 'Cardiología', 'https://randomuser.me/api/portraits/women/31.jpg', 18, 4.8, 'Hospital del Corazón Avanzado', true, 'Cardióloga especialista en insuficiencia cardíaca', '08:00', '17:00', 45),
('doc-032', 'Dr. Diego Martínez', 'Pediatría', 'https://randomuser.me/api/portraits/men/32.jpg', 15, 4.7, 'Clínica Pediátrica Integral', true, 'Pediatra especialista en neumología pediátrica', '09:00', '18:00', 30),
('doc-033', 'Dra. Gabriela Rojas', 'Dermatología', 'https://randomuser.me/api/portraits/women/33.jpg', 13, 4.6, 'Centro de Dermatología Clínica', true, 'Dermatóloga especialista en dermatología quirúrgica', '08:30', '17:30', 30),
('doc-034', 'Dr. Hugo Salazar', 'Ginecología', 'https://randomuser.me/api/portraits/men/34.jpg', 19, 4.8, 'Clínica Ginecológica Avanzada', true, 'Ginecólogo especialista en fertilidad', '09:30', '18:30', 45),
('doc-035', 'Dra. Adriana Vega', 'Neurología', 'https://randomuser.me/api/portraits/women/35.jpg', 16, 4.9, 'Instituto de Neurología Clínica', true, 'Neuróloga especialista en esclerosis múltiple', '08:00', '17:00', 45),
('doc-036', 'Dr. Sergio Ortega', 'Psicología', 'https://randomuser.me/api/portraits/men/36.jpg', 11, 4.5, 'Centro de Psicología Integral', true, 'Psicólogo especialista en trastornos del estado de ánimo', '10:00', '19:00', 60),
('doc-037', 'Dra. Natalia Castro', 'Oftalmología', 'https://randomuser.me/api/portraits/women/37.jpg', 14, 4.7, 'Clínica Oftalmológica Integral', true, 'Oftalmóloga especialista en catarata', '08:30', '17:30', 30),
('doc-038', 'Dr. Omar Reyes', 'Endocrinología', 'https://randomuser.me/api/portraits/men/38.jpg', 17, 4.8, 'Centro de Endocrinología Clínica', true, 'Endocrinólogo especialista en tiroides', '09:00', '18:00', 45),
('doc-039', 'Dra. Valeria Soto', 'Urología', 'https://randomuser.me/api/portraits/women/39.jpg', 15, 4.7, 'Clínica Urológica Integral', true, 'Uróloga especialista en urología funcional', '08:00', '17:00', 30),
('doc-040', 'Dr. Raúl Mendoza', 'Medicina General', 'https://randomuser.me/api/portraits/men/40.jpg', 22, 4.9, 'Centro de Medicina Integral', true, 'Médico general especialista en medicina deportiva', '07:30', '16:30', 20),
('doc-041', 'Dra. Claudia Paredes', 'Cardiología', 'https://randomuser.me/api/portraits/women/41.jpg', 20, 4.8, 'Hospital Cardiovascular Especializado', true, 'Cardióloga especialista en cardiología nuclear', '08:00', '17:00', 45),
('doc-042', 'Dr. Felipe Torres', 'Pediatría', 'https://randomuser.me/api/portraits/men/42.jpg', 17, 4.7, 'Clínica Pediátrica Especializada', true, 'Pediatra especialista en gastroenterología pediátrica', '09:00', '18:00', 30),
('doc-043', 'Dra. Marcela Silva', 'Dermatología', 'https://randomuser.me/api/portraits/women/43.jpg', 14, 4.6, 'Centro de Dermatología Especializada', true, 'Dermatóloga especialista en dermatología pediátrica', '08:30', '17:30', 30),
('doc-044', 'Dr. Cristian López', 'Ginecología', 'https://randomuser.me/api/portraits/men/44.jpg', 18, 4.8, 'Clínica Ginecológica Integral', true, 'Ginecólogo especialista en ginecología endocrina', '09:30', '18:30', 45),
('doc-045', 'Dra. Daniela Herrera', 'Neurología', 'https://randomuser.me/api/portraits/women/45.jpg', 19, 4.9, 'Instituto de Neurología Especializado', true, 'Neuróloga especialista en cefaleas', '08:00', '17:00', 45),
('doc-046', 'Dr. Matías Cruz', 'Psicología', 'https://randomuser.me/api/portraits/men/46.jpg', 13, 4.5, 'Centro de Psicología Especializada', true, 'Psicólogo especialista en psicología infantil', '10:00', '19:00', 60),
('doc-047', 'Dra. Francisca Vega', 'Oftalmología', 'https://randomuser.me/api/portraits/women/47.jpg', 16, 4.7, 'Clínica Oftalmológica Especializada', true, 'Oftalmóloga especialista en oftalmología pediátrica', '08:30', '17:30', 30),
('doc-048', 'Dr. Ignacio Rojas', 'Endocrinología', 'https://randomuser.me/api/portraits/men/48.jpg', 15, 4.8, 'Centro de Endocrinología Especializada', true, 'Endocrinólogo especialista en diabetes gestacional', '09:00', '18:00', 45),
('doc-049', 'Dra. Constanza Morales', 'Urología', 'https://randomuser.me/api/portraits/women/49.jpg', 17, 4.7, 'Clínica Urológica Especializada', true, 'Uróloga especialista en urología pediátrica', '08:00', '17:00', 30),
('doc-050', 'Dr. Benjamín Castro', 'Medicina General', 'https://randomuser.me/api/portraits/men/50.jpg', 21, 4.9, 'Centro de Medicina Familiar Especializada', true, 'Médico general especialista en medicina de urgencias', '07:30', '16:30', 20);

-- Insertar tags para todos los doctores
INSERT INTO doctor_tags (doctor_id, tag) VALUES 
('doc-001', 'salud'), ('doc-001', 'consultas'), ('doc-001', 'pediatría'),
('doc-002', 'salud'), ('doc-002', 'consultas'), ('doc-002', 'ginecología'),
('doc-003', 'salud'), ('doc-003', 'consultas'), ('doc-003', 'cardiología'),
('doc-004', 'salud'), ('doc-004', 'consultas'), ('doc-004', 'dermatología'),
('doc-005', 'salud'), ('doc-005', 'consultas'), ('doc-005', 'neurología'),
('doc-006', 'salud'), ('doc-006', 'consultas'), ('doc-006', 'psicología'),
('doc-007', 'salud'), ('doc-007', 'consultas'), ('doc-007', 'oftalmología'),
('doc-008', 'salud'), ('doc-008', 'consultas'), ('doc-008', 'endocrinología'),
('doc-009', 'salud'), ('doc-009', 'consultas'), ('doc-009', 'urología'),
('doc-010', 'salud'), ('doc-010', 'consultas'), ('doc-010', 'medicina general');
('doc-011', 'salud'), ('doc-011', 'consultas'), ('doc-011', 'cardiología'),
('doc-012', 'salud'), ('doc-012', 'consultas'), ('doc-012', 'pediatría'),
('doc-013', 'salud'), ('doc-013', 'consultas'), ('doc-013', 'dermatología'),
('doc-014', 'salud'), ('doc-014', 'consultas'), ('doc-014', 'ginecología'),
('doc-015', 'salud'), ('doc-015', 'consultas'), ('doc-015', 'neurología'),
('doc-016', 'salud'), ('doc-016', 'consultas'), ('doc-016', 'psicología'),
('doc-017', 'salud'), ('doc-017', 'consultas'), ('doc-017', 'oftalmología'),
('doc-018', 'salud'), ('doc-018', 'consultas'), ('doc-018', 'endocrinología'),
('doc-019', 'salud'), ('doc-019', 'consultas'), ('doc-019', 'urología'),
('doc-020', 'salud'), ('doc-020', 'consultas'), ('doc-020', 'medicina general'),
('doc-021', 'salud'), ('doc-021', 'consultas'), ('doc-021', 'cardiología'),
('doc-022', 'salud'), ('doc-022', 'consultas'), ('doc-022', 'pediatría'),
('doc-023', 'salud'), ('doc-023', 'consultas'), ('doc-023', 'dermatología'),
('doc-024', 'salud'), ('doc-024', 'consultas'), ('doc-024', 'ginecología'),
('doc-025', 'salud'), ('doc-025', 'consultas'), ('doc-025', 'neurología'),
('doc-026', 'salud'), ('doc-026', 'consultas'), ('doc-026', 'psicología'),
('doc-027', 'salud'), ('doc-027', 'consultas'), ('doc-027', 'oftalmología'),
('doc-028', 'salud'), ('doc-028', 'consultas'), ('doc-028', 'endocrinología'),
('doc-029', 'salud'), ('doc-029', 'consultas'), ('doc-029', 'urología'),
('doc-030', 'salud'), ('doc-030', 'consultas'), ('doc-030', 'medicina general'),
('doc-031', 'salud'), ('doc-031', 'consultas'), ('doc-031', 'cardiología'),
('doc-032', 'salud'), ('doc-032', 'consultas'), ('doc-032', 'pediatría'),
('doc-033', 'salud'), ('doc-033', 'consultas'), ('doc-033', 'dermatología'),
('doc-034', 'salud'), ('doc-034', 'consultas'), ('doc-034', 'ginecología'),
('doc-035', 'salud'), ('doc-035', 'consultas'), ('doc-035', 'neurología'),
('doc-036', 'salud'), ('doc-036', 'consultas'), ('doc-036', 'psicología'),
('doc-037', 'salud'), ('doc-037', 'consultas'), ('doc-037', 'oftalmología'),
('doc-038', 'salud'), ('doc-038', 'consultas'), ('doc-038', 'endocrinología'),
('doc-039', 'salud'), ('doc-039', 'consultas'), ('doc-039', 'urología'),
('doc-040', 'salud'), ('doc-040', 'consultas'), ('doc-040', 'medicina general'),
('doc-041', 'salud'), ('doc-041', 'consultas'), ('doc-041', 'cardiología'),
('doc-042', 'salud'), ('doc-042', 'consultas'), ('doc-042', 'pediatría'),
('doc-043', 'salud'), ('doc-043', 'consultas'), ('doc-043', 'dermatología'),
('doc-044', 'salud'), ('doc-044', 'consultas'), ('doc-044', 'ginecología'),
('doc-045', 'salud'), ('doc-045', 'consultas'), ('doc-045', 'neurología'),
('doc-046', 'salud'), ('doc-046', 'consultas'), ('doc-046', 'psicología'),
('doc-047', 'salud'), ('doc-047', 'consultas'), ('doc-047', 'oftalmología'),
('doc-048', 'salud'), ('doc-048', 'consultas'), ('doc-048', 'endocrinología'),
('doc-049', 'salud'), ('doc-049', 'consultas'), ('doc-049', 'urología'),
('doc-050', 'salud'), ('doc-050', 'consultas'), ('doc-050', 'medicina general');

-- Insertar días laborales para todos los doctores (ejemplo para los primeros 10)
INSERT INTO doctor_dias_laborales (doctor_id, dia) VALUES 
INSERT INTO doctor_dias_laborales (doctor_id, dia) VALUES
-- Cardiología
('doc-003', 'lunes'), ('doc-003', 'martes'), ('doc-003', 'jueves'), ('doc-003', 'viernes'),
('doc-011', 'lunes'), ('doc-011', 'martes'), ('doc-011', 'jueves'), ('doc-011', 'viernes'),
('doc-021', 'lunes'), ('doc-021', 'martes'), ('doc-021', 'jueves'), ('doc-021', 'viernes'),
('doc-031', 'lunes'), ('doc-031', 'martes'), ('doc-031', 'jueves'), ('doc-031', 'viernes'),
('doc-041', 'lunes'), ('doc-041', 'martes'), ('doc-041', 'jueves'), ('doc-041', 'viernes'),
-- Pediatría
('doc-001', 'lunes'), ('doc-001', 'miércoles'), ('doc-001', 'viernes'),
('doc-012', 'lunes'), ('doc-012', 'miércoles'), ('doc-012', 'viernes'),
('doc-022', 'lunes'), ('doc-022', 'miércoles'), ('doc-022', 'viernes'),
('doc-032', 'lunes'), ('doc-032', 'miércoles'), ('doc-032', 'viernes'),
('doc-042', 'lunes'), ('doc-042', 'miércoles'), ('doc-042', 'viernes'),
-- Dermatología
('doc-004', 'martes'), ('doc-004', 'jueves'), ('doc-004', 'sábado'),
('doc-013', 'martes'), ('doc-013', 'jueves'), ('doc-013', 'sábado'),
('doc-023', 'martes'), ('doc-023', 'jueves'), ('doc-023', 'sábado'),
('doc-033', 'martes'), ('doc-033', 'jueves'), ('doc-033', 'sábado'),
('doc-043', 'martes'), ('doc-043', 'jueves'), ('doc-043', 'sábado'),
-- Ginecología
('doc-002', 'lunes'), ('doc-002', 'miércoles'), ('doc-002', 'viernes'),
('doc-014', 'lunes'), ('doc-014', 'miércoles'), ('doc-014', 'viernes'),
('doc-024', 'lunes'), ('doc-024', 'miércoles'), ('doc-024', 'viernes'),
('doc-034', 'lunes'), ('doc-034', 'miércoles'), ('doc-034', 'viernes'),
('doc-044', 'lunes'), ('doc-044', 'miércoles'), ('doc-044', 'viernes'),
-- Neurología
('doc-005', 'lunes'), ('doc-005', 'miércoles'), ('doc-005', 'jueves'),
('doc-015', 'lunes'), ('doc-015', 'miércoles'), ('doc-015', 'jueves'),
('doc-025', 'lunes'), ('doc-025', 'miércoles'), ('doc-025', 'jueves'),
('doc-035', 'lunes'), ('doc-035', 'miércoles'), ('doc-035', 'jueves'),
('doc-045', 'lunes'), ('doc-045', 'miércoles'), ('doc-045', 'jueves'),
-- Psicología
('doc-006', 'martes'), ('doc-006', 'jueves'), ('doc-006', 'sábado'),
('doc-016', 'martes'), ('doc-016', 'jueves'), ('doc-016', 'sábado'),
('doc-026', 'martes'), ('doc-026', 'jueves'), ('doc-026', 'sábado'),
('doc-036', 'martes'), ('doc-036', 'jueves'), ('doc-036', 'sábado'),
('doc-046', 'martes'), ('doc-046', 'jueves'), ('doc-046', 'sábado'),
-- Oftalmología
('doc-007', 'lunes'), ('doc-007', 'martes'), ('doc-007', 'miércoles'), ('doc-007', 'jueves'), ('doc-007', 'viernes'),
('doc-017', 'lunes'), ('doc-017', 'martes'), ('doc-017', 'miércoles'), ('doc-017', 'jueves'), ('doc-017', 'viernes'),
('doc-027', 'lunes'), ('doc-027', 'martes'), ('doc-027', 'miércoles'), ('doc-027', 'jueves'), ('doc-027', 'viernes'),
('doc-037', 'lunes'), ('doc-037', 'martes'), ('doc-037', 'miércoles'), ('doc-037', 'jueves'), ('doc-037', 'viernes'),
('doc-047', 'lunes'), ('doc-047', 'martes'), ('doc-047', 'miércoles'), ('doc-047', 'jueves'), ('doc-047', 'viernes'),
-- Endocrinología
('doc-008', 'lunes'), ('doc-008', 'miércoles'), ('doc-008', 'viernes'),
('doc-018', 'lunes'), ('doc-018', 'miércoles'), ('doc-018', 'viernes'),
('doc-028', 'lunes'), ('doc-028', 'miércoles'), ('doc-028', 'viernes'),
('doc-038', 'lunes'), ('doc-038', 'miércoles'), ('doc-038', 'viernes'),
('doc-048', 'lunes'), ('doc-048', 'miércoles'), ('doc-048', 'viernes'),
-- Urología
('doc-009', 'martes'), ('doc-009', 'jueves'), ('doc-009', 'sábado'),
('doc-019', 'martes'), ('doc-019', 'jueves'), ('doc-019', 'sábado'),
('doc-029', 'martes'), ('doc-029', 'jueves'), ('doc-029', 'sábado'),
('doc-039', 'martes'), ('doc-039', 'jueves'), ('doc-039', 'sábado'),
('doc-049', 'martes'), ('doc-049', 'jueves'), ('doc-049', 'sábado'),
-- Medicina General
('doc-010', 'lunes'), ('doc-010', 'martes'), ('doc-010', 'miércoles'), ('doc-010', 'jueves'), ('doc-010', 'viernes'),
('doc-020', 'lunes'), ('doc-020', 'martes'), ('doc-020', 'miércoles'), ('doc-020', 'jueves'), ('doc-020', 'viernes'),
('doc-030', 'lunes'), ('doc-030', 'martes'), ('doc-030', 'miércoles'), ('doc-030', 'jueves'), ('doc-030', 'viernes'),
('doc-040', 'lunes'), ('doc-040', 'martes'), ('doc-040', 'miércoles'), ('doc-040', 'jueves'), ('doc-040', 'viernes'),
('doc-050', 'lunes'), ('doc-050', 'martes'), ('doc-050', 'miércoles'), ('doc-050', 'jueves'), ('doc-050', 'viernes');
-- Insertar pacientes de ejemplo
INSERT INTO pacientes (id, nombre, apellido, email, telefono, fecha_nacimiento, genero, direccion) VALUES
('pac-001', 'María', 'González', 'maria.gonzalez@email.com', '+504 9999-0001', '1990-05-15', 'Femenino', 'Tegucigalpa, Honduras'),
('pac-002', 'Juan', 'López', 'juan.lopez@email.com', '+504 9999-0002', '1985-08-22', 'Masculino', 'San Pedro Sula, Honduras'),
('pac-003', 'Ana', 'Martínez', 'ana.martinez@email.com', '+504 9999-0003', '1992-12-10', 'Femenino', 'La Ceiba, Honduras'),
('pac-004', 'Carlos', 'Rodríguez', 'carlos.rodriguez@email.com', '+504 9999-0004', '1988-03-20', 'Masculino', 'Choluteca, Honduras'),
('pac-005', 'Laura', 'Hernández', 'laura.hernandez@email.com', '+504 9999-0005', '1995-07-08', 'Femenino', 'Comayagua, Honduras'),
('pac-006', 'Roberto', 'García', 'roberto.garcia@email.com', '+504 9999-0006', '1983-11-14', 'Masculino', 'El Progreso, Honduras'),
('pac-007', 'Carmen', 'Fernández', 'carmen.fernandez@email.com', '+504 9999-0007', '1991-09-30', 'Femenino', 'Tela, Honduras'),
('pac-008', 'Miguel', 'Pérez', 'miguel.perez@email.com', '+504 9999-0008', '1987-04-12', 'Masculino', 'Danlí, Honduras'),
('pac-009', 'Isabel', 'Sánchez', 'isabel.sanchez@email.com', '+504 9999-0009', '1993-06-25', 'Femenino', 'Juticalpa, Honduras'),
('pac-010', 'Francisco', 'Ramírez', 'francisco.ramirez@email.com', '+504 9999-0010', '1986-01-18', 'Masculino', 'Santa Rosa de Copán, Honduras');

-- Insertar citas de ejemplo
INSERT INTO citas (id, doctor_id, paciente_id, fecha, hora, estado, motivo, notas) VALUES
('cit-001', 'doc-001', 'pac-001', '2024-01-15', '09:00', 'CONFIRMADA', 'Consulta de rutina', 'Paciente con buen estado general'),
('cit-002', 'doc-002', 'pac-002', '2024-01-16', '10:30', 'PENDIENTE', 'Control prenatal', 'Primera consulta prenatal'),
('cit-003', 'doc-003', 'pac-003', '2024-01-17', '08:00', 'CONFIRMADA', 'Evaluación cardíaca', 'Paciente refiere dolor en el pecho'),
('cit-004', 'doc-004', 'pac-004', '2024-01-18', '14:00', 'CONFIRMADA', 'Consulta dermatológica', 'Paciente con manchas en la piel'),
('cit-005', 'doc-005', 'pac-005', '2024-01-19', '11:00', 'PENDIENTE', 'Evaluación neurológica', 'Paciente con dolores de cabeza frecuentes'),
('cit-006', 'doc-006', 'pac-006', '2024-01-20', '15:30', 'CONFIRMADA', 'Terapia psicológica', 'Sesión de seguimiento'),
('cit-007', 'doc-007', 'pac-007', '2024-01-21', '09:30', 'PENDIENTE', 'Consulta oftalmológica', 'Paciente con problemas de visión'),
('cit-008', 'doc-008', 'pac-008', '2024-01-22', '13:00', 'CONFIRMADA', 'Control endocrinológico', 'Paciente diabético'),
('cit-009', 'doc-009', 'pac-009', '2024-01-23', '10:00', 'PENDIENTE', 'Consulta urológica', 'Paciente con síntomas urinarios'),
('cit-010', 'doc-010', 'pac-010', '2024-01-24', '16:00', 'CONFIRMADA', 'Consulta general', 'Chequeo anual');

-- Relacionar doctores con especialidades
INSERT INTO doctor_especialidades (doctor_id, especialidad_id) VALUES
('doc-001', 'esp-003'), ('doc-002', 'esp-006'), ('doc-003', 'esp-001'),
('doc-004', 'esp-002'), ('doc-005', 'esp-004'), ('doc-006', 'esp-008'),
('doc-007', 'esp-007'), ('doc-008', 'esp-009'), ('doc-009', 'esp-010'),
('doc-010', 'esp-005'), ('doc-011', 'esp-001'), ('doc-012', 'esp-003'),
('doc-013', 'esp-002'), ('doc-014', 'esp-006'), ('doc-015', 'esp-004'),
('doc-016', 'esp-008'), ('doc-017', 'esp-007'), ('doc-018', 'esp-009'),
('doc-019', 'esp-010'), ('doc-020', 'esp-005'); 