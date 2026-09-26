# Laboratorio 04 - Persistencia con Spring Boot y Hibernate

API REST desarrollada con **Spring Boot**, **Spring Data JPA** y **Hibernate** para practicar persistencia de datos y relaciones entre entidades.

## Tecnologías

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MariaDB
- Maven
- Postman

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
MariaDB
```

## Relaciones implementadas

### OneToMany / ManyToOne

Relación entre categorías y productos.

```text
Categoria 1 ─────── N Producto
```

La clave foránea `categoria_id` se almacena en `producto`.

### OneToOne

Relación entre usuarios y perfiles.

```text
Usuario 1 ─────── 1 Perfil
```

Se utiliza `@OneToOne`, `@JoinColumn` y cascada para gestionar ambas entidades.

### ManyToMany

Relación entre estudiantes y cursos.

```text
Estudiante N ─────── N Curso
              │
              ▼
      estudiante_curso
```

La tabla intermedia `estudiante_curso` es gestionada mediante `@JoinTable` y contiene las claves:

- `estudiante_id`
- `curso_id`

También se valida que un estudiante no pueda inscribirse dos veces en el mismo curso.

## Endpoints principales

### Usuarios

```http
POST   /api/usuarios
GET    /api/usuarios
GET    /api/usuarios/{id}
DELETE /api/usuarios/{id}
```

### Cursos

```http
POST   /api/cursos
GET    /api/cursos
```

### Estudiantes

```http
POST   /api/estudiantes
GET    /api/estudiantes
GET    /api/estudiantes/{id}
GET    /api/estudiantes/{id}/cursos
POST   /api/estudiantes/{id}/cursos/{cursoId}
DELETE /api/estudiantes/{id}/cursos/{cursoId}
```

## Configuración

La conexión a la base de datos se configura mediante `application.properties`.

Las credenciales locales no se almacenan en el repositorio.

## Ejecución

```bash
./mvnw spring-boot:run
```

La API estará disponible por defecto en:

```text
http://localhost:8080
```

## Autor

**Victor Manuel Santamaria Fabian**  

**Diego Daniel Panez Rondinel**  

Diseño y Desarrollo de Software - Tecsup