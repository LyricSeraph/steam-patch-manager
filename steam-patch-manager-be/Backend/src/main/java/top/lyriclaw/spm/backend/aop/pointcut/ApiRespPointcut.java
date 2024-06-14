package top.lyriclaw.spm.backend.aop.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
@Aspect
public class ApiRespPointcut {

    @Pointcut("execution(top.lyriclaw.spm.backend.vo.response.ApiResp *(..))")
    public void methodReturnsApiResp() {}

}

