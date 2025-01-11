-- creacion de la bbdd
drop database if exists olimpiadas;
create database olimpiadas;
use olimpiadas;
CREATE TABLE puesto (
    id_puesto INT,
    posicion VARCHAR(50) NOT NULL
);
CREATE TABLE competidor_de_evento (
    id_evento INT,
    id_competidor INT,
    id_puesto INT
);
CREATE TABLE evento (
    id_evento INT,
    id_deporte INT,
    id_juego_olimpico INT,
    nombre_evento VARCHAR(100) NOT NULL
);
CREATE TABLE juez (
    id_juez INT,
    id_evento INT,
    nombre_juez VARCHAR(100) NOT NULL
);
CREATE TABLE atleta (
    id_atleta INT,
    genero CHAR(1) NOT NULL,
    altura FLOAT NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL
);
CREATE TABLE region_atleta (
    id_atleta INT,
    id_region INT
);
CREATE TABLE region (
    id_region INT,
    noc CHAR(3) NOT NULL,
    nombre_region VARCHAR(100) NOT NULL
);
CREATE TABLE competidor_juego_olimpico (
    id_competidores INT,
    id_atleta INT,
    id_juego_olimpico INT,
    edad INT NOT NULL
);
CREATE TABLE juego_olimpico (
    id_juego_olimpico INT,
    id_ciudad INT,
    nombre_juegos VARCHAR(100) NOT NULL,
    agno_celebracion INT NOT NULL,
    estacion VARCHAR(50) NOT NULL
);
CREATE TABLE deporte (
    id_deporte INT,
    nombre_deporte VARCHAR(100) NOT NULL
);
CREATE TABLE ciudad (
    id_ciudad INT,
    nombre_ciudad VARCHAR(100) NOT NULL,
    pais VARCHAR(100) NOT NULL
);












-- PIRMARY KEYS AND FOREIGN KEYS
ALTER TABLE puesto ADD PRIMARY KEY (id_puesto);
ALTER TABLE region ADD PRIMARY KEY (id_region);
ALTER TABLE ciudad ADD PRIMARY KEY (id_ciudad);
ALTER TABLE juego_olimpico
ADD PRIMARY KEY (id_juego_olimpico),
ADD FOREIGN KEY (id_ciudad) REFERENCES ciudad(id_ciudad);
ALTER TABLE deporte ADD PRIMARY KEY (id_deporte);
ALTER TABLE evento
ADD PRIMARY KEY (id_evento),
ADD FOREIGN KEY (id_deporte) REFERENCES deporte(id_deporte),
ADD FOREIGN KEY (id_juego_olimpico) REFERENCES juego_olimpico(id_juego_olimpico);
ALTER TABLE atleta ADD PRIMARY KEY (id_atleta);
ALTER TABLE region_atleta
ADD PRIMARY KEY (id_atleta, id_region),
ADD FOREIGN KEY (id_atleta) REFERENCES atleta(id_atleta),
ADD FOREIGN KEY (id_region) REFERENCES region(id_region);
ALTER TABLE competidor_juego_olimpico
ADD PRIMARY KEY (id_competidores),
ADD FOREIGN KEY (id_atleta) REFERENCES atleta(id_atleta),
ADD FOREIGN KEY (id_juego_olimpico) REFERENCES juego_olimpico(id_juego_olimpico);
ALTER TABLE juez
ADD PRIMARY KEY (id_juez),
ADD FOREIGN KEY (id_evento) REFERENCES evento(id_evento);
ALTER TABLE competidor_de_evento
ADD PRIMARY KEY (id_evento, id_competidor, id_puesto),
ADD FOREIGN KEY (id_evento) REFERENCES evento(id_evento),
ADD FOREIGN KEY (id_competidor) REFERENCES competidor_juego_olimpico(id_competidores),
ADD FOREIGN KEY (id_puesto) REFERENCES puesto(id_puesto);










-- DELETES ON CASCADE
ALTER TABLE competidor_de_evento
DROP FOREIGN KEY competidor_de_evento_ibfk_1,
DROP FOREIGN KEY competidor_de_evento_ibfk_2,
DROP FOREIGN KEY competidor_de_evento_ibfk_3,
ADD CONSTRAINT competidor_de_evento_fk1 FOREIGN KEY (id_evento) REFERENCES evento(id_evento) ON DELETE CASCADE,
ADD CONSTRAINT competidor_de_evento_fk2 FOREIGN KEY (id_competidor) REFERENCES competidor_juego_olimpico(id_competidores) ON DELETE CASCADE,
ADD CONSTRAINT competidor_de_evento_fk3 FOREIGN KEY (id_puesto) REFERENCES puesto(id_puesto) ON DELETE CASCADE;

