package com.edu.tecsup.demo01.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.edu.tecsup.demo01.service.*.*(..))")
    public void antes(JoinPoint joinPoint) {
        System.out.println("▶ Ejecutando: " + joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.edu.tecsup.demo01.service.*.*(..))")
    public void despues(JoinPoint joinPoint) {
        System.out.println("✔ Finalizó: " + joinPoint.getSignature().getName());
    }
}

