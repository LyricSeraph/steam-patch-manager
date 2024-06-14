package top.lyriclaw.spm.backend.aop.advice;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import top.lyriclaw.spm.backend.service.TraceHelper;

@Aspect
@Component
@AllArgsConstructor
public class LogAdvice {

    private final TraceHelper traceHelper;

    @Around(value = "top.lyriclaw.spm.backend.aop.pointcut.ServiceControllerPointcut.isServicePublicMethod()")
    public Object logService(ProceedingJoinPoint pjp) throws Throwable {
        traceHelper.logServicePublicMethod(pjp);
        var result = pjp.proceed(pjp.getArgs());
        traceHelper.logServicePublicMethodResult(pjp, result);
        return result;
    }

    @Around(value = "top.lyriclaw.spm.backend.aop.pointcut.ServiceControllerPointcut.isControllerPublicMethod()")
    public Object logController(ProceedingJoinPoint pjp) throws Throwable {
        traceHelper.logControllerMethod(pjp);
        var result = pjp.proceed(pjp.getArgs());
        traceHelper.logControllerMethodResult(pjp, result);
        return result;
    }


}