ALTER TABLE competidor_juego_olimpico
DROP FOREIGN KEY competidor_juego_olimpico_ibfk_1,
DROP FOREIGN KEY competidor_juego_olimpico_ibfk_2,
ADD CONSTRAINT competidor_juego_olimpico_fk1 FOREIGN KEY (id_atleta) REFERENCES atleta(id_atleta) ON DELETE CASCADE,
ADD CONSTRAINT competidor_juego_olimpico_fk2 FOREIGN KEY (id_juego_olimpico) REFERENCES juego_olimpico(id_juego_olimpico) ON DELETE CASCADE;

ALTER TABLE juez
DROP FOREIGN KEY juez_ibfk_1,
ADD CONSTRAINT juez_fk1 FOREIGN KEY (id_evento) REFERENCES evento(id_evento) ON DELETE CASCADE;

ALTER TABLE region_atleta
DROP FOREIGN KEY region_atleta_ibfk_1,
DROP FOREIGN KEY region_atleta_ibfk_2,
ADD CONSTRAINT region_atleta_fk1 FOREIGN KEY (id_atleta) REFERENCES atleta(id_atleta) ON DELETE CASCADE,
ADD CONSTRAINT region_atleta_fk2 FOREIGN KEY (id_region) REFERENCES region(id_region) ON DELETE CASCADE;

ALTER TABLE evento
DROP FOREIGN KEY evento_ibfk_1,
DROP FOREIGN KEY evento_ibfk_2,
ADD CONSTRAINT evento_fk1 FOREIGN KEY (id_deporte) REFERENCES deporte(id_deporte) ON DELETE CASCADE,
ADD CONSTRAINT evento_fk2 FOREIGN KEY (id_juego_olimpico) REFERENCES juego_olimpico(id_juego_olimpico) ON DELETE CASCADE;


ALTER TABLE juego_olimpico
DROP FOREIGN KEY juego_olimpico_ibfk_1,
ADD CONSTRAINT juego_olimpico_fk1 FOREIGN KEY (id_ciudad) REFERENCES ciudad(id_ciudad) ON DELETE CASCADE;





-- Procedimiento para obtener un el nombre y genero de los atletas que han participado en un evento
delimiter vico

CREATE PROCEDURE get_competitors_by_event(IN event_id INT)
BEGIN
    SELECT atleta.nombre_completo, atleta.genero
    FROM competidor_de_evento
    JOIN atleta ON competidor_de_evento.id_competidor = atleta.id_atleta
    WHERE competidor_de_evento.id_evento = event_id;
END;
delimiter ;







