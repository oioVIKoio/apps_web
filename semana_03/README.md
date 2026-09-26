# Semana 03 - API REST con Spring Boot y JPA

API REST desarrollada con **Spring Boot** y **Spring Data JPA** para gestionar las entidades principales de un sistema de ventas.

## Tecnologías

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Maven

## Arquitectura

El proyecto utiliza una arquitectura por capas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
Base de datos
```

## Entidades

El sistema está compuesto por:

- Categoria
- Producto
- Cliente
- Empleado
- Venta
- Detalle

## Relaciones

```text
Categoria 1 ───── N Producto

Cliente   1 ───── N Venta

Empleado  1 ───── N Venta

Venta     1 ───── N Detalle

Producto  1 ───── N Detalle
```

Las relaciones se implementan mediante `@OneToMany`, `@ManyToOne` y `@JoinColumn`.

## Endpoints

La API proporciona operaciones CRUD para los principales recursos:

```text
/api/categorias
/api/productos
/api/clientes
/api/empleados
/api/ventas
/api/detalles
```

Las operaciones disponibles incluyen:

```http
GET    /api/{recurso}
GET    /api/{recurso}/{id}
POST   /api/{recurso}
PUT    /api/{recurso}/{id}
DELETE /api/{recurso}/{id}
```

Se utiliza `ResponseEntity` para controlar las respuestas HTTP de las operaciones.

## Estructura

```text
src/main/java/com/tecsup/
├── controller/
├── model/
├── repository/
├── service/
└── Demo01Application.java
```

## Ejecución

```bash
./mvnw spring-boot:run
```

La API estará disponible por defecto en:

```text
http://localhost:8080
```

## Autores

**Victor Manuel Santamaria Fabian**

**Diego Daniel Panez Rondinel**

Diseño y Desarrollo de Software - Tecsup