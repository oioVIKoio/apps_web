# Semana 11 - Creación de microservicios

Una aplicación dividida en servicios independientes: cada uno es su propio proyecto Spring Boot, con su puerto y su base de datos, y se comunican por HTTP.

| Carpeta | Qué tiene |
|---|---|
| [`microservicios/`](./microservicios/) | Procedimiento de la guía (tienda): categoría, cliente, producto y pedido |
| [`clinica/`](./clinica/) | Actividad (clínica Salud Integral): paciente, médico y citas |
| [`postman/`](./postman/) | Una colección para cada parte |

## Tecnologías

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Jakarta Bean Validation
- RestClient
- MariaDB
- Maven
- Postman

## Servicios

| Servicio | Puerto | Base de datos | Ruta |
|---|---|---|---|
| `cliente-service` | 8081 | `bd_cliente` | `/api/clientes` |
| `categoria-service` | 8082 (perfil `dev`) | `bd_categoria_dev` | `/api/categorias` |
| `producto-service` | 8083 | `bd_producto` | `/api/productos` |
| `pedido-service` | 8084 | `bd_pedido` | `/api/pedidos` |
| `paciente-service` | 8085 | `bd_paciente` | `/api/pacientes` |
| `medico-service` | 8086 | `bd_medico` | `/api/medicos`, `/api/especialidades` |
| `cita-service` | 8087 | `bd_cita` | `/api/citas` |

## Cómo ejecutarlo

1. Crear las bases de datos en MariaDB (puerto 3307):

```sql
CREATE DATABASE bd_categoria;
CREATE DATABASE bd_categoria_dev;
CREATE DATABASE bd_cliente;
CREATE DATABASE bd_producto;
CREATE DATABASE bd_pedido;
CREATE DATABASE bd_paciente;
CREATE DATABASE bd_medico;
CREATE DATABASE bd_cita;
```

2. Definir la contraseña local y levantar cada servicio en su carpeta:

```bash
export DB_PASSWORD='tu-clave'
./mvnw spring-boot:run
```

En IntelliJ, `DB_PASSWORD` va en **Run → Edit Configurations → Environment variables**. El usuario es `root`, salvo que se defina `DB_USER`.

## Comunicación entre servicios

`cita-service` guarda `id_paciente` e `id_medico` sin llave foránea, porque esos datos son de otros servicios. Antes de registrar una cita los consulta por HTTP con `PacienteClient` y `MedicoClient`:

```text
POST /api/citas
  ├─ GET paciente-service/api/pacientes/{id}
  ├─ GET medico-service/api/medicos/{id}
  └─ guarda en bd_cita
```

| Caso | Respuesta |
|---|---|
| Datos correctos | `201` |
| Datos inválidos, paciente o médico inactivo | `400` |
| Paciente o médico que no existe | `404` |
| `paciente-service` o `medico-service` apagado | `500` · `No se pudo comunicar con paciente-service` |

Con `paciente-service` apagado, las citas no se pueden registrar, pero se siguen listando y `medico-service` sigue respondiendo.

## Endpoints de la clínica

```http
POST   /api/pacientes
GET    /api/pacientes
GET    /api/pacientes/{id}
PUT    /api/pacientes/{id}
DELETE /api/pacientes/{id}

POST   /api/especialidades
GET    /api/especialidades

POST   /api/medicos
GET    /api/medicos
GET    /api/medicos/{id}
PUT    /api/medicos/{id}
DELETE /api/medicos/{id}

POST   /api/citas
GET    /api/citas
GET    /api/citas/{id}
PUT    /api/citas/{id}/estado
PUT    /api/citas/{id}/cancelar
```

Eliminar un paciente o un médico cambia su `estado` de `A` a `I`, y deja de salir en la lista. Así sus citas no quedan apuntando a un registro borrado. Cancelar una cita cambia su estado a `CANCELADA`.

## Pruebas en Postman

Con los servicios levantados y **las bases de datos vacías**, importar las colecciones y correrlas en orden (**Run collection**). Cada POST guarda el `id` creado en una variable.

| Colección | Peticiones | Validaciones |
|---|---|---|
| [`Lab_S11_Tienda`](./postman/Lab_S11_Tienda.postman_collection.json) | 30 | 32 |
| [`Lab_S11_Clinica`](./postman/Lab_S11_Clinica.postman_collection.json) | 35 | 40 |

## Cambios respecto a la guía

| Cambio | Por qué |
|---|---|
| Java 21 y Spring Boot 4.1.1 | La guía pide Java 25 y 4.1.0; son las versiones del resto del curso |
| MariaDB con `${DB_PASSWORD}` | Es la base de datos del curso, y la contraseña no se sube al repositorio |
| `categoria-service` y `application-dev.yml` | La guía usa tilde y mayúscula; en Linux, `Application-dev.yml` no se carga |
| `@JsonIgnore` en `DetallePedido.pedido` | Con el código de la guía, el POST responde 201 con un JSON roto de 11 KB (pedido → detalle → pedido, hasta 500 niveles) |
| Pedido con DTO y `ResourceNotFoundException` | Con `RuntimeException`, un pedido inexistente respondería 500 en lugar de 404 |
| `PUT` de pedido también reemplaza los detalles | En la guía, los detalles enviados se ignoran |
| Sin llaves foráneas entre servicios | La guía las pone en `cita`, pero paciente y médico viven en otras bases de datos |
| `RestClient` en `cita-service` (extra) | Canvas pide comunicación entre servicios y la guía no trae el código; Feign y Eureka son de la semana 12 |

## Lo que no incluye

- Endpoints de `horario_medico` y `atencion`: solo tienen entidad y tabla, porque la guía no les pide funciones.
- Reglas entre estados de una cita ni control de cruces de horario.

## Perfil prod

`application-prod.yml` apunta al host `servidor` de la guía, que no existe en local: la aplicación no arranca (`UnknownHostException: servidor`). Para probarlo se cambia la URL con una variable de entorno, sin tocar el archivo:

```bash
export DB_PASSWORD='tu-clave'
export SPRING_PROFILES_ACTIVE=prod
export SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3307/bd_categoria
./mvnw spring-boot:run
```

Levanta en el puerto `9092` y usa `bd_categoria`.

## Autor

**Victor Manuel Santamaria Fabian**

**Diego Daniel Panez Rondinel**

Diseño y Desarrollo de Software - Tecsup
