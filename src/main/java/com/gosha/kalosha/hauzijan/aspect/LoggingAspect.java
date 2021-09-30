package com.gosha.kalosha.hauzijan.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(30)
@Log4j2
public class LoggingAspect
{
    @Around("LoggingPointcuts.allRepositoryBeans() || LoggingPointcuts.allServiceBeans()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable
    {
        var method = (MethodSignature) pjp.getSignature();
        long begin = System.currentTimeMillis();
        Object targetMethodResult = pjp.proceed();
        long delta = System.currentTimeMillis() - begin;
        log.info("Execution of method " + method.getMethod().getName() + " lasted " + delta + " ms");
        return targetMethodResult;
    }
}
