package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.GarbageCans;
import me.anchora.garbage.entry.GarbageCansCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GarbageCansMapper {
    int countByExample(GarbageCansCriteria example);

    int deleteByExample(GarbageCansCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(GarbageCans record);

    int insertSelective(GarbageCans record);

    List<GarbageCans> selectByExampleWithRowbounds(GarbageCansCriteria example, RowBounds rowBounds);

    List<GarbageCans> selectByExample(GarbageCansCriteria example);

    GarbageCans selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GarbageCans record, @Param("example") GarbageCansCriteria example);

    int updateByExample(@Param("record") GarbageCans record, @Param("example") GarbageCansCriteria example);

    int updateByPrimaryKeySelective(GarbageCans record);

    int updateByPrimaryKey(GarbageCans record);

    void createTable();

    void insertData();
}