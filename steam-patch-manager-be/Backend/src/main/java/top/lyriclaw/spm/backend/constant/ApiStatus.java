package top.lyriclaw.spm.backend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiStatus {

    STATUS_UNKNOWN_ERROR(-2, "unknown error"),
    STATUS_INTERNAL_SERVER_ERROR(-1, "internal server error"),
    STATUS_OK(0x0000, "success"),
    STATUS_AUTH_TOKEN_REQUIRED(0x0001, "auth token required"),
    STATUS_NOT_FOUND(0x0002, "resource not found"),

    STATUS_CREATE_RECORD_ERROR(0x1001, "create record error"),
    ;


    private final int code;
    private final String message;

}
