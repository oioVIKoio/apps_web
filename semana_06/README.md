# Semana 06 - Auditoría y Control de Acceso con Spring AOP

Laboratorio desarrollado para implementar **Programación Orientada a Aspectos (AOP)** en una API REST con Spring Boot.

Se amplió la API de productos desarrollada previamente incorporando **auditoría de operaciones, logging, manejo de errores y control de acceso mediante usuarios y roles**, manteniendo estas funcionalidades separadas de la lógica principal de `ProductoService`.

## Tecnologías

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Spring AOP
- AspectJ
- Jakarta Bean Validation
- MariaDB
- Maven
- Postman

## Arquitectura

El proyecto mantiene una arquitectura por capas:

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

A esta estructura se incorpora AOP para interceptar las operaciones realizadas desde la capa de servicio:

```text
ProductoController
        ↓
ProductoService
        ↓
   Spring AOP
   ├── LoggingAspect
   ├── AuditoriaAspect
   └── ErrorAspect
        ↓
AuditoriaService
        ↓
AuditoriaRepository
        ↓
auditoria_log
```

## Spring AOP

Se utilizaron aspectos para separar funcionalidades transversales de la lógica principal de productos.

### LoggingAspect

Intercepta los métodos de la capa `service` para registrar en consola el inicio y finalización de las operaciones.

Ejemplo:

```text
▶ Ejecutando: actualizar
✔ Finalizó: actualizar
```

### AuditoriaAspect

`AuditoriaAspect` intercepta las operaciones realizadas sobre los productos y registra información sobre cada acción.

Las operaciones auditadas son:

| Operación | Acción | Información registrada |
|---|---|---|
| Crear producto | `CREAR` | Registro del producto y usuario |
| Listar productos | `LISTAR` | Cantidad de productos obtenidos |
| Actualizar producto | `ACTUALIZAR` | ID del producto actualizado |
| Eliminar producto | `ELIMINAR` | ID del producto eliminado |
| Error de operación | `ERROR` | Información del error producido |

## Control de acceso por usuarios y roles

Se incorporó un control de acceso mediante los headers HTTP:

```text
Usuario
Rol
```

Los usuarios utilizados para las pruebas son:

| Usuario | Rol |
|---|---|
| Ricardo | `ADMIN` |
| Ana | `USER` |
| Luis | `USER` |

Los permisos implementados son:

| Operación | ADMIN | USER |
|---|:---:|:---:|
| Listar productos | Sí | Sí |
| Crear productos | Sí | Sí |
| Actualizar productos | Sí | No |
| Eliminar productos | Sí | No |

El rol `USER` puede consultar y registrar productos, mientras que las operaciones de actualización y eliminación permanecen restringidas al rol `ADMIN`.

## Validación de acceso

Antes de ejecutar determinadas operaciones, AOP verifica los headers `Usuario` y `Rol`.

Si no se proporcionan los datos requeridos, la API responde:

```text
401 Unauthorized
```

Ejemplo:

```text
Debe enviar Usuario y Rol
```

Si el usuario existe pero no posee los permisos necesarios para realizar una operación, la API responde:

```text
403 Forbidden
```

Ejemplo:

```text
Acceso denegado
```

De esta forma se diferencia entre una solicitud sin identificación válida y una solicitud cuyo usuario no tiene autorización suficiente.

## Información dinámica con JoinPoint

Se utiliza `JoinPoint` para obtener información de los métodos interceptados y evitar registros de auditoría con valores fijos.

### Actualización

Para obtener el ID recibido por el método:

```java
Long id = (Long) joinPoint.getArgs()[0];
```

Esto permite registrar:

```text
Se actualizó producto con ID: 5
```

### Eliminación

El mismo mecanismo permite recuperar el ID del producto eliminado:

```java
Long id = (Long) joinPoint.getArgs()[0];
```

Generando registros como:

```text
Se eliminó producto ID: 5
```

### Listado

Para el listado se captura el resultado retornado mediante `@AfterReturning`:

```java
@AfterReturning(
    pointcut = "execution(* com.edu.tecsup.demo01.service.ProductoService.listar(..))",
    returning = "resultado"
)
```

La cantidad de productos se obtiene mediante:

```java
resultado.size()
```

Esto permite generar dinámicamente:

```text
Se obtuvieron 5 productos
```

## Persistencia de auditoría

Los eventos son almacenados mediante la entidad `AuditoriaLog` en la tabla:

```text
auditoria_log
```

Cada registro puede contener:

