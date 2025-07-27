-- Script DML para insertar datos de ejemplo
-- V2__Insert_sample_doctors.sql

-- Doctor 1: Dra. Sofía Torres - Pediatría
INSERT INTO doctors (id, name, specialty, img, experience_years, rating, hospital, available, description, horario_entrada, horario_salida, duracion_cita) 
VALUES (
    '7b80f187-9e7a-448b-8691-363f12dd72f5',
    'Dra. Sofía Torres',
    'Pediatría',
    'https://randomuser.me/api/portraits/women/0.jpg',
    24,
    3.7,
    'Hospital Central',
    true,
    'Especialista en pediatría con amplia experiencia en atención infantil',
    '08:00',
    '17:00',
    30
);

-- Tags para Dra. Sofía Torres
INSERT INTO doctor_tags (doctor_id, tag) VALUES 
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'salud'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'consultas'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'pediatría');

-- Días laborales para Dra. Sofía Torres
INSERT INTO doctor_dias_laborales (doctor_id, dia) VALUES 
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'lunes'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'martes'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'miércoles'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'jueves'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'viernes');

-- Horarios disponibles para Dra. Sofía Torres
INSERT INTO doctor_horarios_disponibles (doctor_id, dia, horarios) VALUES 
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'lunes', '["08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30"]'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'martes', '["08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30"]'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'miércoles', '["08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30"]'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'jueves', '["08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30"]'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'viernes', '["08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30"]'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'sábado', '[]'),
('7b80f187-9e7a-448b-8691-363f12dd72f5', 'domingo', '[]');

-- Doctor 2: Dr. Carlos Pineda - Ginecología
INSERT INTO doctors (id, name, specialty, img, experience_years, rating, hospital, available, description, horario_entrada, horario_salida, duracion_cita) 
VALUES (
    'b0570476-6470-4e4b-864e-dec1f7ffd1e9',
    'Dr. Carlos Pineda',
    'Ginecología',
    'https://randomuser.me/api/portraits/men/1.jpg',
    28,
    3.8,
    'Clínica Vida',
    true,
    'Especialista en ginecología y obstetricia',
    '09:00',
    '18:00',
    45
);

-- Tags para Dr. Carlos Pineda
INSERT INTO doctor_tags (doctor_id, tag) VALUES 
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'salud'),
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'consultas'),
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'ginecología');

-- Días laborales para Dr. Carlos Pineda
INSERT INTO doctor_dias_laborales (doctor_id, dia) VALUES 
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'lunes'),
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'miércoles'),
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'viernes');

-- Horarios disponibles para Dr. Carlos Pineda
INSERT INTO doctor_horarios_disponibles (doctor_id, dia, horarios) VALUES 
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'lunes', '["09:00","09:45","10:30","11:15","12:00","12:45","13:30","14:15","15:00","15:45","16:30","17:15"]'),
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'martes', '[]'),
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'miércoles', '["09:00","09:45","10:30","11:15","12:00","12:45","13:30","14:15","15:00","15:45","16:30","17:15"]'),
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'jueves', '[]'),
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'viernes', '["09:00","09:45","10:30","11:15","12:00","12:45","13:30","14:15","15:00","15:45","16:30","17:15"]'),
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'sábado', '[]'),
('b0570476-6470-4e4b-864e-dec1f7ffd1e9', 'domingo', '[]');

-- Doctor 3: Dra. Ana Ramírez - Cardiología
INSERT INTO doctors (id, name, specialty, img, experience_years, rating, hospital, available, description, horario_entrada, horario_salida, duracion_cita) 
VALUES (
    'd28f8f5a-ed71-4271-b4ec-757cfa156e68',
    'Dra. Ana Ramírez',
    'Cardiología',
    'https://randomuser.me/api/portraits/women/2.jpg',
    27,
    3.6,
    'Centro Médico Esperanza',
    true,
    'Cardióloga especialista en enfermedades del corazón',
    '07:00',
    '16:00',
    30
);

-- Tags para Dra. Ana Ramírez
INSERT INTO doctor_tags (doctor_id, tag) VALUES 
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'salud'),
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'consultas'),
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'cardiología');

-- Días laborales para Dra. Ana Ramírez
INSERT INTO doctor_dias_laborales (doctor_id, dia) VALUES 
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'martes'),
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'jueves'),
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'sábado');

-- Horarios disponibles para Dra. Ana Ramírez
INSERT INTO doctor_horarios_disponibles (doctor_id, dia, horarios) VALUES 
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'lunes', '[]'),
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'martes', '["07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30"]'),
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'miércoles', '[]'),
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'jueves', '["07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30"]'),
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'viernes', '[]'),
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'sábado', '["07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30"]'),
('d28f8f5a-ed71-4271-b4ec-757cfa156e68', 'domingo', '[]'); 