package top.lyriclaw.spm.backend.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class AuthorizationPointcut {

    @Pointcut("@annotation(top.lyriclaw.spm.backend.aop.annotations.AuthRequired)")
    public void isAnnotatedByAuthRequired() {}

}
