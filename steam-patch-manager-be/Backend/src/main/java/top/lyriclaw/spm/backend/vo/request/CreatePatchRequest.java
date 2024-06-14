package top.lyriclaw.spm.backend.vo.request;

import lombok.Data;

@Data
public class CreatePatchRequest {

    private long appid;
    private String patchName;
    private String reference;
    private String description;
    private String appVersion;
    private String patchVersion;
}
