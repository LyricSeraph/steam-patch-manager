package top.lyriclaw.spm.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import top.lyriclaw.spm.backend.constant.ApiStatus;
import top.lyriclaw.spm.backend.vo.ApiException;
import top.lyriclaw.spm.backend.vo.response.ApiResp;


@ControllerAdvice(basePackages = "top.lyriclaw.spm.backend")
@Slf4j
public class RestErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleResponseException(ApiException ex, WebRequest request) {
        ApiResp<Object> apiResp = ApiResp.error(ex.getStatus());
        return handleExceptionInternal(ex, apiResp, new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        log.error("handleAll ex = " + ex + ", request = " + request, ex);
        ApiResp<Object> apiError = ApiResp.error(ApiStatus.STATUS_INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
