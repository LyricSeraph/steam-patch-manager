package top.lyriclaw.spm.db.model;

import java.io.Serializable;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PatchModel implements Serializable {
    private Long id;

    private Long appId;

    private String patchName;

    private String appVersion;

    private String patchVersion;

    private String reference;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private static final long serialVersionUID = 1L;
}