-- TRIGGER 1: Evitar que un juez sea asignado a varios juegos 
DELIMITER //
CREATE TRIGGER validar_juez_unico_por_juego
BEFORE INSERT ON juez
FOR EACH ROW
BEGIN
    DECLARE juegos_actual INT;

    -- Obtener los juegos olímpicos del evento que se está intentando asignar
    SELECT id_juego_olimpico INTO juegos_actual
    FROM evento
    WHERE id_evento = NEW.id_evento;

    -- Verificar si el juez ya está asignado a otro evento de los mismos juegos
    IF EXISTS (
        SELECT 1
        FROM juez j
        JOIN evento e ON j.id_evento = e.id_evento
        WHERE j.id_juez = NEW.id_juez AND e.id_juego_olimpico = juegos_actual
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Un juez no puede arbitrar más de un evento en los mismos juegos olímpicos.';
    END IF;
END;
//
DELIMITER ;


-- TRIGGER 2: Validar que la edad mínima de los atletas sea igual o mayor a 16 años:
DELIMITER //
CREATE TRIGGER validar_edad_minima
BEFORE INSERT ON competidor_juego_olimpico
FOR EACH ROW
BEGIN
    IF NEW.edad < 16 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La edad del competidor debe ser al menos 16 años para participar en los juegos olímpicos.';
    END IF;
END;
//
DELIMITER ;


   


-- INSERCIÓN DATOS DE EJEMPLO
START TRANSACTION;
INSERT INTO atleta (id_atleta, genero, altura, nombre_completo) VALUES
(1, 'M', 1.85, 'Juan Pérez'),
(2, 'F', 1.70, 'Ana Gómez'),
(3, 'M', 1.90, 'Carlos García'),
(4, 'F', 1.65, 'María López'),
(5, 'M', 1.75, 'Miguel Hernández'),
(6, 'F', 1.68, 'Laura Martínez'),
(7, 'M', 1.80, 'David Rodríguez'),
(8, 'F', 1.60, 'Sofía Fernández'),
(9, 'M', 1.78, 'José Morales'),
(10, 'F', 1.72, 'Isabela Romero'),
(11, 'M', 1.83, 'Daniel Ruiz'),
(12, 'F', 1.69, 'Olivia Torres'),
(13, 'M', 1.88, 'Alejandro Medina'),
(14, 'F', 1.63, 'Emma Sánchez'),
(15, 'M', 1.81, 'Mateo Castro'),
(16, 'F', 1.67, 'Lucía Jiménez'),
(17, 'M', 1.76, 'Antonio Peña'),
(18, 'F', 1.70, 'Mía Vega'),
(19, 'M', 1.82, 'Andrés Paredes'),
(20, 'M', 1.70, 'Lionel Messi');
INSERT INTO region (id_region, noc, nombre_region) VALUES
(1, 'ARG', 'Argentina'),
(2, 'USA', 'Estados Unidos'),
(3, 'BRA', 'Brasil'),
(4, 'ESP', 'España'),
(5, 'FRA', 'Francia'),
(6, 'ITA', 'Italia'),
(7, 'GER', 'Alemania'),
(8, 'MEX', 'México'),
(9, 'CAN', 'Canadá'),
(10, 'JPN', 'Japón');
INSERT INTO region_atleta (id_atleta, id_region) VALUES
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(6, 7),
(7, 8),
(8, 9),
(9, 10),
(10, 1),
(11, 2),
(12, 3),
(13, 4),
(14, 5),
(15, 6),
(16, 7),
(17, 8),
(18, 9),
(19, 10),
(20, 1);
INSERT INTO ciudad (id_ciudad, nombre_ciudad, pais) VALUES
(1, 'Los Ángeles', 'Estados Unidos'),
(2, 'Toronto', 'Canadá'),
(3, 'Beijing', 'China'),
(4, 'Río de Janeiro', 'Brasil'),
(5, 'Buenos Aires', 'Argentina');
INSERT INTO juego_olimpico (id_juego_olimpico, id_ciudad, nombre_juegos, agno_celebracion, estacion) VALUES
(1, 1, 'Olimpiadas de Verano Los Ángeles', 2028, 'Verano'),
(2, 2, 'Olimpiadas de Invierno Toronto', 2026, 'Invierno'),
(3, 3, 'Olimpiadas de Verano Beijing', 2008, 'Verano'),
(4, 4, 'Olimpiadas de Verano Río', 2016, 'Verano'),
(5, 5, 'Juegos Olímpicos Juveniles Buenos Aires', 2018, 'Verano');
INSERT INTO deporte (id_deporte, nombre_deporte) VALUES
(1, 'Atletismo'),
(2, 'Natación'),
(3, 'Gimnasia'),
(4, 'Ciclismo'),
(5, 'Fútbol'),
(6, 'Boxeo'),
(7, 'Baloncesto'),
(8, 'Voleibol'),
(9, 'Tenis'),
(10, 'Golf'),
(11, 'Rugby'),
(12, 'Hockey'),
(13, 'Esgrima'),
(14, 'Handbol'),
(15, 'Taekwondo');
INSERT INTO evento (id_evento, id_deporte, id_juego_olimpico, nombre_evento) VALUES
(1, 1, 1, '100 metros planos'),
(2, 2, 2, '100 metros libres'),
(3, 3, 3, 'Gimnasia artística'),
(4, 4, 4, 'Ciclismo de ruta'),
(5, 5, 3, 'Final de fútbol'),
(6, 6, 1, 'Boxeo Masculino'),
(7, 7, 2, 'Baloncesto Femenino'),
(8, 8, 3, 'Voleibol Masculino'),
(9, 9, 4, 'Tenis Individual'),
(10, 10, 5, 'Golf Individual'),
(11, 11, 1, 'Rugby 7 Masculino'),
(12, 12, 2, 'Hockey Femenino'),
(13, 13, 3, 'Esgrima Individual'),
(14, 14, 4, 'Handbol Masculino'),
(15, 15, 5, 'Taekwondo Masculino');
INSERT INTO puesto (id_puesto, posicion) VALUES
(1, 'Oro'),
(2, 'Plata'),
(3, 'Bronce');
INSERT INTO competidor_juego_olimpico (id_competidores, id_atleta, id_juego_olimpico, edad) VALUES
(1, 1, 1, 25),
(2, 2, 2, 23),
(3, 3, 3, 28),
(4, 4, 4, 22),
(5, 5, 5, 27),
(6, 6, 1, 21),
(7, 7, 2, 26),
(8, 8, 3, 24),
(9, 9, 4, 29),
(10, 20, 3, 20);
INSERT INTO competidor_de_evento (id_evento, id_competidor, id_puesto) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 1),
(5, 10, 1),
(1, 6, 3),
(2, 7, 1),
(3, 8, 2),
(4, 9, 3),
(5, 5, 2);
INSERT INTO juez (id_juez, id_evento, nombre_juez) VALUES
(1, 1, 'Carlos Rodríguez'),
(2, 2, 'Ana Martínez'),
(3, 3, 'Luis Fernández'),
(4, 4, 'Marta Sánchez'),
(5, 5, 'Javier Pérez');
COMMIT;


