package com.gosha.kalosha.hauzijan.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(10)
@Log4j2
public class LoggingControllerAspect
{
    @Before("LoggingPointcuts.allRestControllerBeans()")
    public void beforeControllerAdvice(JoinPoint jp)
    {
        var method = (MethodSignature) jp.getSignature();
        log.info("Invoked method " + method.getMethod().getName());
    }
}
