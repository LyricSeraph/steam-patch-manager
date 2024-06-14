package top.lyriclaw.spm.backend.vo.request;

import lombok.Data;

@Data
public class ModifyPatchRequest {

    public enum Field {
        patchName,
        appVersion,
        patchVersion,
        description,
        reference
    }

    private Field key;
    private String value;
}
