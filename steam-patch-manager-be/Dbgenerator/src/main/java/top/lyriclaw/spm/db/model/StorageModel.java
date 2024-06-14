package top.lyriclaw.spm.db.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class StorageModel implements Serializable {
    private UUID id;

    private String storageType;

    private Long patchId;

    private String filename;

    private String description;

    private Long size;

    private String md5;

    private Instant createdAt;

    private Instant updatedAt;

    private static final long serialVersionUID = 1L;
}