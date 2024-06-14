package top.lyriclaw.spm.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lyriclaw.spm.backend.filter.SessionInitFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SessionInitFilter> authFilterBean(@Autowired SessionInitFilter filter){
        FilterRegistrationBean<SessionInitFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

}