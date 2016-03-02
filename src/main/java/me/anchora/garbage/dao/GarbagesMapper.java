package me.anchora.garbage.dao;

import java.util.List;

import me.anchora.garbage.entry.Garbages;
import me.anchora.garbage.entry.GarbagesCriteria;
import me.anchora.garbage.entry.base.ReportVo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GarbagesMapper {
    int countByExample(GarbagesCriteria example);

    int deleteByExample(GarbagesCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Garbages record);

    int insertSelective(Garbages record);

    List<Garbages> selectByExampleWithRowbounds(GarbagesCriteria example, RowBounds rowBounds);

    List<Garbages> selectByExample(GarbagesCriteria example);

    Garbages selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Garbages record, @Param("example") GarbagesCriteria example);

    int updateByExample(@Param("record") Garbages record, @Param("example") GarbagesCriteria example);

    int updateByPrimaryKeySelective(Garbages record);

    int updateByPrimaryKey(Garbages record);

    Double sumWeightByExample(GarbagesCriteria criteria);

    List<Garbages> selectByExampleWithOtherWithRowbounds(Garbages garbages, RowBounds rowBounds);

    Integer countByExampleWithOther(Garbages garbages);

    void createTable();

    List<ReportVo> garbageTypeReport();

    List<ReportVo> garbageTypeReportWithRowbounds(ReportVo reportVo, RowBounds rowBounds);

    Integer userGarbageTypeReportCount(ReportVo vo);
}