- Acción realizada.
- Método interceptado.
- Fecha y hora.
- Detalle de la operación.
- Usuario responsable.

Ejemplos:

```text
CREAR     | guardar    | Se registró un producto          | Ana (USER)
LISTAR    | listar     | Se obtuvieron 5 productos        | Ana (USER)
ACTUALIZAR| actualizar | Se actualizó producto con ID: 5 | Ricardo (ADMIN)
ELIMINAR  | eliminar   | Se eliminó producto ID: 5       | Ricardo (ADMIN)
```

## Manejo de errores con AOP

Se implementó `ErrorAspect` utilizando `@AfterThrowing`.

Cuando ocurre una excepción dentro de `ProductoService`, el aspecto puede interceptar el error y registrarlo mediante `AuditoriaService`.

Por ejemplo, al intentar eliminar un producto inexistente:

```http
DELETE /api/productos/9999
```

se puede producir:

```text
Producto con ID 9999 no existe
```

El evento se registra con la acción:

```text
ERROR
```

De esta manera, los errores pueden conservarse como parte de la trazabilidad de la aplicación y no únicamente mostrarse en consola.

## DTO y validaciones

El proyecto conserva el uso de `ProductoDTO` y Jakarta Bean Validation para validar la información recibida por la API.

Entre las principales anotaciones utilizadas se encuentran:

- `@NotBlank` para campos de texto obligatorios.
- `@Positive` para valores que deben ser mayores a cero.
- `@Min` para establecer valores mínimos.
- `@Valid` para ejecutar las validaciones desde el Controller.

## Endpoints

### Listar productos

```http
GET /api/productos
```

### Buscar productos por nombre

```http
GET /api/productos/buscar?nombre={nombre}
```

### Obtener producto por ID

```http
GET /api/productos/{id}
```

### Crear producto

```http
POST /api/productos
```

Ejemplo:

```json
{
  "nombre": "Monitor Samsung",
  "precio": 850,
  "stock": 10,
  "categoria": "MONITORES"
}
```

### Actualizar producto

```http
PUT /api/productos/{id}
```

### Eliminar producto

```http
DELETE /api/productos/{id}
```

## Pruebas realizadas

Las pruebas funcionales se realizaron utilizando Postman.

Se verificó:

- Registro de productos mediante `POST`.
- Consulta de productos mediante `GET`.
- Actualización mediante `PUT`.
- Eliminación mediante `DELETE`.
- Auditoría automática de las operaciones.
- Registro de información dinámica mediante AOP.
- Registro del usuario responsable de las operaciones.
- Acceso de `USER` al listado de productos.
- Creación de productos mediante `USER`.
- Restricción de operaciones según el rol.
- Respuesta `401 Unauthorized` cuando faltan los headers requeridos.
- Respuesta `403 Forbidden` cuando el rol no tiene permisos.
- Manejo y registro de errores mediante AOP.

## Flujo de una petición con control de acceso

```text
Cliente / Postman
        ↓
Headers: Usuario + Rol
        ↓
ProductoController
        ↓
AuditoriaAspect
        ↓
Validación de usuario y permisos
        ↓
ProductoService
        ↓
ProductoRepository
        ↓
MariaDB
        ↓
AuditoriaAspect
        ↓
AuditoriaService
        ↓
auditoria_log
```

Esto permite mantener separadas tres responsabilidades:

```text
ProductoService
→ lógica de productos

AuditoriaAspect
→ auditoría y control de acceso

AuditoriaService
→ persistencia de los eventos
```

## Configuración local

La configuración de la base de datos se mantiene separada mediante un perfil local:

```text
application.properties
application-local.properties
```

`application-local.properties` contiene la configuración específica del entorno y no debe versionarse en el repositorio.

## Ejecución

Desde el directorio del proyecto:

```bash
./mvnw spring-boot:run
```

La API estará disponible por defecto en:

```text
http://localhost:8080
```

## Resultado

Se implementó una API REST que combina persistencia, validaciones y Programación Orientada a Aspectos.

Spring AOP permitió incorporar **logging, auditoría, manejo de errores y control de acceso** sin agregar directamente estas responsabilidades a la lógica principal de `ProductoService`.

Además, la auditoría fue ampliada para registrar información dinámica como el usuario responsable, la cantidad de productos consultados y los identificadores involucrados en las operaciones.

## Autores

**Victor Manuel Santamaria Fabian**  

**Diego Daniel Panez Rondinel**

Diseño y Desarrollo de Software - Tecsup