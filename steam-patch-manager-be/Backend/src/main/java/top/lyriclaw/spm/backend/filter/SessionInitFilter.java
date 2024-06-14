package top.lyriclaw.spm.backend.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import top.lyriclaw.spm.backend.service.SessionService;
import top.lyriclaw.spm.backend.constant.RequestHeaders;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Component
public class SessionInitFilter implements Filter {

    private final SessionService sessionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        MDC.put("traceId", RandomStringUtils.randomAlphanumeric(8));
        var token = getHeader(request, RequestHeaders.AUTH_TOKEN);
        sessionService.init();
        sessionService.set(SessionService.KeyType.Token, token);
        sessionService.freeze();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private String getHeader(ServletRequest request, String key) {
        return StringUtils.defaultIfEmpty(((HttpServletRequest) request).getHeader(key), StringUtils.EMPTY);
    }


}
