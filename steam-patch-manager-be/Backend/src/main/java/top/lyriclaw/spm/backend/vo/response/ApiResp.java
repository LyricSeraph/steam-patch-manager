package top.lyriclaw.spm.backend.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import top.lyriclaw.spm.backend.constant.ApiStatus;

@Data
@AllArgsConstructor
@ToString
public class ApiResp<T> {

    private int status;
    private String msg;
    private T data;

    public static <T> ApiResp<T> success(T data) {
        return create(ApiStatus.STATUS_OK.getCode(), "success", data);
    }

    public static <T> ApiResp<T> success() {
        return success(null);
    }

    public static <T> ApiResp<T> error(ApiStatus status) {
        return create(status.getCode(), status.getMessage(), null);
    }

    public static <T> ApiResp<T> create(int status, String msg, T data) {
        return new ApiResp<T>(status, msg, data);
    }
}
