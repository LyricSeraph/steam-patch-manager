package top.lyriclaw.spm.backend.vo.request;

import lombok.Data;

@Data
public class ModifyFileRequest {

    public enum Field {
        filename, description
    }

    private Field key;
    private String value;

}