-- MÁS DATOS DE EJEMPLO

START TRANSACTION;

-- Nuevos atletas
INSERT INTO atleta (id_atleta, genero, altura, nombre_completo) VALUES
(21, 'M', 1.88, 'Tomás Rivas'),
(22, 'F', 1.62, 'Paula Cruz'),
(23, 'M', 1.90, 'Fernando Salazar'),
(24, 'F', 1.68, 'Clara Ortiz'),
(25, 'M', 1.82, 'Hugo Martínez'),
(26, 'F', 1.70, 'Diana Moreno'),
(27, 'M', 1.85, 'Iván Gómez'),
(28, 'F', 1.64, 'Carla Torres'),
(29, 'M', 1.75, 'Adrián Blanco'),
(30, 'F', 1.72, 'Julia Ruiz'),
(31, 'M', 1.83, 'Gabriel Ortega'),
(32, 'F', 1.69, 'Marta Pérez'),
(33, 'M', 1.78, 'Joaquín Herrera'),
(34, 'F', 1.67, 'Valeria Díaz'),
(35, 'M', 1.80, 'Álvaro Castillo'),
(36, 'F', 1.66, 'Natalia Vega'),
(37, 'M', 1.84, 'Sebastián Reyes'),
(38, 'F', 1.63, 'Camila López'),
(39, 'M', 1.86, 'Julián Navarro'),
(40, 'F', 1.68, 'Sofía Hernández');

-- Relación de atletas con regiones
INSERT INTO region_atleta (id_atleta, id_region) VALUES
(21, 1),
(22, 2),
(23, 3),
(24, 4),
(25, 5),
(26, 6),
(27, 7),
(28, 8),
(29, 9),
(30, 10),
(31, 1),
(32, 2),
(33, 3),
(34, 4),
(35, 5),
(36, 6),
(37, 7),
(38, 8),
(39, 9),
(40, 10);

-- Participación de atletas en juegos olímpicos
INSERT INTO competidor_juego_olimpico (id_competidores, id_atleta, id_juego_olimpico, edad) VALUES
(11, 21, 1, 24),
(12, 22, 2, 25),
(13, 23, 3, 23),
(14, 24, 4, 27),
(15, 25, 5, 26),
(16, 26, 1, 22),
(17, 27, 2, 28),
(18, 28, 3, 21),
(19, 29, 4, 29),
(20, 30, 5, 20),
(21, 31, 1, 23),
(22, 32, 2, 25),
(23, 33, 3, 24),
(24, 34, 4, 26),
(25, 35, 5, 27),
(26, 36, 1, 24),
(27, 37, 2, 25),
(28, 38, 3, 22),
(29, 39, 4, 28),
(30, 40, 5, 21);

-- Participación en eventos y posiciones
INSERT INTO competidor_de_evento (id_evento, id_competidor, id_puesto) VALUES
(6, 11, 1),
(7, 12, 2),
(8, 13, 3),
(9, 14, 1),
(10, 15, 2),
(11, 16, 3),
(12, 17, 1),
(13, 18, 2),
(14, 19, 3),
(15, 20, 1),
(1, 21, 2),
(2, 22, 3),
(3, 23, 1),
(4, 24, 2),
(5, 25, 3),
(6, 26, 1),
(7, 27, 2),
(8, 28, 3),
(9, 29, 1),
(10, 30, 2);

