# Semana 02 - Capas y Persistencia con Spring Data JPA

Aplicación **Spring Boot** con arquitectura por capas que lista usuarios y productos guardados en **MySQL**, mostrándolos en vistas **Thymeleaf**.

## Tecnologías

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Thymeleaf
- MySQL
- Maven

## Arquitectura

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
MySQL
```

Los servicios se inyectan por constructor en los controladores.

## Rutas

| Método | Ruta | Vista |
|---|---|---|
| GET | `/productos` | `productos.html` |
| GET | `/usuarios/usuarios-view` | `usuarios.html` |

## Base de datos

Tablas `usuario` (id, nombre, email) y `producto` (id, nombre, precio), creadas por Hibernate (`ddl-auto=update`).

La contraseña se lee de una variable de entorno:

```bash
export DB_PASSWORD=<tu_contraseña>
```

## Ejecución

```bash
cd demo01
./mvnw spring-boot:run
```
