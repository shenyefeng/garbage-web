package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.GarbageTypes;
import me.anchora.garbage.entry.GarbageTypesCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GarbageTypesMapper {
    int countByExample(GarbageTypesCriteria example);

    int deleteByExample(GarbageTypesCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(GarbageTypes record);

    int insertSelective(GarbageTypes record);

    List<GarbageTypes> selectByExampleWithRowbounds(GarbageTypesCriteria example, RowBounds rowBounds);

    List<GarbageTypes> selectByExample(GarbageTypesCriteria example);

    GarbageTypes selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GarbageTypes record, @Param("example") GarbageTypesCriteria example);

    int updateByExample(@Param("record") GarbageTypes record, @Param("example") GarbageTypesCriteria example);

    int updateByPrimaryKeySelective(GarbageTypes record);

    int updateByPrimaryKey(GarbageTypes record);

    void createTable();
}