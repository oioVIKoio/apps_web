# Semana 05 - DTO, Validaciones y Búsquedas con Spring Boot

API REST desarrollada con **Spring Boot**, **Spring Data JPA** y **Hibernate** para aplicar DTO, validación de datos y consultas personalizadas sobre productos.

## Tecnologías

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Jakarta Bean Validation
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

Además, se utiliza un DTO para controlar y validar los datos recibidos por la API.

```text
JSON
  ↓
Controller
  ↓
@Valid
  ↓
ProductoDTO
  ↓
Service
  ↓
Repository
  ↓
Base de datos
```

## DTO y validaciones

`ProductoDTO` permite definir los datos recibidos y aplicar reglas de validación mediante Jakarta Bean Validation.

Validaciones utilizadas:

- `@NotBlank` para validar campos de texto obligatorios.
- `@Positive` para valores que deben ser mayores a cero.
- `@Min` para establecer un valor mínimo.
- `@Valid` para ejecutar las validaciones desde el Controller.

## Búsqueda por nombre

Se implementó una búsqueda parcial de productos utilizando un método derivado de Spring Data JPA:

```java
List<Producto> findByNombreContainingIgnoreCase(String nombre);
```

La búsqueda:

- Permite coincidencias parciales mediante `Containing`.
- Ignora mayúsculas y minúsculas mediante `IgnoreCase`.

Ejemplo:

```http
GET /api/productos/buscar?nombre=laptop
```

Puede encontrar productos como:

```text
Laptop ASUS TUF
Laptop Lenovo IdeaPad
Soporte para Laptop
```

## Preguntas de reflexión

### 1. ¿Por qué usamos un DTO en lugar de exponer directamente la entidad?

Un DTO (Data Transfer Object) permite separar los datos que recibe o envía la API de la entidad utilizada para la persistencia. Esto permite controlar los atributos que serán recibidos y aplicar validaciones sin trabajar directamente sobre la entidad.

En este proyecto, `ProductoDTO` recibe los datos enviados por el cliente y aplica las validaciones correspondientes antes de convertirlos en un objeto `Producto`.

### 2. ¿Qué función cumple `@Valid`?

`@Valid` indica a Spring que debe ejecutar las validaciones definidas en el DTO antes de continuar con el método del controlador.

En `ProductoDTO` se utilizan:

- `@NotBlank` para evitar nombres vacíos.
- `@Positive` para exigir un precio mayor a cero.
- `@Min` para evitar un stock negativo.

Si alguna validación falla, la solicitud es rechazada antes de guardar la información.

### 3. ¿Qué pasaría si eliminamos `@RestControllerAdvice`?

Sin `@RestControllerAdvice`, dejaríamos de utilizar el manejador global personalizado de excepciones.

En este proyecto, `GlobalExceptionHandler` captura los errores producidos por las validaciones y devuelve una respuesta `400 Bad Request` con los campos y mensajes correspondientes.

Spring podría seguir gestionando el error mediante su comportamiento predeterminado, pero perderíamos el formato personalizado implementado en la API.

### 4. ¿Qué hace `@Autowired`?

`@Autowired` permite realizar inyección de dependencias automáticamente mediante Spring.

En el proyecto se utiliza principalmente en el siguiente flujo:

```text
ProductoController
        ↓
ProductoService
        ↓
ProductoRepository

## Endpoints

### Productos

```http
GET    /api/productos
GET    /api/productos/{id}
POST   /api/productos
DELETE /api/productos/{id}
```

### Búsqueda

```http
GET /api/productos/buscar?nombre={nombre}
```

## Configuración local

La configuración de conexión a la base de datos se mantiene separada mediante un perfil local.

```text
application.properties
application-local.properties
```

`application-local.properties` contiene la configuración propia del entorno y no se versiona en el repositorio.

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
Diseño y Desarrollo de Software - Tecsup