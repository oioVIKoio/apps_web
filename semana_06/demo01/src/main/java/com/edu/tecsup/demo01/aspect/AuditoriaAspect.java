package com.edu.tecsup.demo01.aspect;

import com.edu.tecsup.demo01.model.Producto;
import com.edu.tecsup.demo01.service.AuditoriaService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class AuditoriaAspect {

    @Autowired
    private AuditoriaService auditoriaService;

    @AfterReturning("execution(* com.edu.tecsup.demo01.service.ProductoService.guardar(..))")
    public void auditarGuardar(JoinPoint joinPoint) {

        auditoriaService.registrar(
                "CREAR",
                joinPoint.getSignature().getName(),

                "Se registró un producto"
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
                "Se obtuvieron " + resultado.size() + " productos"
        );
    }
    @AfterReturning(
            pointcut = "execution(* com.edu.tecsup.demo01.service.ProductoService.actualizar(..))"
    )
    public void auditarActualizar(JoinPoint joinPoint) {

        Long id = (Long) joinPoint.getArgs()[0];

        auditoriaService.registrar(
                "ACTUALIZAR",
                joinPoint.getSignature().getName(),
                "Se actualizó producto con ID: " + id
        );
    }
    @AfterReturning("execution(* com.edu.tecsup.demo01.service.ProductoService.eliminar(..))")
    public void auditarEliminar(JoinPoint joinPoint) {

        Long id = (Long) joinPoint.getArgs()[0];

        auditoriaService.registrar(
                "ELIMINAR",
                joinPoint.getSignature().getName(),
                "Se eliminó producto con ID: " + id
        );
    }
}
