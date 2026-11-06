-- Evaluación 03 - Gestión de pagos de servicios
-- Una base de datos por microservicio (MariaDB, puerto 3307).
-- Para arrancar no hace falta: en dev la base se crea sola (createDatabaseIfNotExist=true)
-- y Hibernate crea las tablas (ddl-auto: update). Los datos se mantienen al reiniciar.
-- Este script vacía las bases dev para empezar de cero. Correrlo con los servicios apagados.
DROP DATABASE IF EXISTS bd_cliente_dev;
DROP DATABASE IF EXISTS bd_servicio_dev;
DROP DATABASE IF EXISTS bd_recibo_dev;
CREATE DATABASE bd_cliente_dev;
CREATE DATABASE bd_servicio_dev;
CREATE DATABASE bd_recibo_dev;
