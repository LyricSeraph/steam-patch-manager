package top.lyriclaw.spm.backend.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class DebugFunctionPointcut {

    @Pointcut("@annotation(top.lyriclaw.spm.backend.aop.annotations.ForDebug)")
    public void isMethodAnnotatedByForDebug() {}

    @Pointcut("@within(top.lyriclaw.spm.backend.aop.annotations.ForDebug)")
    public void isClassAnnotatedByForDebug() {}

    @Pointcut("isMethodAnnotatedByForDebug() || isClassAnnotatedByForDebug())")
    public void isAnnotatedByForDebug() {}
}