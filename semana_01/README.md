# Semana 01 - Fundamentos de Spring Boot

Primer proyecto con **Spring Boot**: controladores REST que devuelven JSON y un controlador MVC que muestra datos en una vista con **Thymeleaf**.

## Tecnologías

- Java 21
- Spring Boot
- Spring Web MVC
- Thymeleaf
- Maven

## Endpoints

| Método | Ruta | Tipo | Respuesta |
|---|---|---|---|
| GET | `/informacion` | REST | JSON con el nombre |
| GET | `/saludo` | REST | Texto plano |
| GET | `/api/persona` | REST | JSON con la información personal |
| GET | `/persona` | MVC | Vista `informacion-personal.html` |

## Estructura

```text
demo01/src/main/java/com/tecsup/laboratorio01
├── controller
│   ├── demo01.java
│   ├── demo02.java
│   ├── informacionController.java
│   └── InformacionRestController.java
└── model
    └── InformacionPersonal.java
```

## Ejecución

```bash
cd demo01
./mvnw spring-boot:run
```

La aplicación queda en `http://localhost:8080`.
