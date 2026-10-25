# Semana 08 - Pruebas unitarias e integración con Spring Boot

API REST de **Productos** y **Categorías** desarrollada con **Spring Boot**, **Spring Data JPA** y **Hibernate**, con pruebas unitarias (**JUnit 5** + **Mockito**) y de integración (**MockMvc**).

Parte del proyecto de la [semana 05](../semana_05/) (DTO, validaciones y búsquedas).

## Tecnologías

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Jakarta Bean Validation
- MariaDB (desarrollo)
- H2 (solo pruebas)
- JUnit 5, Mockito y MockMvc
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
MariaDB
```

## Pruebas

| Clase | Tipo | Qué prueba | Tests |
|---|---|---|---|
| `ProductoServiceTest` | Unitaria (Mockito) | listar, guardar, obtener, eliminar, actualizar, actualizar y obtener sin producto | 7 |
| `ProductoControllerTest` | Integración (MockMvc) | POST, GET, validación, PUT y DELETE (con y sin producto) | 7 |
| `CategoriaServiceTest` | Unitaria (Mockito) | CRUD y excepción cuando la categoría no existe | 7 |
| `CategoriaControllerTest` | Integración (MockMvc) | CRUD, validación y respuestas 404 | 8 |
| `Demo01ApplicationTests` | Contexto | La aplicación levanta | 1 |

Las pruebas de integración usan **H2 en memoria** con el perfil `test` (`src/test/resources/application-test.properties`), así no escriben datos en la base de desarrollo.

```bash
./mvnw test
```

```text
Tests run: 30, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Módulo Categoría

| Capa | Archivo |
|---|---|
| Entity | `model/Categoria.java` |
| Repository | `repository/CategoriaRepository.java` |
| DTO | `dto/CategoriaDTO.java` (`@NotBlank` y `@Size`) |
| Service | `service/CategoriaService.java` |
| Controller | `controller/CategoriaController.java` |
| Excepciones | `exception/RecursoNoEncontradoException.java` y `GlobalExceptionHandler` |

Si la categoría no existe, el servicio lanza `RecursoNoEncontradoException` y el `@RestControllerAdvice` responde `404` con el mensaje en JSON.

```json
{ "error": "Categoría no encontrada: 1" }
```

## Endpoints

### Productos

```http
GET    /api/productos
GET    /api/productos/{id}
GET    /api/productos/buscar?nombre={nombre}
POST   /api/productos
PUT    /api/productos/{id}
DELETE /api/productos/{id}
```

### Categorías

```http
GET    /api/categorias
GET    /api/categorias/{id}
POST   /api/categorias
PUT    /api/categorias/{id}
DELETE /api/categorias/{id}
```

## Configuración local

```text
application.properties
application-local.properties
```

`application-local.properties` contiene la conexión a la base de datos local y no se versiona en el repositorio.

## Ejecución

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible por defecto en:

```text
http://localhost:8080
```

## Autor

**Victor Manuel Santamaria Fabian**

**Diego Daniel Panez Rondinel**

Diseño y Desarrollo de Software - Tecsup
