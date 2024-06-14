package top.lyriclaw.spm.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lyriclaw.spm.db.mapper.BasePatchMapper;

@Repository
@Mapper
public interface PatchMapper extends BasePatchMapper {
}
