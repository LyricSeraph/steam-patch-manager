package top.lyriclaw.spm.db.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import top.lyriclaw.spm.db.model.PatchModel;
import top.lyriclaw.spm.db.model.PatchModelCriteria;

public interface BasePatchMapper {
    long countByExample(PatchModelCriteria example);

    int deleteByExample(PatchModelCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(PatchModel row);

    int insertSelective(PatchModel row);

    List<PatchModel> selectByExampleWithRowbounds(PatchModelCriteria example, RowBounds rowBounds);

    List<PatchModel> selectByExample(PatchModelCriteria example);

    PatchModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") PatchModel row, @Param("example") PatchModelCriteria example);

    int updateByExample(@Param("row") PatchModel row, @Param("example") PatchModelCriteria example);

    int updateByPrimaryKeySelective(PatchModel row);

    int updateByPrimaryKey(PatchModel row);
}