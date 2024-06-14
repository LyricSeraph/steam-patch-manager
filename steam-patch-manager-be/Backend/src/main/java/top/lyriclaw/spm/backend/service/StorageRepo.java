package top.lyriclaw.spm.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public interface StorageRepo {

    boolean saveFile(UUID id, MultipartFile file);
    String md5(UUID id);
    boolean deleteFile(UUID id);
    String getUrl(UUID id);

}
