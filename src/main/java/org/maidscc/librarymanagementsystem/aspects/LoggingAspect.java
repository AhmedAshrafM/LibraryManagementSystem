package org.maidscc.librarymanagementsystem.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* org.maidscc.librarymanagementsystem.services.*.*(..))")
    private void publicMethodsFromLoggingPackage() {
    }

    @Before("publicMethodsFromLoggingPackage()")
    public void logMethodCall(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }


    @Around(value = "publicMethodsFromLoggingPackage()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("Execution time of method: {} with arguments: {} is {} ms", joinPoint.getSignature().getName(), joinPoint.getArgs(), endTime - startTime);
        return result;
    }

}
