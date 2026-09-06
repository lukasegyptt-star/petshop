-- Esquema de base de datos para el sistema Petshop
-- Ejecutar este script en MySQL antes de correr la aplicación

CREATE DATABASE IF NOT EXISTS petshop_db;
USE petshop_db;

-- Usuarios del sistema: ADMINISTRADOR, ADMINISTRATIVO, BANADOR
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    rol ENUM('ADMINISTRADOR','ADMINISTRATIVO','BANADOR') NOT NULL
);

-- Clientes (dueños de mascotas)
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(30),
    email VARCHAR(100),
    creado_por INT,
    FOREIGN KEY (creado_por) REFERENCES usuarios(id)
);

-- Mascotas: cada mascota pertenece a un único cliente
CREATE TABLE IF NOT EXISTS mascotas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50),
    raza VARCHAR(50),
    cliente_id INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

-- Turnos / agenda
CREATE TABLE IF NOT EXISTS turnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mascota_id INT NOT NULL,
    tipo_servicio ENUM('BANO','CORTE_PELO','BANO_Y_CORTE') NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado ENUM('PENDIENTE','FINALIZADO') NOT NULL DEFAULT 'PENDIENTE',
    banador_id INT,
    creado_por INT,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id),
    FOREIGN KEY (banador_id) REFERENCES usuarios(id),
    FOREIGN KEY (creado_por) REFERENCES usuarios(id)
);

-- Usuario administrador inicial (usuario: admin / contraseña: admin123)
INSERT INTO usuarios (nombre_usuario, contrasena, nombre_completo, rol)
VALUES ('admin', 'admin123', 'Administrador General', 'ADMINISTRADOR')
ON DUPLICATE KEY UPDATE nombre_usuario = nombre_usuario;
