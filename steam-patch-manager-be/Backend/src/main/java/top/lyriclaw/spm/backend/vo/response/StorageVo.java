package top.lyriclaw.spm.backend.vo.response;

import lombok.Data;

import java.util.UUID;

@Data
public class StorageVo {

    private UUID id;
    private String storageType;
    private Long patchId;
    private String filename;
    private String description;
    private long size;
    private String md5;
    private String url;

}
