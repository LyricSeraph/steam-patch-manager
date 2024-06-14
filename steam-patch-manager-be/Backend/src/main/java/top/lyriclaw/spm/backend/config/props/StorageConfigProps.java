package top.lyriclaw.spm.backend.config.props;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
@ConfigurationProperties(prefix = "project.storage")
@Data
public class StorageConfigProps {

    private Path localPath;

    @PostConstruct
    void mkdirPaths() {
        Path[] dirs = {localPath};
        for (Path path : dirs) {
            if (!path.toFile().exists()) {
                //noinspection ResultOfMethodCallIgnored
                path.toFile().mkdirs();
            }
        }
    }

}
