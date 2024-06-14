package top.lyriclaw.spm.backend.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "project.auth")
@Data
public class AuthConfigProps {

    private boolean enableAuthKey;
    private String authKey;

}
