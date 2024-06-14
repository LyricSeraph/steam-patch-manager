package top.lyriclaw.spm.backend.vo.response;

import lombok.Data;
import top.lyriclaw.spm.db.model.PatchModel;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class PatchVo {

    private Long id;
    private Long appId;
    private String patchName;
    private String appVersion;
    private String patchVersion;
    private String reference;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
