package top.lyriclaw.spm.backend.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import top.lyriclaw.spm.backend.constant.ApiStatus;

@Setter
@Getter
public class ApiException extends RuntimeException {

    private ApiStatus status;
    private HttpStatus httpStatus;

    public ApiException(ApiStatus status, HttpStatus httpStatus) {
        super(status.getMessage());
        this.status = status;
        this.httpStatus = httpStatus;
    }

}
