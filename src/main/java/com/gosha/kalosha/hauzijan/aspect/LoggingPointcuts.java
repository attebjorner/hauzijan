package com.gosha.kalosha.hauzijan.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingPointcuts
{
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void allRestControllerBeans() {}

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void allServiceBeans() {}

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void allRepositoryBeans() {}
}
