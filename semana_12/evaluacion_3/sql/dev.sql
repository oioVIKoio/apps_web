-- Evaluación 03 - Gestión de pagos de servicios
-- Una base de datos por microservicio (MariaDB, puerto 3307).
-- Perfil dev: Hibernate crea las tablas al arrancar (ddl-auto: update).
-- Las DROP dejan las bases vacías para correr la colección de Postman desde cero.
DROP DATABASE IF EXISTS bd_cliente_dev;
DROP DATABASE IF EXISTS bd_servicio_dev;
DROP DATABASE IF EXISTS bd_recibo_dev;
CREATE DATABASE bd_cliente_dev;
CREATE DATABASE bd_servicio_dev;
CREATE DATABASE bd_recibo_dev;
