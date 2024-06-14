package top.lyriclaw.spm.backend.config;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.file.Path;


@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${project.fe.dist.dir}")
    private Path frontEndDistPath;

    @Autowired
    private Environment environment;

    @Override
    protected void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dist/**")
                .addResourceLocations("file:" + frontEndDistPath.toString() + "/");
    }

}
