package com.edu.tecsup.demo01.aspect;

import com.edu.tecsup.demo01.exception.ForbiddenException;
import com.edu.tecsup.demo01.exception.UnauthorizedException;
import com.edu.tecsup.demo01.model.Producto;
import com.edu.tecsup.demo01.service.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class AuditoriaAspect {

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private HttpServletRequest request;

    private final Map<String, String> usuarios = Map.of(
            "Ricardo", "ADMIN",
            "Ana", "USER",
            "Luis", "USER"
    );

    private void validarUsuario() {

        String usuario = request.getHeader("Usuario");
        String rolHeader = request.getHeader("Rol");

        if (usuario == null || rolHeader == null) {
            throw new UnauthorizedException(
                    "Debe enviar Usuario y Rol"
            );
        }

        String rolReal = usuarios.get(usuario);

        if (rolReal == null || !rolReal.equals(rolHeader)) {
            throw new ForbiddenException(
                    "No tiene permisos"
            );
        }
    }

    private void validarRol(String... rolesPermitidos) {

        validarUsuario();

        String rol = request.getHeader("Rol");

        for (String permitido : rolesPermitidos) {
            if (permitido.equals(rol)) {
                return;
            }
        }

        throw new ForbiddenException("Acceso denegado");
    }

    private String obtenerUsuario() {

        String usuario = request.getHeader("Usuario");

        if (usuario == null) {
            return "ANONIMO";
        }

        String rol = usuarios.get(usuario);

        return rol != null
                ? usuario + " (" + rol + ")"
                : "DESCONOCIDO";
    }

    // CONTROL DE ACCESO

    @Before("execution(* com.edu.tecsup.demo01.service.ProductoService.guardar(..))")
    public void validarCrear() {

        // ACTIVIDAD:
        // ADMIN y USER pueden crear productos
        validarRol("ADMIN", "USER");
    }

    @Before("execution(* com.edu.tecsup.demo01.service.ProductoService.eliminar(..))")
    public void validarEliminar() {
        validarRol("ADMIN");
    }

    @Before("execution(* com.edu.tecsup.demo01.service.ProductoService.actualizar(..))")
    public void validarActualizar() {
        validarRol("ADMIN");
    }

    @Before("execution(* com.edu.tecsup.demo01.service.ProductoService.listar(..))")
    public void validarListar() {
        validarRol("ADMIN", "USER");
    }

    // AUDITORÍA

    @AfterReturning("execution(* com.edu.tecsup.demo01.service.ProductoService.guardar(..))")
    public void auditarGuardar(JoinPoint joinPoint) {

        auditoriaService.registrar(
                "CREAR",
                joinPoint.getSignature().getName(),
                "Se registró un producto",
                obtenerUsuario()
        );
    }

    @AfterReturning(
            pointcut = "execution(* com.edu.tecsup.demo01.service.ProductoService.listar(..))",
            returning = "resultado"
    )
    public void auditarListar(
            JoinPoint joinPoint,
            List<Producto> resultado) {

        auditoriaService.registrar(
                "LISTAR",
                joinPoint.getSignature().getName(),
                "Se obtuvieron " + resultado.size() + " productos",
                obtenerUsuario()
        );
    }

    @AfterReturning("execution(* com.edu.tecsup.demo01.service.ProductoService.actualizar(..))")
    public void auditarActualizar(JoinPoint joinPoint) {

        Long id = (Long) joinPoint.getArgs()[0];

        auditoriaService.registrar(
                "ACTUALIZAR",
                joinPoint.getSignature().getName(),
                "Se actualizó producto con ID: " + id,
                obtenerUsuario()
        );
    }

    @AfterReturning("execution(* com.edu.tecsup.demo01.service.ProductoService.eliminar(..))")
    public void auditarEliminar(JoinPoint joinPoint) {

        Long id = (Long) joinPoint.getArgs()[0];

        auditoriaService.registrar(
                "ELIMINAR",
                joinPoint.getSignature().getName(),
                "Se eliminó producto ID: " + id,
                obtenerUsuario()
        );
    }
}