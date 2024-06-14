package top.lyriclaw.spm.backend.aop.advice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import top.lyriclaw.spm.backend.config.props.DebugConfigProps;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class DebugFunctionAdvice {

    private final DebugConfigProps debugConfigProps;

    @Around(value = "top.lyriclaw.spm.backend.aop.pointcut.DebugFunctionPointcut.isAnnotatedByForDebug()")
    public Object debugFilter(ProceedingJoinPoint pjp) throws Throwable {
        if (debugConfigProps.isDebugEnabled()) {
            return pjp.proceed(pjp.getArgs());
        }
        return null;
    }

}
