package top.lyriclaw.spm.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.lyriclaw.spm.backend.aop.annotations.AuthRequired;
import top.lyriclaw.spm.backend.vo.response.ApiResp;

@Tag(name = "004 Auth")
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    @AuthRequired
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResp<?> auth() {
        return ApiResp.success();
    }
}