-- Asignar jueces a eventos adicionales si es necesario
INSERT INTO juez (id_juez, id_evento, nombre_juez) VALUES
(6, 6, 'Pedro Gutiérrez'),
(7, 7, 'Elena García'),
(8, 8, 'Santiago Peña'),
(9, 9, 'Lucía Ríos'),
(10, 10, 'Fernando Castro');

COMMIT;

START TRANSACTION;

-- Nuevos atletas
INSERT INTO atleta (id_atleta, genero, altura, nombre_completo) VALUES
(41, 'M', 1.88, 'Enrique Domínguez'),
(42, 'F', 1.63, 'Rocío Navarro'),
(43, 'M', 1.92, 'Manuel Sánchez'),
(44, 'F', 1.70, 'Sara Ponce'),
(45, 'M', 1.80, 'Diego Vargas'),
(46, 'F', 1.65, 'Ángela Ramírez'),
(47, 'M', 1.87, 'Lucas Cano'),
(48, 'F', 1.67, 'Bianca Ortiz'),
(49, 'M', 1.84, 'Cristian Pérez'),
(50, 'F', 1.61, 'Elena Vázquez'),
(51, 'M', 1.81, 'Jorge Roldán'),
(52, 'F', 1.66, 'Nerea Serrano'),
(53, 'M', 1.86, 'Marcelo Benítez'),
(54, 'F', 1.68, 'Claudia Montero'),
(55, 'M', 1.79, 'Sergio Guzmán'),
(56, 'F', 1.72, 'Verónica Álvarez'),
(57, 'M', 1.77, 'Fabián López'),
(58, 'F', 1.64, 'Alicia Torres'),
(59, 'M', 1.89, 'Oscar Méndez'),
(60, 'F', 1.71, 'Silvia Gutiérrez');

-- Relación de atletas con regiones
INSERT INTO region_atleta (id_atleta, id_region) VALUES
(41, 2),
(42, 3),
(43, 4),
(44, 5),
(45, 6),
(46, 7),
(47, 8),
(48, 9),
(49, 10),
(50, 1),
(51, 2),
(52, 3),
(53, 4),
(54, 5),
(55, 6),
(56, 7),
(57, 8),
(58, 9),
(59, 10),
(60, 1);

-- Participación de atletas en juegos olímpicos
INSERT INTO competidor_juego_olimpico (id_competidores, id_atleta, id_juego_olimpico, edad) VALUES
(31, 41, 1, 23),
(32, 42, 2, 24),
(33, 43, 3, 27),
(34, 44, 4, 22),
(35, 45, 5, 28),
(36, 46, 1, 26),
(37, 47, 2, 24),
(38, 48, 3, 29),
(39, 49, 4, 21),
(40, 50, 5, 25),
(41, 51, 1, 22),
(42, 52, 2, 27),
(43, 53, 3, 23),
(44, 54, 4, 24),
(45, 55, 5, 26),
(46, 56, 1, 28),
(47, 57, 2, 25),
(48, 58, 3, 22),
(49, 59, 4, 27),
(50, 60, 5, 20);

-- Participación en eventos y posiciones
INSERT INTO competidor_de_evento (id_evento, id_competidor, id_puesto) VALUES
(11, 31, 3),
(12, 32, 1),
(13, 33, 2),
(14, 34, 3),
(15, 35, 1),
(1, 36, 2),
(2, 37, 3),
(3, 38, 1),
(4, 39, 2),
(5, 40, 3),
(6, 41, 1),
(7, 42, 2),
(8, 43, 3),
(9, 44, 1),
(10, 45, 2),
(11, 46, 3),
(12, 47, 1),
(13, 48, 2),
(14, 49, 3),
(15, 50, 1);

-- Asignar jueces a eventos adicionales si es necesario
INSERT INTO juez (id_juez, id_evento, nombre_juez) VALUES
(11, 11, 'Pablo Díaz'),
(12, 12, 'María Sánchez'),
(13, 13, 'Javier López'),
(14, 14, 'Sofía Ramos'),
(15, 15, 'Andrés Gálvez');

COMMIT;




-- CONSULTAS PARA USAR EN LA APLICACIÓN

-- 1.Mostrar el nombre completo de un atleta, la región a la que pertenece, entre paréntesis el nombre abreviado de la región, el número de juegos olímpicos en los que ha participado,  
-- el primer juego olímpico en el que participó, su edad, el número total de participaciones, y la cantidad de medallas olímpicas que tiene de la siguiente forma: Oro: 1, Plata: 0, Bronce: 3, Total: 4.
-- La edad no se muestra por que está ligada a cada juego olímpico, este dato se podría usar para calcular la edad media de los competidores en cada año de los juegos olímpicos entre otras cosas.

