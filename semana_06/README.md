# Semana 06 - Auditoría con Spring AOP

Laboratorio desarrollado para implementar **Programación Orientada a Aspectos (AOP)** en una API REST con Spring Boot.

Se amplió el proyecto de productos de la semana anterior incorporando auditoría de operaciones, logging y manejo de errores sin mezclar esta lógica transversal directamente con la lógica principal de `ProductoService`.

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

Registra las operaciones realizadas correctamente sobre los productos.

Las acciones auditadas son:

| Operación | Acción | Información registrada |
|---|---|---|
| Crear producto | `CREAR` | Registro de un nuevo producto |
| Listar productos | `LISTAR` | Cantidad de productos obtenidos |
| Actualizar producto | `ACTUALIZAR` | ID del producto actualizado |
| Eliminar producto | `ELIMINAR` | Eliminación del producto |

## Información dinámica con JoinPoint

Para evitar registros de auditoría con información fija, se utiliza `JoinPoint` para acceder a los parámetros recibidos por los métodos interceptados.

En la actualización se obtiene dinámicamente el ID:

```java
Long id = (Long) joinPoint.getArgs()[0];
```

Esto permite generar registros como:

```text
Se actualizó producto con ID: 5
```

Para el listado se captura el resultado retornado:

```java
@AfterReturning(
    pointcut = "execution(* com.edu.tecsup.demo01.service.ProductoService.listar(..))",
    returning = "resultado"
)
```

La cantidad se obtiene mediante:

```java
resultado.size()
```

generando registros como:

```text
Se obtuvieron 5 productos
```

## Persistencia de auditoría

Los eventos son almacenados en la entidad `AuditoriaLog`.

Cada registro contiene:

- Acción realizada.
- Método interceptado.
- Fecha y hora.
- Detalle de la operación.

Los registros son almacenados en:

```text
auditoria_log
```

Ejemplo:

```text
ACTUALIZAR | actualizar | Se actualizó producto con ID: 5
LISTAR     | listar      | Se obtuvieron 5 productos
```

## Manejo de errores con AOP

Se implementó `ErrorAspect` utilizando `@AfterThrowing`.

Cuando ocurre una excepción dentro de `ProductoService`, el aspecto captura el error y lo registra mediante `AuditoriaService`.

Por ejemplo, al intentar eliminar un producto inexistente:

```http
DELETE /api/productos/9999
```

se genera el mensaje:

```text
Producto con ID 9999 no existe
```

y el evento puede almacenarse en `auditoria_log` con la acción:

```text
ERROR
```

De esta manera, los errores no solamente son mostrados en consola, sino que también pueden quedar registrados en la base de datos.

## Endpoints probados

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

## Validaciones

El proyecto conserva las validaciones implementadas mediante `ProductoDTO` y Jakarta Bean Validation.

Entre las principales validaciones se encuentran:

- `@NotBlank` para campos obligatorios.
- `@Positive` para valores mayores a cero.
- `@Min` para establecer valores mínimos.
- `@Valid` para ejecutar las validaciones desde el Controller.

## Pruebas realizadas

Se realizaron pruebas funcionales utilizando Postman para verificar:

- Registro de productos mediante POST.
- Actualización mediante PUT.
- Consulta mediante GET.
- Eliminación mediante DELETE.
- Manejo de productos inexistentes.
- Registro automático de las operaciones en `auditoria_log`.
- Registro de errores mediante AOP.
- Ejecución de los aspectos mediante los logs de la aplicación.

## Configuración local

La configuración de la base de datos se mantiene separada utilizando el perfil local:

```text
application.properties
application-local.properties
```

El archivo `application-local.properties` contiene la configuración específica del entorno local y no debe versionarse en el repositorio.

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

La implementación permite mantener separada la lógica principal de productos de funcionalidades transversales como auditoría, logging y manejo de errores.

Spring AOP permite interceptar las operaciones realizadas en la capa de servicio y registrar información dinámica sin agregar directamente esta lógica dentro de cada operación del servicio.

## Autor

**Victor Manuel Santamaria Fabian**  

**Diego Daniel Panez Rondinel** 

Diseño y Desarrollo de Software - Tecsup