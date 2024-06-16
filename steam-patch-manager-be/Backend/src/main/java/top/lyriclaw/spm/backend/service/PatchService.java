package top.lyriclaw.spm.backend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import top.lyriclaw.spm.backend.mapper.PatchMapper;
import top.lyriclaw.spm.backend.vo.request.CreatePatchRequest;
import top.lyriclaw.spm.backend.vo.request.ModifyPatchRequest;
import top.lyriclaw.spm.backend.vo.response.MyPage;
import top.lyriclaw.spm.db.model.PatchModel;
import top.lyriclaw.spm.db.model.PatchModelCriteria;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PatchService {

    private final PatchMapper patchMapper;
    private final FileService storageService;

    public PatchModel createPatch(CreatePatchRequest request) {
        var patch = PatchModel.builder()
                .appId(request.getAppid())
                .patchName(request.getPatchName())
                .reference(request.getReference())
                .description(request.getDescription())
                .appVersion(request.getAppVersion())
                .patchVersion(request.getPatchVersion())
                .build();
        if (patchMapper.insert(patch) > 0) {
            return patchMapper.selectByPrimaryKey(patch.getId());
        }
        return null;
    }

    public PatchModel updatePatch(long id, ModifyPatchRequest request) {
        var builder = PatchModel.builder().id(id);
        try {
            Method method = builder.getClass().getMethod(request.getKey().name(), String.class);
            builder = (PatchModel.PatchModelBuilder) method.invoke(builder, request.getValue());
        } catch (Exception e) {
            log.error("updatePatch: " + "field not found: " + request.getKey(), e);
        }
        var patch = builder.build();
        if (patchMapper.updateByPrimaryKeySelective(patch) > 0) {
            return patchMapper.selectByPrimaryKey(patch.getId());
        }
        return null;
    }

    public List<PatchModel> getPatchesByAppId(long appId) {
        var patchCriteria = new PatchModelCriteria();
        patchCriteria.createCriteria().andAppIdEqualTo(appId);
        patchCriteria.setOrderByClause("created_at asc");
        return patchMapper.selectByExample(patchCriteria);
    }

    public List<PatchModel> getAll() {
        var patchCriteria = new PatchModelCriteria();
        patchCriteria.setOrderByClause("created_at asc");
        return patchMapper.selectByExample(patchCriteria);
    }

    public MyPage<PatchModel> queryWithPage(Long appid, int page, int size) {
        int offset = (page - 1) * size;
        var patchCriteria = new PatchModelCriteria();
        if (appid != null) {
            patchCriteria.createCriteria().andAppIdEqualTo(appid);
        }
        patchCriteria.setOrderByClause("created_at asc");
        long count = patchMapper.countByExample(patchCriteria);
        var list = patchMapper.selectByExampleWithRowbounds(patchCriteria, new RowBounds(offset, size));
        return MyPage.<PatchModel>builder()
                .data(list)
                .total(count)
                .page(page)
                .size(size)
                .build();
    }

    public void delete(long patchId) {
        patchMapper.deleteByPrimaryKey(patchId);
        storageService.deleteByPatchId(patchId);
    }

}