SELECT 
    a.nombre_completo AS "Atleta",
    r.nombre_region AS "Región",
    CONCAT('(', r.noc, ')') AS "Código Región",
    COUNT(DISTINCT cj.id_juego_olimpico) AS "Juegos Olímpicos Participados",
    MIN(jo.agno_celebracion) AS "Primer Juego Olímpico",
    SUM(CASE WHEN cde.id_puesto = 1 THEN 1 ELSE 0 END) AS "Oro",
    SUM(CASE WHEN cde.id_puesto = 2 THEN 1 ELSE 0 END) AS "Plata",
    SUM(CASE WHEN cde.id_puesto = 3 THEN 1 ELSE 0 END) AS "Bronce",
    SUM(CASE WHEN cde.id_puesto IN (1, 2, 3) THEN 1 ELSE 0 END) AS "Total Medallas"
FROM 
    atleta a
JOIN 
    region_atleta ra ON a.id_atleta = ra.id_atleta
JOIN 
    region r ON ra.id_region = r.id_region
JOIN 
    competidor_juego_olimpico cj ON a.id_atleta = cj.id_atleta
LEFT JOIN 
    competidor_de_evento cde ON cj.id_competidores = cde.id_competidor
LEFT JOIN 
    juego_olimpico jo ON cj.id_juego_olimpico = jo.id_juego_olimpico
GROUP BY 
    a.id_atleta, a.nombre_completo, r.nombre_region, r.noc, cj.edad
ORDER BY 
    a.nombre_completo;

-- 2. Consulta que muestre todas las regiones, el total de medallas de oro, plata , bronce y medallas totales atribuidas a esa región, que las regiones estén ordenadas empezando por la región con más medallas obtenidas.
SELECT 
    r.nombre_region AS "Región",
    r.noc AS "Código Región",
    SUM(CASE WHEN cde.id_puesto = 1 THEN 1 ELSE 0 END) AS "Oro",
    SUM(CASE WHEN cde.id_puesto = 2 THEN 1 ELSE 0 END) AS "Plata",
    SUM(CASE WHEN cde.id_puesto = 3 THEN 1 ELSE 0 END) AS "Bronce",
    SUM(CASE WHEN cde.id_puesto IN (1, 2, 3) THEN 1 ELSE 0 END) AS "Total Medallas"
FROM 
    region r
LEFT JOIN 
    region_atleta ra ON r.id_region = ra.id_region
LEFT JOIN 
    atleta a ON ra.id_atleta = a.id_atleta
LEFT JOIN 
    competidor_juego_olimpico cj ON a.id_atleta = cj.id_atleta
LEFT JOIN 
    competidor_de_evento cde ON cj.id_competidores = cde.id_competidor
GROUP BY 
    r.id_region, r.nombre_region, r.noc
ORDER BY 
    "Total Medallas" DESC;
    
--  3. Consulta que muestre toda la información relacionada con la tabla juego olimpico, 
-- la ciudad y país donde se celebraron, el número de eventos que tuvo, el número de deportes, número de competidores y número de jueces que participaron.
   SELECT 
    jo.id_juego_olimpico,
    jo.nombre_juegos,
    jo.agno_celebracion,
    jo.estacion,
    c.nombre_ciudad,
    c.pais,
    COUNT(DISTINCT e.id_evento) AS numero_eventos,
    COUNT(DISTINCT e.id_deporte) AS numero_deportes,
    COUNT(DISTINCT cjo.id_competidores) AS numero_competidores,
    COUNT(DISTINCT j.id_juez) AS numero_jueces
FROM 
    juego_olimpico jo
JOIN 
    ciudad c ON jo.id_ciudad = c.id_ciudad
LEFT JOIN 
    evento e ON jo.id_juego_olimpico = e.id_juego_olimpico
LEFT JOIN 
    competidor_de_evento cde ON e.id_evento = cde.id_evento
LEFT JOIN 
    competidor_juego_olimpico cjo ON cde.id_competidor = cjo.id_competidores
LEFT JOIN 
    juez j ON e.id_evento = j.id_evento
GROUP BY 
    jo.id_juego_olimpico, c.nombre_ciudad, c.pais, jo.nombre_juegos, jo.agno_celebracion, jo.estacion
ORDER BY 
    jo.agno_celebracion ASC;

   

