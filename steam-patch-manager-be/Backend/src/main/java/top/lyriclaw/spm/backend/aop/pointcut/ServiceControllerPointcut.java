package top.lyriclaw.spm.backend.aop.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ServiceControllerPointcut {

    @Pointcut("within(@org.springframework.stereotype.Service top.lyriclaw.spm.backend.service.*)")
    public void isAnnotatedByService() {}

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController top.lyriclaw.spm.backend.controller.*)")
    public void isAnnotatedByController() {}

    @Pointcut("isAnnotatedByService() && top.lyriclaw.spm.backend.aop.pointcut.AccessLevelPointcut.publicMethod()")
    public void isServicePublicMethod() {}

    @Pointcut("isAnnotatedByController() && top.lyriclaw.spm.backend.aop.pointcut.ApiRespPointcut.methodReturnsApiResp()")
    public void isControllerPublicMethod() {}
}