package top.lyriclaw.spm.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.checkerframework.common.value.qual.IntRange;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import top.lyriclaw.spm.backend.aop.annotations.AuthRequired;
import top.lyriclaw.spm.backend.constant.ApiStatus;
import top.lyriclaw.spm.backend.constant.StorageType;
import top.lyriclaw.spm.backend.service.PatchService;
import top.lyriclaw.spm.backend.service.FileService;
import top.lyriclaw.spm.backend.vo.request.CreatePatchRequest;
import top.lyriclaw.spm.backend.vo.request.ModifyPatchRequest;
import top.lyriclaw.spm.backend.vo.response.*;
import top.lyriclaw.spm.db.model.PatchModel;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "001 Patch")
@RestController
@RequestMapping("/api/v1/patches")
@AllArgsConstructor
public class PatchController {

    private final PatchService patchService;
    private final FileService storageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApiResp<List<DetailedPatchVo>> getById(@Valid @NonNull @PathVariable("id") Long id) {
        var patches = patchService.getPatchesByAppId(id);
        var result = new ArrayList<DetailedPatchVo>();
        for (var patch : patches) {
            result.add(toDetailedPatchVo(patch));
        }
        return ApiResp.success(result);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResp<MyPage<PatchVo>> query(@RequestParam(value = "appid", required = false) Long appid,
                                          @Valid @IntRange(from = 1) @RequestParam(value = "page", defaultValue = "1") int page,
                                          @Valid @IntRange(from = 10, to = 20) @RequestParam(value = "size", defaultValue = "20") int size) {
        var result = patchService.queryWithPage(appid, page, size);
        return ApiResp.success(result.convert(this::toDetailedPatchVo));
    }

    @AuthRequired
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResp<PatchVo> save(@Valid @RequestBody CreatePatchRequest request) {
        var result = patchService.createPatch(request);
        if (result != null) {
            return ApiResp.success(toPatchVo(result));
        }
        return ApiResp.error(ApiStatus.STATUS_CREATE_RECORD_ERROR);
    }

    @AuthRequired
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ApiResp<PatchVo> update(@Valid @NonNull @PathVariable("id") Long id, @RequestBody ModifyPatchRequest request) {
        var result = patchService.updatePatch(id, request);
        if (result != null) {
            return ApiResp.success(toPatchVo(result));
        }
        return ApiResp.error(ApiStatus.STATUS_CREATE_RECORD_ERROR);
    }

    @AuthRequired
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ApiResp<Object> delete(@Valid @NonNull @PathVariable("id") Long id) {
        patchService.delete(id);
        return ApiResp.success();
    }

    private PatchVo toPatchVo(PatchModel model) {
        var vo = new PatchVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    private DetailedPatchVo toDetailedPatchVo(PatchModel patch) {
        var resultVo = new DetailedPatchVo();
        resultVo.setFiles(new ArrayList<>());
        BeanUtils.copyProperties(patch, resultVo);
        var storageModels = storageService.getByPatchId(patch.getId());
        for (var sm : storageModels) {
            StorageVo storageVo = new StorageVo();
            BeanUtils.copyProperties(sm, storageVo);
            storageVo.setUrl(storageService.getUrl(StorageType.valueOf(sm.getStorageType()), sm.getId()));
            resultVo.getFiles().add(storageVo);
        }
        return resultVo;

    }
}

