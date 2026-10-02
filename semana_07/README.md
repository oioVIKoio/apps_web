# Semana 07 - Seguridad Básica con Spring Security

API REST desarrollada con **Spring Boot**, **Spring Security** y **PostgreSQL** para implementar autenticación HTTP Basic y autorización basada en roles.

El laboratorio implementa usuarios almacenados en base de datos, contraseñas protegidas con BCrypt y diferentes niveles de acceso para los roles `USER`, `ADMIN` y `MANAGER`.

## Tecnologías

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- BCrypt
- Maven
- Postman

## Arquitectura

El proyecto mantiene una estructura por capas y agrega Spring Security al flujo de las peticiones:

```text
Cliente / Postman
       ↓
Spring Security
       ↓
SecurityConfig
       ↓
UserDetailsServiceImpl
       ↓
UserRepository
       ↓
PostgreSQL
       ↓
Controller autorizado
```

La autenticación utiliza los usuarios almacenados en PostgreSQL, mientras que la autorización determina qué endpoints puede utilizar cada rol.

## Modelo de seguridad

Se implementaron dos entidades principales:

### User

Representa las cuentas utilizadas para autenticarse.

Contiene:

- ID
- Username
- Password
- Roles

### Role

Representa los permisos asignados a los usuarios.

Los roles utilizados son:

```text
ROLE_USER
ROLE_ADMIN
ROLE_MANAGER
```

La relación entre usuarios y roles se implementa mediante una relación `ManyToMany` y la tabla intermedia:

```text
user_roles
```

## Autenticación

La aplicación utiliza **HTTP Basic Authentication**.

Spring Security recibe las credenciales y utiliza `UserDetailsServiceImpl` para buscar el usuario registrado en PostgreSQL.

```text
HTTP Basic
    ↓
username + password
    ↓
UserDetailsServiceImpl
    ↓
UserRepository
    ↓
User + Roles
    ↓
Spring Security
```

Las contraseñas se almacenan codificadas mediante:

```java
BCryptPasswordEncoder
```

De esta manera, las contraseñas originales no se almacenan directamente en la base de datos.

## Autorización por roles

`SecurityConfig` define los permisos de acceso para cada grupo de endpoints.

| Método | Endpoint | Acceso |
|---|---|---|
| GET | `/api/free` | Público |
| GET | `/client/home` | `ROLE_USER` o `ROLE_ADMIN` |
| GET | `/management/dashboard` | `ROLE_ADMIN` |
| GET | `/manager/reportes` | `ROLE_MANAGER` |

Esto permite separar los recursos disponibles según el rol del usuario autenticado.

## Usuarios iniciales

`DataLoader` inicializa los roles y usuarios necesarios para realizar las pruebas:

| Usuario | Rol |
|---|---|
| `user` | `ROLE_USER` |
| `admin` | `ROLE_ADMIN` |
| `manager` | `ROLE_MANAGER` |

Las contraseñas son procesadas mediante BCrypt antes de almacenarse.

## Configuración

La configuración general se encuentra en:

```text
src/main/resources/application.properties
```

Las credenciales pueden proporcionarse mediante variables de entorno:

| Variable | Descripción |
|---|---|
| `DB_URL` | URL JDBC de PostgreSQL |
| `DB_USERNAME` | Usuario de PostgreSQL |
| `DB_PASSWORD` | Contraseña de PostgreSQL |
| `SEED_USER_PASSWORD` | Contraseña inicial de `user` |
| `SEED_ADMIN_PASSWORD` | Contraseña inicial de `admin` |
| `SEED_MANAGER_PASSWORD` | Contraseña inicial de `manager` |

Ejemplo:

```bash
export DB_URL='jdbc:postgresql://localhost:5432/demo01'
export DB_USERNAME='postgres'
export DB_PASSWORD='tu-clave'

export SEED_USER_PASSWORD='clave-user'
export SEED_ADMIN_PASSWORD='clave-admin'
export SEED_MANAGER_PASSWORD='clave-manager'

./mvnw spring-boot:run
```

> Las credenciales reales no deben almacenarse directamente en el repositorio.

### Perfil local

Para desarrollo local se utiliza:

```text
application-local.properties
```

Este archivo contiene la configuración específica del entorno local y se mantiene fuera del repositorio mediante `.gitignore`.

## Pruebas con Postman

Las pruebas manuales se realizaron utilizando **Basic Auth**.

### Endpoint público

```http
GET /api/free
```

No requiere autenticación.

Resultado esperado:

```text
200 OK
```

### Acceso USER

```http
GET /client/home
```

Autenticación:

```text
Username: user
Password: <contraseña configurada>
```

Resultado esperado:

```text
200 OK
```

### Acceso ADMIN

```http
GET /management/dashboard
```

Autenticación:

```text
Username: admin
Password: <contraseña configurada>
```

Resultado esperado:

```text
200 OK
```

### Acceso MANAGER

```http
GET /manager/reportes
```

Autenticación:

```text
Username: manager
Password: <contraseña configurada>
```

Resultado esperado:

```text
200 OK
```

## Validación de permisos

Además de comprobar los accesos permitidos, se verificaron escenarios de acceso incorrecto.

Una petición a un recurso protegido sin autenticación produce:

```text
401 Unauthorized
```

Mientras que un usuario autenticado que intenta acceder a un recurso para el cual no posee el rol requerido obtiene:

```text
403 Forbidden
```

Esto permite diferenciar entre **autenticación** y **autorización**:

```text
Autenticación
→ verifica quién es el usuario

Autorización
→ verifica qué puede hacer ese usuario
```

## Pruebas automatizadas

Las pruebas pueden ejecutarse mediante:

```bash
./mvnw test
```

El entorno de pruebas utiliza una base de datos H2 en memoria, evitando depender de PostgreSQL durante la ejecución de los tests.

## Ejecución

Para iniciar la aplicación:

```bash
./mvnw spring-boot:run
```

Por defecto, la API estará disponible en:

```text
http://localhost:8080
```

## Resultado

Se implementó seguridad básica en una API REST utilizando Spring Security.

La aplicación permite:

- Autenticar usuarios almacenados en PostgreSQL.
- Proteger contraseñas mediante BCrypt.
- Asignar roles a los usuarios.
- Restringir endpoints según el rol.
- Mantener un endpoint de acceso público.
- Diferenciar respuestas `401 Unauthorized` y `403 Forbidden`.
- Configurar credenciales sin almacenarlas directamente en el repositorio.

## Autores

**Victor Manuel Santamaria Fabian**  
**Diego Daniel Panez Rondinel**

Diseño y Desarrollo de Software - Tecsup