package com.edu.tecsup.demo01.aspect;

import com.edu.tecsup.demo01.service.AuditoriaService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorAspect {

    @Autowired
    private AuditoriaService auditoriaService;

    @AfterThrowing(
            pointcut = "execution(* com.edu.tecsup.demo01.service.ProductoService.*(..))",
            throwing = "ex"
    )
    public void capturarError(JoinPoint joinPoint, Exception ex) {

        auditoriaService.registrar(
                "ERROR",
                joinPoint.getSignature().getName(),
                ex.getMessage()
        );

        System.out.println("ERROR AOP: " + ex.getMessage());
    }
}