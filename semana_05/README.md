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