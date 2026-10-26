-- Base de datos del proyecto Don Limón (APF3)
-- Equivalente a lo que genera Hibernate con spring.jpa.hibernate.ddl-auto=update
CREATE DATABASE IF NOT EXISTS don_limon_db CHARACTER SET utf8mb4;
USE don_limon_db;

CREATE TABLE IF NOT EXISTS categoria (
    id_categoria INT          NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id_categoria),
    UNIQUE KEY uk_categoria_nombre (nombre)
);

CREATE TABLE IF NOT EXISTS plato (
    id_plato     INT           NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(100)  NOT NULL,
    descripcion  VARCHAR(255)  NOT NULL,
    precio       DECIMAL(8,2)  NOT NULL,
    imagen       VARCHAR(255)  NULL,
    id_categoria INT           NOT NULL,
    PRIMARY KEY (id_plato),
    CONSTRAINT fk_plato_categoria FOREIGN KEY (id_categoria) REFERENCES categoria (id_categoria)
);

CREATE TABLE IF NOT EXISTS reserva (
    id_reserva INT          NOT NULL AUTO_INCREMENT,
    nombre     VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    fecha      DATE         NOT NULL,
    comensales INT          NOT NULL,
    PRIMARY KEY (id_reserva)
);

-- Datos iniciales (la aplicación también los carga sola si la BD está vacía)
INSERT INTO categoria (nombre) VALUES ('Entradas'), ('Platos de fondo'), ('Sopas'), ('Bebidas');

INSERT INTO plato (nombre, descripcion, precio, id_categoria) VALUES
('Conchas Negras',     'Conchas negras al natural con limón y salsa criolla.', 28.00, 1),
('Leche de Tigre',     'Jugo cítrico de ceviche con trozos de pescado, ají y cebolla.', 12.00, 1),
('Ceviche Mixto',      'Mezcla de pescado, mariscos y conchas marinados en limón.', 30.00, 2),
('Arroz con Mariscos', 'Arroz cremoso con mariscos frescos y salsa especial de la casa.', 35.00, 2),
('Seco de Chabelo',    'Plato norteño típico de Piura: carne seca guisada con yuca frita.', 28.00, 2),
('Parihuela',          'Sopa marina espesa con mariscos, pescado fresco y ají panca.', 32.00, 3),
('Chicha Morada',      'Bebida tradicional peruana de maíz morado con frutas y canela.', 8.00, 4),
('Limonada',           'Limonada natural con menta y hierbabuena, preparada al momento.', 7.00, 4);
