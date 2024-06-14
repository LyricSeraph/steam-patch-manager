package top.lyriclaw.spm.db.mapper;

import java.util.List;
import java.util.UUID;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import top.lyriclaw.spm.db.model.StorageModel;
import top.lyriclaw.spm.db.model.StorageModelCriteria;

public interface BaseStorageMapper {
    long countByExample(StorageModelCriteria example);

    int deleteByExample(StorageModelCriteria example);

    int deleteByPrimaryKey(UUID id);

    int insert(StorageModel row);

    int insertSelective(StorageModel row);

    List<StorageModel> selectByExampleWithRowbounds(StorageModelCriteria example, RowBounds rowBounds);

    List<StorageModel> selectByExample(StorageModelCriteria example);

    StorageModel selectByPrimaryKey(UUID id);

    int updateByExampleSelective(@Param("row") StorageModel row, @Param("example") StorageModelCriteria example);

    int updateByExample(@Param("row") StorageModel row, @Param("example") StorageModelCriteria example);

    int updateByPrimaryKeySelective(StorageModel row);

    int updateByPrimaryKey(StorageModel row);
}