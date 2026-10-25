# Semana 09 - Autenticación JWT con Spring Security

API REST con **Spring Boot**, **Spring Security** y **JSON Web Token (JWT)**: registro, inicio de sesión que devuelve un token y rutas protegidas por rol (`USER`, `ADMIN` y `MODERATOR`), sin sesiones (*stateless*).

Proyecto: [`demo_Security_JWT`](./demo_Security_JWT/)

## Tecnologías

- Java 21
- Spring Boot 4.1.1
- Spring Security
- Spring Data JPA
- Jakarta Bean Validation
- JJWT 0.11.5
- MariaDB (desarrollo)
- H2 (solo pruebas)
- Maven
- Postman

## Estructura

```text
com.tecsup
├── config        SecurityConfig
├── controller    AuthController, TestController
├── dto           JwtResponse, LoginRequest, MessageResponse, SignupRequest
├── entity        Role, User
├── exception     GlobalExceptionHandler
├── jwt           JwtAuthFilter, JwtUtils
├── repository    RoleRepository, UserRepository
└── service       UserDetailsImpl, UserDetailsServiceImpl
```

## Flujo

```text
Postman → Authorization: Bearer <token>
        → JwtAuthFilter (lee el token)
        → JwtUtils (valida la firma y la expiración, obtiene el email)
        → UserDetailsServiceImpl (carga el usuario y sus roles)
        → SecurityContextHolder (guarda la autenticación)
        → SecurityConfig (revisa el rol de la ruta)
        → Controller
```

## Endpoints

| Método | Ruta | Acceso |
|---|---|---|
| POST | `/api/auth/signup` | Público |
| POST | `/api/auth/signin` | Público |
| GET | `/api/test/all` | Público |
| GET | `/api/test/user` | `USER` o `ADMIN` |
| GET | `/api/test/admin` | `ADMIN` |
| GET | `/api/test/mod` | `MODERATOR` (la ruta está protegida, pero el controller no la implementa) |

## Configuración

1. Crear la base de datos:

```sql
CREATE DATABASE security_db;
```

2. Copiar `src/main/resources/application-local.properties.example` como `application-local.properties` y poner los datos de la BD y una clave `jwt.secret` de 32 caracteres o más. Ese archivo no se sube al repositorio.

3. Levantar la aplicación (Hibernate crea las tablas `roles`, `users` y `user_roles`):

```bash
./mvnw spring-boot:run
```

4. Insertar los roles **antes de registrar usuarios** (el signup los busca por nombre y, si no existen, falla con `Rol ... no encontrado`):

```sql
INSERT INTO roles(name) VALUES ('USER'), ('ADMIN'), ('MODERATOR');
```

## Pruebas en Postman

Importar [`postman/Lab_S09_JWT.postman_collection.json`](./demo_Security_JWT/postman/Lab_S09_JWT.postman_collection.json) (**Import** → arrastrar el archivo) y correrla en orden (**Run collection**). Los *signin* guardan el token en las variables `tokenAdmin` y `tokenUser`, y las rutas protegidas lo usan solo.

| Petición | Resultado esperado |
|---|---|
| Signup admin / Signup user | `201` · `Usuario registrado correctamente` |
| Signup repetido | `400` · `Error: Username ya existe` |
| Signup con datos inválidos | `400` · mensaje por cada campo |
| Signin admin / Signin user | `200` · `accessToken`, `tokenType: Bearer` y roles |
| Signin con contraseña incorrecta | `401` · `Error: Credenciales incorrectas` |
| Caso 1: `/api/test/all` sin token | `200` · `Public Content` |
| Caso 2: `/api/test/user` sin token | `401` |
| Caso 3: `/api/test/user` con token modificado | `401` |
| Caso 4: `/api/test/user` con token de user | `200` · `User Content` |
| Caso 5: `/api/test/admin` con token de user | `403` |
| Caso 6: `/api/test/admin` con token de admin | `200` · `Admin Content` |
| Ruta que no existe | `404` |

Para probar a mano: en **Authorization** elegir **Bearer Token** y pegar el `accessToken` del signin.

## Pruebas automáticas

`AuthJwtTest` (MockMvc + H2) repite el registro, el login y los 6 casos:

```bash
./mvnw test
```

```text
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Cambios respecto a la guía

| Cambio | Por qué |
|---|---|
| `/api/test/all` en `permitAll()` | La actividad la trata como pública, pero el `SecurityConfig` de la guía solo libera `/api/auth/**` |
| `authenticationEntryPoint` con `401` | Sin `httpBasic` ni `formLogin`, Spring Security responde `403` cuando no hay token |
| `GlobalExceptionHandler` | `400` con los errores de `@Valid` y `401` con credenciales incorrectas |
| `@Valid` en `SignupRequest` y `LoginRequest` | Lo pide la actividad en Canvas |
| Signup responde `201 Created` | Es lo correcto al crear un recurso |
| `jwt.secret` y la BD en `application-local.properties` | El repositorio es público: la clave que firma los tokens no se sube |
| Driver de MariaDB | El conector de MySQL 9 no funciona con MariaDB (consulta una tabla que solo existe en MySQL) |
| Boot 4.1.1 y Java 21 | Las mismas versiones del resto del curso (la guía usa 4.0.6 y Java 17) |

## Autor

**Victor Manuel Santamaria Fabian**

**Diego Daniel Panez Rondinel**

Diseño y Desarrollo de Software - Tecsup
