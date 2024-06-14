package top.lyriclaw.spm.backend.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.lyriclaw.spm.backend.mapper.StorageMapper;
import top.lyriclaw.spm.backend.constant.StorageType;
import top.lyriclaw.spm.backend.vo.request.ModifyFileRequest;
import top.lyriclaw.spm.db.model.StorageModel;
import top.lyriclaw.spm.db.model.StorageModelCriteria;

import java.lang.reflect.Method;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class FileService {

    private final StorageMapper storageMapper;
    private final LocalStorageRepo localStorageRepo;
    private final EnumMap<StorageType, StorageRepo> storageRepos = new EnumMap<>(StorageType.class);

    @PostConstruct
    public void init() {
        storageRepos.put(StorageType.Local, localStorageRepo);
    }

    public StorageModel get(UUID id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<StorageModel> getAll() {
        return storageMapper.selectByExample(new StorageModelCriteria());
    }

    public StorageModel save(long patchId, StorageType type, MultipartFile file) {
        var modelToInsert = StorageModel.builder()
                .id(UUID.randomUUID())
                .patchId(patchId)
                .filename(file.getOriginalFilename())
                .size(file.getSize())
                .storageType(type.toString())
                .build();
        if (storageMapper.insert(modelToInsert) > 0) {
            boolean success = storageRepos.get(type).saveFile(modelToInsert.getId(), file);
            if (success) {
                String md5 = storageRepos.get(type).md5(modelToInsert.getId());
                modelToInsert.setMd5(md5);
                storageMapper.updateByPrimaryKeySelective(StorageModel.builder()
                        .id(modelToInsert.getId())
                        .md5(md5)
                        .build());
                return storageMapper.selectByPrimaryKey(modelToInsert.getId());
            } else {
                storageMapper.deleteByPrimaryKey(modelToInsert.getId());
            }
        }
        return null;
    }

    public StorageModel updateFile(UUID id, ModifyFileRequest request) {
        var builder = StorageModel.builder()
                .id(id);
        try {
            Method method = builder.getClass().getMethod(request.getKey().name(), String.class);
            builder = (StorageModel.StorageModelBuilder) method.invoke(builder, request.getValue());
        } catch (Exception e) {
            log.error("updateFile: " + "field not found: " + request.getKey(), e);
        }
        var model = builder.build();
        storageMapper.updateByPrimaryKeySelective(model);
        return storageMapper.selectByPrimaryKey(model.getId());
    }

    public List<StorageModel> getByPatchId(long patchId) {
        var criteria = new StorageModelCriteria();
        criteria.createCriteria().andPatchIdEqualTo(patchId);
        return storageMapper.selectByExample(criteria);
    }

    public void delete(UUID id) {
        StorageModel model = storageMapper.selectByPrimaryKey(id);
        storageRepos.get(StorageType.valueOf(model.getStorageType())).deleteFile(model.getId());
        storageMapper.deleteByPrimaryKey(model.getId());
    }

    public void deleteByPatchId(long patchId) {
        var targets = getByPatchId(patchId);
        if (targets != null) {
            for (var target : targets) {
                delete(target.getId());
            }
        }
    }

    public String getUrl(StorageType type, UUID storageId) {
        return storageRepos.get(type).getUrl(storageId);
    }

}
