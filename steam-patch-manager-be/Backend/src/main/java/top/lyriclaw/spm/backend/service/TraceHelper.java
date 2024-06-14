package top.lyriclaw.spm.backend.service;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.function.Tuple2;
import retrofit2.http.Multipart;
import top.lyriclaw.spm.backend.aop.annotations.ForDebug;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TraceHelper {

    public static Class<?>[] ignoredClasses = {
            MultipartFile.class,
            HttpServletRequest.class,
            HttpServletResponse.class,
    };

    @ForDebug
    public void logServicePublicMethod(ProceedingJoinPoint pjp) {
        var argsDict = collectArgsDict(pjp);
        log.trace("Service >> {}.{}: {}",
                pjp.getSignature().getDeclaringType().getName(),
                pjp.getSignature().getName(),
                JSONObject.toJSONString(argsDict));
    }

    @ForDebug
    public void logServicePublicMethodResult(ProceedingJoinPoint pjp, Object result) {
        log.trace("Service >> {}.{} -> {}",
                pjp.getSignature().getDeclaringType().getName(),
                pjp.getSignature().getName(),
                result);
    }

    @ForDebug
    public void logControllerMethod(ProceedingJoinPoint pjp) {
        var argsDict = collectArgsDict(pjp);
        log.trace("Controller >> {}.{}: {}",
                pjp.getSignature().getDeclaringType().getName(),
                pjp.getSignature().getName(),
                JSONObject.toJSONString(argsDict));
    }

    @ForDebug
    public void logControllerMethodResult(ProceedingJoinPoint pjp, Object result) {
        log.trace("Controller >> {}.{} -> {}",
                pjp.getSignature().getDeclaringType().getName(),
                pjp.getSignature().getName(), result);
    }

    private Map<String, Object> collectArgsDict(ProceedingJoinPoint pjp) {
        String[] parameterNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
        Object[] parameters = pjp.getArgs();
        return StreamUtils.zip(Arrays.stream(parameterNames), Arrays.stream(parameters), Map::entry)
                .map(entry -> {
                    if (isIgnoredType(entry.getValue())) {
                        return Map.entry(entry.getKey(), entry.getValue().toString());
                    } else {
                        return entry;
                    }
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean isIgnoredType(Object o) {
        for (Class<?> ignoredClass : ignoredClasses) {
            if (ignoredClass.isInstance(o)) {
                return true;
            }
        }
        return false;
    }

}
