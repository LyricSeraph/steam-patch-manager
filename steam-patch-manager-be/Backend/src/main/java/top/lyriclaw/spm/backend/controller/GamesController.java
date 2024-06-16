package top.lyriclaw.spm.backend.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.lyriclaw.spm.backend.service.PatchService;
import top.lyriclaw.spm.backend.service.SteamGameService;
import top.lyriclaw.spm.backend.vo.response.ApiResp;
import top.lyriclaw.spm.backend.vo.response.GameVo;
import top.lyriclaw.spm.db.model.PatchModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "003 Games")
@RestController
@RequestMapping("/api/v1/games")
@AllArgsConstructor
public class GamesController {

    private final PatchService patchService;
    private final SteamGameService steamGameService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApiResp<GameVo> getGameById(@PathVariable("id") Long id) {
        String name = steamGameService.getAppNameById(id);
        return ApiResp.success(new GameVo(id, name));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResp<List<GameVo>> getGamesWithPatch() {
        var patchList = patchService.getAll();
        var appidSet = patchList.stream()
                .map(PatchModel::getAppId)
                .collect(Collectors.toSet());
        var resultList = appidSet.stream().sorted()
                .map((appid) -> {
                    String name = steamGameService.getAppNameById(appid);
                    name = StringUtils.hasLength(name) ? name : "";
                    return new GameVo(appid, name);
                })
                .collect(Collectors.toList());
        return ApiResp.success(resultList);
    }

}
