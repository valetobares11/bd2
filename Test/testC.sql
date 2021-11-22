create database testC;


USE testC;

CREATE TABLE Alumno(DNI INTEGER NOT NULL PRIMARY KEY,
nro_alumno INTEGER NOT NULL,
nombre VARCHAR(30) NOT NULL,
apellido VARCHAR(30) NOT NULL,
direccion VARCHAR(30),
edad INTEGER NOT NULL,
CONSTRAINT control_edad CHECK(edad > 12 AND edad < 18)
);

CREATE TABLE Curso(codigo INTEGER NOT NULL PRIMARY KEY,
nombre VARCHAR(30),
CONSTRAINT nombres_especificos CHECK(nombre = 'BASE DE DATOS' OR nombre = 'BASE DE DATOS II' OR nombre = 'ORGANIZACION DEL PROCESADOR')
);

CREATE TABLE Evaluacion(nro_evaluacion INTEGER NOT NULL PRIMARY KEY,
nota INTEGER,
CONSTRAINT rango_notas CHECK(nota > 0 AND nota < 11)
);

CREATE TABLE Cursa(DNI INTEGER NOT NULL,
codigo INTEGER NOT NULL,
nro_evaluacion INTEGER NOT NULL,
CONSTRAINT fkdni FOREIGN KEY (DNI) REFERENCES Alumno(DNI),
CONSTRAINT fkcodigo FOREIGN KEY (codigo) REFERENCES Curso(codigo) ON DELETE CASCADE,
CONSTRAINT fknro FOREIGN KEY (nro_evaluacion) REFERENCES Evaluacion(nro_evaluacion) ON DELETE CASCADE);


CREATE PROCEDURE DeleteFromTrigger(DNI_deleted INTEGER)
BEGIN
    DECLARE aux INT DEFAULT 0;
        SELECT nro_evaluacion FROM Cursa WHERE DNI = DNI_deleted LIMIT 1,1 INTO aux;
        DELETE FROM Evaluacion
        WHERE nro_evaluacion = aux;
END$$


CREATE TRIGGER DeleteEvaluacion AFTER DELETE on Alumno
FOR EACH ROW
BEGIN
CALL DeleteFromTrigger(old.DNI);
DELETE FROM Cursa
    WHERE old.DNI = DNI;
END$$

