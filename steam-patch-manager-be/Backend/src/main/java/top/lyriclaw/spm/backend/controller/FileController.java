package top.lyriclaw.spm.backend.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lyriclaw.spm.backend.aop.annotations.AuthRequired;
import top.lyriclaw.spm.backend.constant.ApiStatus;
import top.lyriclaw.spm.backend.constant.StorageType;
import top.lyriclaw.spm.backend.service.FileService;
import top.lyriclaw.spm.backend.vo.request.ModifyFileRequest;
import top.lyriclaw.spm.backend.vo.response.ApiResp;
import top.lyriclaw.spm.backend.vo.response.StorageVo;

import java.util.UUID;

@Tag(name = "002 Files")
@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class FileController {

    private final FileService storageService;

    @AuthRequired
    @RequestMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ApiResp<StorageVo> upload(@RequestPart("patchId") String patchId,
                                     @RequestPart("file") MultipartFile file) {
        // create record
        var model = storageService.save(Long.parseLong(patchId), StorageType.Local, file);
        if (model != null) {
            StorageVo result = new StorageVo();
            BeanUtils.copyProperties(model, result);
            result.setUrl(storageService.getUrl(StorageType.valueOf(model.getStorageType()), model.getId()));
            return ApiResp.success(result);
        }
        return ApiResp.error(ApiStatus.STATUS_CREATE_RECORD_ERROR);
    }

    @AuthRequired
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ApiResp<StorageVo> update(@Valid @NonNull @PathVariable("id") UUID id, @RequestBody ModifyFileRequest request) {
        var model = storageService.updateFile(id, request);
        StorageVo result = new StorageVo();
        BeanUtils.copyProperties(model, result);
        result.setUrl(storageService.getUrl(StorageType.valueOf(model.getStorageType()), model.getId()));
        return ApiResp.success(result);
    }

    @AuthRequired
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ApiResp<Object> delete(@Valid @NonNull @PathVariable("id") UUID id) {
        storageService.delete(id);
        return ApiResp.success();
    }
}
