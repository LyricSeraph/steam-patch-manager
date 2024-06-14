package top.lyriclaw.spm.backend.aop.advice;

import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import top.lyriclaw.spm.backend.service.AuthService;
import top.lyriclaw.spm.backend.service.SessionService;
import top.lyriclaw.spm.backend.vo.ApiException;
import top.lyriclaw.spm.backend.constant.ApiStatus;

@Aspect
@Component
@AllArgsConstructor
public class AuthorizationAdvice {

    private final SessionService sessionService;
    private final AuthService authService;

    @Before(value = "top.lyriclaw.spm.backend.aop.pointcut.ApiRespPointcut.methodReturnsApiResp() && " +
            "top.lyriclaw.spm.backend.aop.pointcut.AuthorizationPointcut.isAnnotatedByAuthRequired()")
    public void authTokenRequired() {
        String token = sessionService.get(SessionService.KeyType.Token);
        if (!authService.isAuthenticated(token)) {
            throw new ApiException(ApiStatus.STATUS_AUTH_TOKEN_REQUIRED, HttpStatus.UNAUTHORIZED);
        }
    }

}
