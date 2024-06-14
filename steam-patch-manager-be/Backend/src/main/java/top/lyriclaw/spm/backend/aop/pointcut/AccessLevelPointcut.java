package top.lyriclaw.spm.backend.aop.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class AccessLevelPointcut {

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {}
}
