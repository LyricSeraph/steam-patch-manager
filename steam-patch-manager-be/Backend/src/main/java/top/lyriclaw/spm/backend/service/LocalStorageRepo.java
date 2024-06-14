package top.lyriclaw.spm.backend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import top.lyriclaw.spm.backend.config.props.StorageConfigProps;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class LocalStorageRepo implements StorageRepo {

    private final StorageConfigProps storageConfigProps;

    private final String contextPath;

    public LocalStorageRepo(StorageConfigProps storageConfigProps,
                            @Value("${server.servlet.context-path}") String contextPath) {
        this.storageConfigProps = storageConfigProps;
        this.contextPath = contextPath;
    }

    @Override
    public boolean saveFile(UUID id, MultipartFile file) {
        File targetFile = getPath(id.toString()).toFile();
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            log.error("saveFile: failed to save file " + id, e);
            return false;
        }
        return true;
    }

    @Override
    public String md5(UUID id) {
        File targetFile = getPath(id.toString()).toFile();
        try (var inputStream = new FileInputStream(targetFile)) {
            return DigestUtils.md5DigestAsHex(inputStream);
        } catch (Exception e) {
            log.error("calculate md5 for file failed: " + id.toString(), e);
        }
        return null;
    }

    @Override
    public boolean deleteFile(UUID id) {
        File fileToDelete = getPath(id.toString()).toFile();
        if (!fileToDelete.delete()) {
            log.warn("delete failed: {}", fileToDelete);
            return false;
        } else {
            log.info("delete success: {}", fileToDelete);
            return true;
        }
    }

    @Override
    public String getUrl(UUID id) {
        return contextPath + "/resources/" + id.toString();
    }

    private Path getPath(String filename) {
        return Paths.get(storageConfigProps.getLocalPath().toString(), filename);
    }
}
