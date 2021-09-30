package com.gosha.kalosha.hauzijan.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(20)
@Log4j2
public class LoggingServiceAspect
{
    @AfterThrowing(pointcut = "LoggingPointcuts.allServiceBeans()", throwing = "exception")
    public void afterThrowingServiceAdvice(JoinPoint jp, Throwable exception)
    {
        var method = (MethodSignature) jp.getSignature();
        log.warn(exception.getClass().getName() + ": " + exception.getMessage() + " from " + method.getMethod().getName());
    }
}
