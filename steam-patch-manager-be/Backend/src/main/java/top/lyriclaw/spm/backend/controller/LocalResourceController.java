package top.lyriclaw.spm.backend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lyriclaw.spm.backend.config.props.StorageConfigProps;
import top.lyriclaw.spm.backend.service.FileService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@Slf4j
@AllArgsConstructor
public class LocalResourceController {

    private final StorageConfigProps storageConfigProps;
    private final ResourceLoader resourceLoader;
    private final FileService storageService;

    private Resource loadResource(String filePath) {
        return resourceLoader.getResource("file://" + filePath);
    }

    @GetMapping("/resources/{storageId}")
    @ResponseBody
    public ResponseEntity<Resource> serveResource(@PathVariable("storageId") UUID storageId) {
        return serveFile(storageId);
    }

    private ResponseEntity<Resource> serveFile(UUID storageId) {
        Path baseDirectory = storageConfigProps.getLocalPath();
        Path path = Paths.get(baseDirectory.toString(), storageId.toString());
        String realFileName = storageService.get(storageId).getFilename();
        Resource file = loadResource(path.toString());
        if (file.exists()) {
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + realFileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(file);
        }
        return ResponseEntity.notFound().build();
    }

}
