# Semana 10 - Configuración de ambientes con Spring Profiles

Configuración de varios ambientes (desarrollo, calidad, pruebas y producción) con **Spring Profiles** y **variables de entorno**: cada ambiente usa su propio puerto y su propia base de datos sin cambiar el código.

| Proyecto | Qué tiene |
|---|---|
| [`demo01`](./demo01/) | Procedimiento de la guía sobre la API de Productos de la [semana 05](../semana_05/): perfiles `dev`, `qa` y `prod` y `GET /config` |
| [`empleados`](./empleados/) | Actividad (TechSolutions SAC): CRUD de empleados, perfiles `dev`, `qa`, `test` y `prod` y `GET /configuracion` |

## Tecnologías

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Jakarta Bean Validation
- MariaDB
- H2 (solo pruebas)
- Maven
- Postman

## Perfiles

Cada perfil tiene su archivo `application-<perfil>.properties`. El perfil activo se elige con la variable `PROFILE_ACTIVE` (por defecto `dev`):

```properties
# application.properties
spring.profiles.active=${PROFILE_ACTIVE:dev}
```

```properties
# application-dev.properties
server.port=${SERVER_PORT:8080}
empresa.ambiente=DESARROLLO
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

### demo01

| Perfil | Puerto | Base de datos | `empresa.ambiente` | `ddl-auto` |
|---|---|---|---|---|
| `dev` | 8080 | `productos_dev` | DESARROLLO | `update` |
| `qa` | 8082 | `productos_qa` | CALIDAD | `update` |
| `prod` | 8083 | `productos_prod` | PRODUCCION | `validate` |

### empleados

| Perfil | Puerto | Base de datos | `empresa.ambiente` | `ddl-auto` |
|---|---|---|---|---|
| `dev` | 8080 | `empleados_dev` | DESARROLLO | `update` |
| `qa` | 8082 | `empleados_qa` | CALIDAD | `update` |
| `test` | 8084 | `empleados_test` | PRUEBAS | `update` |
| `prod` | 8086 | `empleados_prod` | PRODUCCION | `validate` |

En `prod`, `ddl-auto=validate` no crea tablas: si la tabla no existe la aplicación no arranca (`Schema validation: missing table`). Antes de levantar producción se crea la tabla:

```sql
CREATE TABLE empleado(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombres VARCHAR(100),
  apellidos VARCHAR(100),
  email VARCHAR(100),
  sueldo DECIMAL(10,2)
);
```

## Variables de entorno

| Variable | Ejemplo (dev) |
|---|---|
| `PROFILE_ACTIVE` | `dev` |
| `DB_URL` | `jdbc:mariadb://localhost:3307/empleados_dev` |
| `DB_USER` | `root` |
| `DB_PASSWORD` | la contraseña local (no se sube al repositorio) |
| `SERVER_PORT` | `8080` |

En IntelliJ van en **Run → Edit Configurations → Environment variables**, separadas por `;`. Desde la terminal:

```bash
export PROFILE_ACTIVE=qa
export DB_URL=jdbc:mariadb://localhost:3307/empleados_qa
export DB_USER=root
export DB_PASSWORD='tu-clave'
export SERVER_PORT=8082
./mvnw spring-boot:run
```

## Endpoints

### demo01

```http
GET /config              → Ambiente activo: DESARROLLO
GET /api/productos
```

### empleados

```http
POST   /empleados
GET    /empleados
GET    /empleados/{id}
PUT    /empleados/{id}
DELETE /empleados/{id}
GET    /configuracion
```

`/configuracion` lee el ambiente, el perfil y el puerto con `Environment`, y la base de datos con `SELECT DATABASE()` sobre la conexión real:

```json
{
  "ambiente": "DESARROLLO",
  "perfil": "dev",
  "puerto": "8080",
  "baseDatos": "empleados_dev"
}
```

## Pruebas en Postman

Importar [`empleados/postman/Lab_S10_Empleados.postman_collection.json`](./empleados/postman/Lab_S10_Empleados.postman_collection.json) y correrla en orden (**Run collection**). El POST guarda el `id` del empleado creado en la variable `id`. Para otro ambiente se cambia la variable `baseUrl` (8080, 8082, 8084 u 8086).

| Petición | Resultado esperado |
|---|---|
| Configuración del ambiente | `200` con ambiente, perfil, puerto y base de datos |
| Crear empleado | `201` |
| Crear empleado inválido | `400` con el mensaje de cada campo |
| Listar / Obtener / Actualizar / Eliminar | `200` |
| Obtener empleado eliminado | `404` · `Empleado no encontrado` |

## Pruebas automáticas

`EmpleadoControllerTest` (MockMvc + H2, perfil `h2`) prueba el CRUD, la validación, el 404 y `/configuracion`:

```bash
cd empleados
./mvnw test
```

```text
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Cambios respecto a la guía

| Cambio | Por qué |
|---|---|
| Archivos `application-dev.properties` (con guion) | La guía los nombra `application.dev.properties`; con punto, Spring no los carga y la aplicación falla porque no encuentra la URL de la base de datos |
| MariaDB en lugar de MySQL | Es la base de datos que usa el resto del curso |
| La base de datos de `/configuracion` sale de la conexión | Así se verifica a qué base se conectó de verdad, no solo lo que dice la configuración |

## Autor

**Victor Manuel Santamaria Fabian**

**Diego Daniel Panez Rondinel**

Diseño y Desarrollo de Software - Tecsup
