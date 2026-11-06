-- Evaluación 03 - Gestión de pagos de servicios
-- Perfil prod: se ejecuta en el servidor de producción, no en la laptop.
-- En prod Hibernate no crea tablas (ddl-auto: validate), por eso van aquí.

CREATE DATABASE IF NOT EXISTS bd_cliente;
CREATE DATABASE IF NOT EXISTS bd_servicio;
CREATE DATABASE IF NOT EXISTS bd_recibo;

CREATE TABLE IF NOT EXISTS bd_cliente.cliente (
    id_cliente INT NOT NULL AUTO_INCREMENT,
    dni        VARCHAR(8)   NOT NULL,
    nombres    VARCHAR(100) NOT NULL,
    apellidos  VARCHAR(100) NOT NULL,
    correo     VARCHAR(100),
    telefono   VARCHAR(15),
    direccion  VARCHAR(200),
    estado     CHAR(1) DEFAULT 'A',
    PRIMARY KEY (id_cliente),
    UNIQUE KEY uk_cliente_dni (dni)
);

CREATE TABLE IF NOT EXISTS bd_servicio.servicio (
    id_servicio INT NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(60)  NOT NULL,
    empresa     VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200),
    estado      CHAR(1) DEFAULT 'A',
    PRIMARY KEY (id_servicio)
);

-- id_cliente e id_servicio no son llaves foráneas: esos datos viven en otras bases.
-- recibo-service los valida por HTTP antes de guardar.
CREATE TABLE IF NOT EXISTS bd_recibo.recibo (
    id_recibo         INT NOT NULL AUTO_INCREMENT,
    id_cliente        INT NOT NULL,
    id_servicio       INT NOT NULL,
    periodo           VARCHAR(7)    NOT NULL,
    monto             DECIMAL(10,2) NOT NULL,
    fecha_vencimiento DATE          NOT NULL,
    estado_pago       ENUM('PAGADO','PENDIENTE') NOT NULL,
    PRIMARY KEY (id_recibo)
);
